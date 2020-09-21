package other;


public class Waterworks extends City {
    int stuffRelation;
    int supply;

    public Waterworks(int supply, String name, int surfaceArea) {
        super(name, surfaceArea);
        this.supply = supply;
    }
}
