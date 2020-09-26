package other;

import agent.OneToOne;

public class Car extends Home {
    int engine;
    String model;


    public Car(int engine, String model, int allmp2, String name, int surfaceArea) {
        super(allmp2, name, surfaceArea);
        this.engine = engine;
        this.model = model;
    }
}
