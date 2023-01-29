package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Payment payment = new Payment();
        try {
            Reservation reservation = reservationRepository2.findById(reservationId).get();
            int bill = reservation.getSpot().getPricePerHour() * reservation.getNumberOfHours();

            if (bill > amountSent) {
                throw new Exception("Insufficient Amount");
            }

            if (Objects.equals(mode, "CASH") || Objects.equals(mode, "CARD") || Objects.equals(mode, "UPI"))
                payment.setPaymentMode(PaymentMode.valueOf(mode));
            else
                throw new Exception("Payment mode not detected");


            payment.setPaymentCompleted(true);
            payment.setReservation(reservation);

            reservation.getSpot().setOccupied(false);

            paymentRepository2.save(payment);
        }catch(Exception e){
            throw new Exception();
        }

        return payment ;
    }
}
