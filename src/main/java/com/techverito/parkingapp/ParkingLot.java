package com.techverito.parkingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingLot {

    private final int lotSize;

    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
    }

    public boolean park() {


        Optional<ParkingSpot> parkingSpotOptional = parkingSpots.stream().filter(ParkingSpot::isEmpty).findFirst();

        if(parkingSpotOptional.isPresent()){
            parkingSpotOptional.get().park();
            return true;
        }
        throw new ParkingFullException();
    }

    public void createSpots() {
        for(int i= 1; i <= lotSize; i++){
            ParkingSpot parkingSpot = new ParkingSpot(true);
            parkingSpots.add(parkingSpot);
        }
    }
}
