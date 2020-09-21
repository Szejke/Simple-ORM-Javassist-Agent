package other;


import java.util.ArrayList;


public class Main {
    static ArrayList<Object> listOfObjects  = new ArrayList();
    public static void main(String[] args)  {
        listOfObjects.add(new Room(32213 , "Living room1", 2342, "jre",132432));
        listOfObjects.add(new Room(467, "Living room2", 2342, "jre",132432));
        listOfObjects.add(new Room(467, "Living room3", 2342, "jre",132432));
        listOfObjects.add(new Stuff(7869,"I am", 54, "BMW1", 250, "tfda", 6785686));
        listOfObjects.add(new Stuff(123,"I am", 54, "BMW2", 768567, "tfda", 456546754));
        listOfObjects.add(new Stuff(123,"I am", 54, "BMW3", 768567, "tfda", 456546754));
        listOfObjects.add(new Car(534367 , "BMW-1", 300, "nso", 433334));
        listOfObjects.add(new Car(567 , "Audi-1", 300, "nso", 433334));
        listOfObjects.add(new Waterworks(32234 , "ghj",12334554));
        listOfObjects.add(new Waterworks(32234 , "asgfdfh",12334554));
        listOfObjects.add(new PowerStation(32 , "power1", 122343));
        listOfObjects.add(new PowerStation(32 , "power2", 122343));
        listOfObjects.add(new PowerStation(32 , "power3", 122343));
        listOfObjects.add(new PowerStation(32 , "power4", 122343));
        listOfObjects.add(new Waterworks(32234 , "Waterworks1",12334554));
        listOfObjects.add(new Waterworks(32234 , "Waterworks3",12334554));
        listOfObjects.add(new Waterworks(32234 , "Waterworks5",12334554));
        listOfObjects.add(new Room(32213 , "room1", 2342, "jre",132432));
        listOfObjects.add(new Room(32213 , "room2", 2342, "jre",132432));
        listOfObjects.add(new Room(32213 , "room3", 2342, "jre",132432));
        listOfObjects.add(new Room(32213 , "room4", 2342, "jre",132432));
        listOfObjects.add(new Room(467, "room5", 2342, "jre",132432));
    }
}
