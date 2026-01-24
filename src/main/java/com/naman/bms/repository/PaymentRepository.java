package com.naman.bms.repository;

import com.naman.bms.model.Booking;
import com.naman.bms.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>
{
    Optional<Payment> findByTransactionId(String transactionId);

}
