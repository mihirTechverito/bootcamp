package com.techverito;

public class Tservice {

    Trepository trepository;

    public Tservice(Trepository trepository) {
        this.trepository = trepository;
    }

    public int taxApplied(){
        return 5 * trepository.taxRate();
    }
}
