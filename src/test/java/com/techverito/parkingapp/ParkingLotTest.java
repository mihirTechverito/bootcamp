package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
  void notifyWhenParkingLotInFull() {

  }
}