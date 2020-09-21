package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class Main {

    //Zmienna do rozróżniania obiektów
    public static String INHERITANCE_ROOT = "java.lang.Object";

    public static <e> void getClassTree(ArrayList obj) {
        for(int iobj = 0; iobj<obj.size(); iobj++) {
            ArrayList classList = new ArrayList();
            ArrayList nameFieldList = new ArrayList();
            ArrayList typeFieldList = new ArrayList();
            ArrayList valueInsert = new ArrayList();
            ArrayList oneToOne = new ArrayList();
            ArrayList oneToManyTable = new ArrayList();
            ArrayList oneToManyField = new ArrayList();
            Field myField = null;


            // pętla która pobiera klasy użyte do stworzenia obiektu
            for (Class myClass = obj.get(iobj).getClass(); !myClass.getName().equals(INHERITANCE_ROOT); myClass = myClass.getSuperclass()) {
                //pobieranie nazwy klasy
                classList.add(myClass.getSimpleName());
                // pobieranie pol klasy
                Field[] classField = myClass.getDeclaredFields();
                //petla ktora operuje na polach

                for (int i = 0; i < classField.length; i++) {
                    //pobieranie path dla pola
                    String nameField = classField[i].toString();
                    String typeField = classField[i].toString();
                    String typ = "";

                    if(classField[i].isAnnotationPresent(com.company.OneToOne.class)) {
                        com.company.OneToOne databaseField = classField[i].getAnnotation(com.company.OneToOne.class);
                        oneToOne.add(databaseField.tableName());
                        System.out.println("Field name: "+classField[i].getName() + " | Table name: " + databaseField.tableName()); }
                    if(classField[i].isAnnotationPresent(com.company.OneToMany.class)) {
                        com.company.OneToMany databaseField = classField[i].getAnnotation(com.company.OneToMany.class);
                        String fieldReferences = classField[i].getName();
                        String tableReferences = databaseField.tableName();
                        oneToManyTable.add(tableReferences);
                        oneToManyField.add(fieldReferences);
                        System.out.println("Field name: "+classField[i].getName() + " | Table name: " + databaseField.tableName()); }

                    // usuwanie sciezki pola by zostala tylko nazwa
                    int index = nameField.lastIndexOf('.');
                    nameField = nameField.substring(index + 1);
                    nameFieldList.add(nameField);
                    System.out.println(typeField);
                    // jezeli path pola ma w sobie string zamien na typ TEXT dla BazyDanych
                    if (typeField.contains("String")) {
                        typ = "TEXT";
                        typeFieldList.add("TEXT"); }
                    if (typeField.contains("boolean")) {
                        typ = "BOOLEAN";
                        typeFieldList.add("BOOLEAN"); }
                    if (typeField.contains("double")) {
                        typ = "BIGINT";
                        typeFieldList.add("BIGINT"); }
                    if (typeField.contains("float")) {
                        typ = "REAL";
                        typeFieldList.add("REAL"); }
                    // etc dla int
                    if (typeField.contains("int")) {
                        typ = "integer";
                        typeFieldList.add("integer"); }
                    try {
                        myField = myClass.getDeclaredField(nameField);
                        myField.setAccessible(true);
                        // wartosc dla argumentu obudowa by byla interptetowana w poleceniu insert
                        if (typ.equals("TEXT")) {
                            valueInsert.add("\'" + myField.get(obj.get(iobj)).toString() + "\'");
                        } else {
                            valueInsert.add(myField.get(obj.get(iobj)).toString());
                        } } catch (NoSuchFieldException c) { valueInsert.add("null");
                    }
                    catch (NullPointerException c) {
                        valueInsert.add("null"); }
                    catch (IllegalAccessException e) {
                        e.printStackTrace(); } } }
            // wypisanie typow oraz nazw pol
            System.out.println(typeFieldList);
            System.out.println(nameFieldList);
            // petla typow dla bazy dancyh
            ArrayList argumentsCrateTable = new ArrayList();
            for (int i = 0; i < typeFieldList.size(); i = i + 1) {
                argumentsCrateTable.add(nameFieldList.get(i) + " " + typeFieldList.get(i));
            }
            DataBase dataBase = new DataBase();
            dataBase.connection();
            //dataBase.dropDatabase(classList.get(0).toString());
            dataBase.createNewTable(classList.get(0).toString(), argumentsCrateTable);
            if(oneToOne.size()>0) {
                for(int i = 0; i< oneToOne.size(); i++)
                    try {
                        dataBase.addOneToOne(classList.get(0).toString(), oneToOne.get(i).toString());
                    }catch ( Exception e) {
                        System.out.println("być może taka relacja już występuje");
                        e.printStackTrace(); }
            }
            if(oneToManyField.size()>0) {
                for(int i = 0; i< oneToManyTable.size(); i++)
                    try {
                    dataBase.addOneToMany(classList.get(0).toString(), oneToManyField.get(i).toString(), oneToManyTable.get(i).toString());
                }catch ( Exception e) {
                    System.out.println("być może taka relacja już występuje");
                    e.printStackTrace(); }
            }
            String type = "\'";
            for (int i = 0; i < classList.size(); i++) {
                type += classList.get(i) + ".";
            }
            type += "\'";
            // zapis do bazy danch danych
            dataBase.insertDatabase(obj.get(iobj).getClass().getSimpleName(), valueInsert, type);
        } }


    static ArrayList<Object> listOfObjects  = new ArrayList<Object>();
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        listOfObjects.add(new Tak("asd", 1, 2,3, true));
        listOfObjects.add(new TryClass("tak", 1, 2, true));
        listOfObjects.add(new Reference(43));
        listOfObjects.add(new ReferExample(43));

        getClassTree(
                listOfObjects
        );
}
}

class TryClass extends Car {
    @OneToOne(tableName = "Tak")
    private String field;
    private int age;
    boolean tak;
    public TryClass(String field, int age, int silnik, boolean tak) {

        this.field = field;
        this.age = age;
        this.silnik = silnik;
        this.tak= tak;


    }
}