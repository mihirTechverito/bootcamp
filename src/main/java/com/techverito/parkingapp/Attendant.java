package com.techverito.parkingapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Attendant {

  private final List<ParkingLot> parkingLots = new ArrayList<>();

  public static final Comparator<ParkingLot> MAXIMUM_FREE_SLOTS =
      Comparator.comparingLong(ParkingLot::availableSpotCount);

  public static final Comparator<ParkingLot> MAXIMUM_FILLED_SLOTS =
      (ParkingLot pl1, ParkingLot pl2) -> Long.compare(pl2.availableSpotCount(), pl1.availableSpotCount());

  public static final Comparator<ParkingLot> NO_SORTING =
          (ParkingLot pl1, ParkingLot pl2) -> 0;

  public Attendant(List<ParkingLot> parkingLots) {

    this.parkingLots.addAll(parkingLots);
  }

  private boolean directToFreeLot() {

    Optional<ParkingLot> optionalParkingLot =
        parkingLots.stream().filter(ParkingLot::isAvailable).findFirst();

    if (optionalParkingLot.isPresent()) {
      optionalParkingLot.get().park();
      return true;
    }

    return false;
  }

  public boolean parkingDirection(Comparator<ParkingLot> comparatorCondition) {
    parkingLots.sort(comparatorCondition);
    return directToFreeLot();
  }

  public boolean assign(Comparator<ParkingLot> lotComparator) {
    return parkingDirection(lotComparator);
  }


  public boolean hasFreeSpots() {

    return parkingLots.stream().anyMatch(ParkingLot::isAvailable);
  }
}
