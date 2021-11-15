package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CoordinatorTest {

  @Test
  void directingCarToAttendantsWithNoFreeSpotShouldFail() {

    ParkingLot parkingLot = mock(ParkingLot.class);
    when(parkingLot.isAvailable()).thenReturn(false);
    Attendant attendant = new Attendant(List.of(parkingLot));
    Coordinator coordinator = new Coordinator(List.of(attendant));


    assertFalse(coordinator.assignEmployee());
  }

  @Test
  void directingCarToAttendantsWithFreeSpotAvailableShouldBeSucceed() {
    Attendant attendant = mock(Attendant.class);
    Attendant attendant2 = mock(Attendant.class);
    Coordinator coordinator = new Coordinator(List.of(attendant, attendant2));

    when(attendant.confirmFreeSpotsAvailable()).thenReturn(false);
    when(attendant2.confirmFreeSpotsAvailable()).thenReturn(true);
    when(attendant2.assign()).thenReturn(true);

    assertTrue(coordinator.assignEmployee());
    verify(attendant2,times(1)).assign();
  }

  @Test
  void directingCarToCoordinatorWithFreeSpotAvailableShouldSucceed() {
    Attendant attendant = mock(Attendant.class);
    Attendant attendant2 = mock(Attendant.class);
    Attendant attendant3 = mock(Attendant.class);
    Coordinator coordinator = new Coordinator(List.of(attendant, attendant2));
    Coordinator coordinator2 = new Coordinator(List.of(attendant3,coordinator));
    when(attendant3.confirmFreeSpotsAvailable()).thenReturn(false);
    when(attendant.confirmFreeSpotsAvailable()).thenReturn(false);
    when(attendant2.confirmFreeSpotsAvailable()).thenReturn(true);
    when(attendant2.assign()).thenReturn(true);

    assertTrue(coordinator2.assignEmployee());
    verify(attendant2,times(1)).assign();
  }
}
