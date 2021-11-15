package com.techverito.parkingapp;

import java.util.List;
import java.util.Optional;

public class Coordinator {


  private final List<Attendant> attendants;

  public Coordinator(List<Attendant> attendants) {
    this.attendants = attendants;
  }

  public boolean assignAttendantV2() {
    if (attendants == null || attendants.isEmpty()) return false;
    Optional<Attendant> optionalAttendant = attendants.stream().filter(Attendant::hasFreeSpots).findFirst();

    if(optionalAttendant.isPresent()){
      return optionalAttendant.get().assign(Attendant.NO_SORTING);
    }
    return false;
  }
}
