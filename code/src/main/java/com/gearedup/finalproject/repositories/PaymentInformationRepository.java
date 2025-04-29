package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
}