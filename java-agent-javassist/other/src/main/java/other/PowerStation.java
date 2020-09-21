package other;

import agent.OneToMany;

public class PowerStation extends City {
    @OneToMany( tableName = "Waterworks")
    int power;

    public PowerStation(int power, String name, int surfaceArea) {
        super(name, surfaceArea);
        this.power = power;
    }
}
