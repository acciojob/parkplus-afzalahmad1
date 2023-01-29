package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);

//        List<Spot> spotList = new ArrayList<>();
//
//        for(int i=0;i<5;i++){
//            Spot spot = new Spot(SpotType.TWO_WHEELER,10,false);
//            spot.setParkingLot(parkingLot);
//            spotList.add(spot);
//        }
//        for(int i=0;i<5;i++){
//            Spot spot = new Spot(SpotType.FOUR_WHEELER,20,false);
//            spot.setParkingLot(parkingLot);
//            spotList.add(spot);
//        }
//        for(int i=0;i<5;i++){
//            Spot spot = new Spot(SpotType.OTHERS,50,false);
//            spot.setParkingLot(parkingLot);
//            spotList.add(spot);
//        }
//        parkingLot.setSpotList(spotList);
//        spotRepository1.saveAll(spotList);

        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();

        spot.setPricePerHour(pricePerHour);
        if(numberOfWheels<4){
            spot.setSpotType(SpotType.TWO_WHEELER);
        } else if (numberOfWheels==4) {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setOccupied(false);
        spot.setParkingLot(parkingLot);
        spotList.add(spot);
        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) throws Exception {
        Spot spot = spotRepository1.findById(spotId).get();
            ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
            spot.setParkingLot(parkingLot);
            spot.setPricePerHour(pricePerHour);
            spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        //delete spot related to parking
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        for(Spot spot : spotList){
            spotRepository1.delete(spot);
        }
        parkingLotRepository1.deleteById(parkingLotId);

    }
}
