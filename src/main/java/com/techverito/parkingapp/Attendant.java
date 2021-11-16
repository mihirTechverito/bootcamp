package com.techverito.parkingapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Attendant extends ParkingLotObserver implements ParkingWorker {

  private final List<ParkingLot> parkingLots = new ArrayList<>();
  private final List<ParkingLot> availableParkingLots = new ArrayList<>();

  public static final Comparator<ParkingLot> MAXIMUM_FREE_SLOTS =
      Comparator.comparingLong(ParkingLot::availableSpotCount);

  public static final Comparator<ParkingLot> MAXIMUM_FILLED_SLOTS =
      (ParkingLot pl1, ParkingLot pl2) -> Long.compare(pl2.availableSpotCount(), pl1.availableSpotCount());

  public static final Comparator<ParkingLot> ORIGINAL_ORDER =
          (ParkingLot pl1, ParkingLot pl2) -> 0;

  public Attendant(List<ParkingLot> parkingLots) {
    this.parkingLots.addAll(parkingLots);
    this.availableParkingLots.addAll(parkingLots);

    this.parkingLots.forEach(parkingLot -> parkingLot.addObserver(this));
  }

  private boolean directToFreeLot() {
    Optional<ParkingLot> optionalParkingLot = availableParkingLots.stream().findFirst();

    if (optionalParkingLot.isPresent()) {
      optionalParkingLot.get().park();
      return true;
    }

    return false;
  }



  public boolean direct(Comparator<ParkingLot> parkingDirector) {
    availableParkingLots.sort(parkingDirector);
    return directToFreeLot();
  }

  @Override
  public boolean assign() {
    return direct(Attendant.ORIGINAL_ORDER);
  }


  @Override
  public boolean confirmFreeSpotsAvailable() {
    return parkingLots.stream().anyMatch(ParkingLot::isAvailable);
  }

  public void onParkingFull(ParkingLot parkingLot) {
    this.availableParkingLots.remove(parkingLot);
  }

  public void onParkingAvailable(ParkingLot parkingLot) {
    this.availableParkingLots.add(parkingLot);
  }

}
