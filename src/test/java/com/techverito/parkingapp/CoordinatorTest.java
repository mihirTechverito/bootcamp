package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoordinatorTest {

  @Test
  void directingCarToAttendantsWithFreeSpotShouldBeSuccess() {
    ParkingLot parkingLot = new ParkingLot(1);
    ParkingLot parkingLot2 = new ParkingLot(2);
    Attendant attendant = new Attendant(List.of(parkingLot));
    Attendant attendant2 = new Attendant(List.of(parkingLot2));
    Coordinator coordinator = new Coordinator(List.of(attendant, attendant2));
    coordinator.assignAttendantV2();
    coordinator.assignAttendantV2();

    assertTrue(coordinator.assignAttendantV2());
  }

  @Test
  void directingCarShouldFailWhenAllAttendantsNotHavingFreeSpot() {
    Attendant attendant = mock(Attendant.class);
    Attendant attendant2 = mock(Attendant.class);
    Coordinator coordinator = new Coordinator(List.of(attendant, attendant2));
    when(attendant.hasFreeSpots()).thenReturn(false);
    when(attendant2.hasFreeSpots()).thenReturn(false);

    assertFalse(coordinator.assignAttendantV2());
  }
}
