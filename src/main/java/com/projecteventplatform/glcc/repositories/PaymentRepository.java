package com.projecteventplatform.glcc.repositories;

import com.projecteventplatform.glcc.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}