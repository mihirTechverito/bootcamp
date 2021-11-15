package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendantTest {

  @Test
  void directCarToParkingLotAndParkSuccessfully() {
    ParkingLot parkingLot = new ParkingLot(1);
    Attendant attendant = new Attendant(List.of(parkingLot));

    boolean isDirectedSuccessfully = attendant.parkingDirection(Attendant.NO_SORTING);

    assertTrue(isDirectedSuccessfully);
  }

  @Test
  void directedCarShouldBeRejectedAsParkingLotFull() {
    ParkingLot parkingLot = new ParkingLot(1);
    Attendant attendant = new Attendant(List.of(parkingLot));

    attendant.parkingDirection(Attendant.NO_SORTING);
    boolean isDirectedFailure = attendant.parkingDirection(Attendant.NO_SORTING);

    assertFalse(isDirectedFailure);
  }

  @Test
  void directMultipleCarsToFreeParkingLotWhenMultipleLotsAreAvailable() {
    ParkingLot parkingLot1 = new ParkingLot(1);
    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.parkingDirection(Attendant.NO_SORTING);
    boolean isDirectedSuccessfully = attendant.parkingDirection(Attendant.NO_SORTING);

    assertTrue(isDirectedSuccessfully);
  }

  @Test
  void directCarToFirstAvailableFreeLot() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.parkingDirection(Attendant.NO_SORTING);
    attendant.parkingDirection(Attendant.NO_SORTING);

    verify(parkingLot1, times(2)).park();
  }

  @Test
  void directCarToLotWithLeastFreeSpace() {
    ParkingLot parkingLot1 = new ParkingLot(2);

    ParkingLot parkingLot2 = spy(new ParkingLot(1));

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.parkingDirection(Attendant.MAXIMUM_FREE_SLOTS);

    verify(parkingLot2, times(1)).park();
  }

  @Test
  void directCarToLotWithMostFreeSpace() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.parkingDirection(Attendant.MAXIMUM_FILLED_SLOTS);

    verify(parkingLot1, times(1)).park();
  }

  @Test
  void directCarShouldFailWhenAllLotsFilled() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.parkingDirection(Attendant.MAXIMUM_FILLED_SLOTS);
    attendant.parkingDirection(Attendant.MAXIMUM_FREE_SLOTS);
    attendant.parkingDirection(Attendant.MAXIMUM_FREE_SLOTS);
    assertFalse(attendant.parkingDirection(Attendant.MAXIMUM_FREE_SLOTS));
  }
}
