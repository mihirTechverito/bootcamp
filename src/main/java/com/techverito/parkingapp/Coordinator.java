package com.techverito.parkingapp;

import org.w3c.dom.Node;


import java.util.List;
import java.util.Optional;

public class Coordinator implements ParkingWorker {

  private final List<ParkingWorker> workers;

  public Coordinator(List<ParkingWorker> workers) {
    this.workers = workers;
  }

  public boolean assignEmployee() {
    if (workers == null || workers.isEmpty()) return false;
    Optional<ParkingWorker> parkingManagementEmployee =
        workers.stream().filter(ParkingWorker::confirmFreeSpotsAvailable).findFirst();

    if (parkingManagementEmployee.isPresent()) {
      return parkingManagementEmployee.get().assign();
    }
    return false;
  }

  @Override
  public boolean assign() {
    return assignEmployee();
  }

  @Override
  public boolean confirmFreeSpotsAvailable() {
    return workers.stream().anyMatch(ParkingWorker::confirmFreeSpotsAvailable);
  }
}
