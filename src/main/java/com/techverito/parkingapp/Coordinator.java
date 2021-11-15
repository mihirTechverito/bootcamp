package com.techverito.parkingapp;

import java.util.List;
import java.util.Optional;

public class Coordinator implements ParkingManagementEmployee {

  private final List<ParkingManagementEmployee> employees;

  public Coordinator(List<ParkingManagementEmployee> employees) {
    this.employees = employees;
  }

  public boolean assignEmployee() {
    if (employees == null || employees.isEmpty()) return false;
    Optional<ParkingManagementEmployee> optionalAttendant =
        employees.stream().filter(ParkingManagementEmployee::confirmFreeSpotsAvailable).findFirst();

    if (optionalAttendant.isPresent()) {
      return optionalAttendant.get().assign();
    }
    return false;
  }

  @Override
  public boolean assign() {
    return assignEmployee();
  }

  @Override
  public boolean confirmFreeSpotsAvailable() {
    return employees.stream().anyMatch(ParkingManagementEmployee::confirmFreeSpotsAvailable);
  }
}
