package com.projecteventplatform.glcc.security.service;

import com.projecteventplatform.glcc.security.dto.ProductRequest;
import com.projecteventplatform.glcc.security.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey ;
    public StripeResponse checkoutProducts(ProductRequest productRequest){
        Stripe.apiKey=secretKey ;
        SessionCreateParams.LineItem.PriceData.ProductData.Builder productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName());

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount())
                .setProductData(productData.build())
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        final SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null ;
        try {
            session = Session.create(params) ;
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session created ")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}