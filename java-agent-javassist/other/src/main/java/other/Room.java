package other;

import agent.OneToMany;

public class Room extends Home {
    int mp2;
    @OneToMany( tableName = "Home")
    int id_home;
    String category;

    public Room(int mp2, String category, int allmp2, String name, int surfaceArea) {
        super(allmp2, name, surfaceArea);
    }
}
