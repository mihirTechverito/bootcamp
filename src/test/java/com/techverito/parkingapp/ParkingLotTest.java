package com.techverito.parkingapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

  @Test
  void parkCarInEmptySpace() {

      ParkingLot parkingLot = new ParkingLot(2);
      parkingLot.createSpots();
      boolean isParked = parkingLot.park();
      assertTrue(isParked);
  }

    @Test
    void parkCarWhenNoSpaceEmpty() {

        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.createSpots();
        parkingLot.park();
        assertThrows(ParkingFullException.class, parkingLot::park);
    }
}