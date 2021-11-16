package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendantTest {

  @Test
  void directCarWhenFreeSpotAvailableShouldBeSuccess() {
    ParkingLot parkingLot = new ParkingLot(1);
    Attendant attendant = new Attendant(List.of(parkingLot));

    boolean isDirectedSuccessfully = attendant.direct(Attendant.ORIGINAL_ORDER);

    assertTrue(isDirectedSuccessfully);
  }

  @Test
  void directCarWhenNoSpotAvailableShouldFail() {
    ParkingLot parkingLot = new ParkingLot(1);
    Attendant attendant = new Attendant(List.of(parkingLot));

    attendant.direct(Attendant.ORIGINAL_ORDER);
    boolean isDirectedFailure = attendant.direct(Attendant.ORIGINAL_ORDER);

    assertFalse(isDirectedFailure);
  }

  @Test
  void directCarsWhenLotsAreAvailable() {
    ParkingLot parkingLot1 = new ParkingLot(1);
    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.direct(Attendant.ORIGINAL_ORDER);
    boolean isDirectedSuccessfully = attendant.direct(Attendant.ORIGINAL_ORDER);

    assertTrue(isDirectedSuccessfully);
  }

  @Test
  void directCarToFirstAvailableFreeLot() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.direct(Attendant.ORIGINAL_ORDER);
    attendant.direct(Attendant.ORIGINAL_ORDER);

    verify(parkingLot1, times(2)).park();
  }

  @Test
  void directCarToLotWithLeastFreeSpace() {
    ParkingLot parkingLot1 = new ParkingLot(2);

    ParkingLot parkingLot2 = spy(new ParkingLot(1));

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.direct(Attendant.MAXIMUM_FREE_SLOTS);

    verify(parkingLot2, times(1)).park();
  }

  @Test
  void directCarToLotWithMostFreeSpace() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.direct(Attendant.MAXIMUM_FILLED_SLOTS);

    verify(parkingLot1, times(1)).park();
  }

  @Test
  void directCarShouldFailWhenAllLotsFilled() {
    ParkingLot parkingLot1 = spy(new ParkingLot(2));

    ParkingLot parkingLot2 = new ParkingLot(1);

    Attendant attendant = new Attendant(List.of(parkingLot1, parkingLot2));

    attendant.direct(Attendant.MAXIMUM_FILLED_SLOTS);
    attendant.direct(Attendant.MAXIMUM_FREE_SLOTS);
    attendant.direct(Attendant.MAXIMUM_FREE_SLOTS);
    assertFalse(attendant.direct(Attendant.MAXIMUM_FREE_SLOTS));
  }
}
