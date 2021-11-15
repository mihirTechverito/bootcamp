package com.techverito.parkingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {

  private final int lotSize;

  private List<ParkingSpot> parkingSpots = new ArrayList<>();

  private ParkingLotObserver observer;

  public ParkingLot(int lotSize) {
    this.lotSize = lotSize;
    createSpots();
  }

  private void notifyOnAvailableAgain() {
    if(observer != null) observer.onParkingAvailable();
  }

  private void notifyObserver() {
    if(observer != null) observer.onParkingFull();
  }

  private void createSpots() {

    this.parkingSpots =
        IntStream.range(1, lotSize + 1)
            .mapToObj(count -> new ParkingSpot(true))
            .collect(Collectors.toList());
  }

  public void addObserver(ParkingLotObserver owner) {
    this.observer = owner;
  }

  public boolean unpark() {
    Optional<ParkingSpot> parkingSpotOptional =
            parkingSpots.stream().filter(ps -> !ps.isEmpty()).findFirst();

    if(parkingSpotOptional.isPresent()){

      parkingSpotOptional.get().unpark();

      if(oneSpotFreed()){
        notifyOnAvailableAgain();
      }
      return true;
    }
    return false;
  }

  public boolean park() {

    Optional<ParkingSpot> parkingSpotOptional =
            parkingSpots.stream().filter(ParkingSpot::isEmpty).findFirst();

    if (parkingSpotOptional.isPresent()) {
      parkingSpotOptional.get().park();

      if (isFull()) {
        notifyObserver();
      }
      return true;
    }
    return false;
  }

  private boolean oneSpotFreed() {
    return parkingSpots.stream().filter(ps -> !ps.isEmpty()).count() == lotSize - 1;
  }

  boolean isFull() {
    return parkingSpots.stream().filter(ParkingSpot::isEmpty).findAny().isEmpty();
  }

  public boolean isAvailable() {
    return !isFull();
  }

  public long availableSpotCount() {
    return parkingSpots.stream().filter(ParkingSpot::isEmpty).count();
  }
}
