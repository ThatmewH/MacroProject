package com.example.macroproject.variables;

import java.util.ArrayList;

public class ListVariable extends Variable<ArrayList> {
    //TODO make a superclass called NumberVariable with all the add, sub, mul and divide
    // with a generic extending number
    public ListVariable(String name, ArrayList arrayList) {
        super(name, arrayList);
    }

}
