package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {

  @Test
  void parkingShouldBeSuccessfulWhenAllSpotsAreAvailable() {

      ParkingLot parkingLot = new ParkingLot(1);
      boolean isParked = parkingLot.park();
      assertTrue(isParked);
  }

    @Test
    void parking_2cars_ShouldBeSuccessfulWhen_2_SpotsAreAvailable() {

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park();
        boolean isParked2 = parkingLot.park();

        assertTrue(isParked2);
    }

    @Test
    void parkingShouldFailWhenAllSpotAreOccupied() {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park();
        assertFalse(parkingLot.park());
    }


    @Test
    void ownerShouldBeNotifiedWhenParkingLotIsFull() {
        ParkingLotObserver owner = mock(ParkingLotObserver.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);

        parkingLot.park();
        parkingLot.park();

        verify(owner, times(1)).onParkingFull(parkingLot);
    }

    @Test
    void ownerShouldBeNotifiedOnlyOnceWhenParkingFull() {
        ParkingLotObserver owner = mock(ParkingLotObserver.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);

        parkingLot.park();
        parkingLot.park();
        parkingLot.park();

        verify(owner, times(1)).onParkingFull(parkingLot);
    }

    @Test
    void ownerShouldBeNotifiedOnlyOnceWhenParkingSpotBecomesFree() {
        ParkingLotObserver owner = mock(ParkingLotObserver.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);

        parkingLot.park();
        parkingLot.park();
        parkingLot.unpark();
        parkingLot.unpark();

        verify(owner, times(1)).onParkingAvailable(parkingLot);
    }

    @Test
    void unparkingAlreadyParkedCarShouldBeSSuccessful() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(null);

        parkingLot.park();

        assertTrue(parkingLot.unpark());
    }

    @Test
    void unparkingCarFromEmptyParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addObserver(null);

        parkingLot.park();
        parkingLot.unpark();

        assertFalse(parkingLot.unpark());
    }




}