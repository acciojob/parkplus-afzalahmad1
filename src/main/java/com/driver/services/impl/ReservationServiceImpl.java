package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        Reservation reservation = new Reservation();
        User user = userRepository3.findById(userId).get();

        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

        List<Spot> spotList = parkingLot.getSpotList();
        if(user == null || parkingLot == null){
            return  reservation = null;
        }

        for(Spot spot : spotList){
            if(numberOfWheels<4 && spot.getSpotType()==SpotType.TWO_WHEELER && spot.getOccupied()==false){
                spot.setOccupied(true);
                reservation.setSpot(spot);
        } else if (numberOfWheels==4 && spot.getSpotType() == SpotType.FOUR_WHEELER && spot.getOccupied()==false) {
                spot.setOccupied(true);
                reservation.setSpot(spot);
            }else{
                spot.setOccupied(true);
                reservation.setSpot(spot);
            }
        }

        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        Payment payment = new Payment();
        payment.setReservation(reservation);

        reservationRepository3.save(reservation);

        return reservation;
    }
}
