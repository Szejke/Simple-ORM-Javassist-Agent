package other;

public class Stuff extends Car{

    int field2;
    String field1;


    public Stuff(int field2, String field1, int engine, String model, int allmp2, String name, int surfaceArea) {
        super(engine, model, allmp2, name, surfaceArea);
        this.field2 = field2;
        this.field1 = field1;
    }
}
