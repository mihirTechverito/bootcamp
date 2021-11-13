package com.techverito.parkingapp;

public class ParkingSpot {

    private boolean isEmpty;

    public ParkingSpot(boolean status) {
        this.isEmpty = status;
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public void park() {
        this.isEmpty = false;
    }
}
