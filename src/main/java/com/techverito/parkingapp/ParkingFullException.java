package com.techverito.parkingapp;

public class ParkingFullException extends RuntimeException{

    public ParkingFullException() {
        super("Parking full");
    }
}
