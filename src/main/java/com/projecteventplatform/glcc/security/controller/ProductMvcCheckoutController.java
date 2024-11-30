package com.projecteventplatform.glcc.security.controller;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.security.dto.ProductRequest;
import com.projecteventplatform.glcc.security.dto.StripeResponse;
import com.projecteventplatform.glcc.security.service.StripeService;
import com.projecteventplatform.glcc.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ProductMvcCheckoutController {
    private StripeService stripeService ;
    private EventService eventService ;

    public ProductMvcCheckoutController(StripeService stripeService , EventService eventService){
        this.stripeService = stripeService ;
        this.eventService = eventService ;
    }


    @GetMapping("/buy-ticket/{id}")
    public String buyTicket(@PathVariable Long id) {
        Event event = eventService.getEventById(id);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setAmount(event.getPrice().longValue()*100);
        productRequest.setQuantity(1L);
        productRequest.setName(event.getTitle());
        productRequest.setCurrency("USD");
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        String redirectUrl = stripeResponse.getSessionUrl();

        //setting up the user to the event and the event to the user
        eventService.addParticipantToEvent(id);
        return "redirect:" + redirectUrl;
    }


}
