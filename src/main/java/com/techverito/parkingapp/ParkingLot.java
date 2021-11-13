package com.techverito.parkingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {

    private final int lotSize;

    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
        createSpots();
    }

    public boolean park() {

        Optional<ParkingSpot> parkingSpotOptional =
                parkingSpots.stream().filter(ParkingSpot::isEmpty).findFirst();

        if (parkingSpotOptional.isPresent()) {
            parkingSpotOptional.get().park();
            return true;
        }
        return false;
    }

    private void createSpots() {

    this.parkingSpots =
        IntStream.range(1, lotSize+1)
            .mapToObj(count -> new ParkingSpot(true))
            .collect(Collectors.toList());
    }
}
