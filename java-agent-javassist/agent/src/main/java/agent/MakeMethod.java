package agent;

public class MakeMethod {

    public String makeMainMathod(){
        return "public static void getClassTree(ArrayList obj) { " +
                "for(int iobj = 0; iobj<obj.size(); iobj++) { " +
                "ArrayList classList = new ArrayList(); " +
                "ArrayList nameFieldList = new ArrayList(); " +
                "ArrayList typeFieldList = new ArrayList(); " +
                "ArrayList valueInsert = new ArrayList(); " +
                "ArrayList oneToOne = new ArrayList(); " +
                "ArrayList oneToManyTable = new ArrayList(); " +
                "ArrayList oneToManyField = new ArrayList(); " +
                "Field myField = null; " +
                "for (Class myClass = obj.get(iobj).getClass(); !myClass.getName().equals(INHERITANCE_ROOT); myClass = myClass.getSuperclass()) { " +
                "classList.add(myClass.getSimpleName()); " +
                "Field[] classField = myClass.getDeclaredFields(); " +
                "for (int i = 0; i < classField.length; i++) { " +
                "String nameField = classField[i].toString(); " +
                "String typeField = classField[i].toString(); " +
                "String typ = \"\"; " +
                "if(classField[i].isAnnotationPresent(agent.OneToOne.class)) { " +
                "agent.OneToOne databaseField = classField[i].getAnnotation(agent.OneToOne.class); " +
                "oneToOne.add(databaseField.tableName()); " +
                "System.out.println(\"Field name: \"+classField[i].getName() + \" | Table name: \" + databaseField.tableName()); } " +
                "if(classField[i].isAnnotationPresent(agent.OneToMany.class)) { " +
                "agent.OneToMany databaseField = classField[i].getAnnotation(agent.OneToMany.class); " +
                "String fieldReferences = classField[i].getName(); " +
                "String tableReferences = databaseField.tableName(); " +
                "oneToManyTable.add(tableReferences); " +
                "oneToManyField.add(fieldReferences); " +
                "System.out.println(\"Field name: \"+classField[i].getName() + \" | Table name: \" + databaseField.tableName()); } " +
                "int index = nameField.lastIndexOf('.'); " +
                "nameField = nameField.substring(index + 1); " +
                "nameFieldList.add(nameField); " +
                "System.out.println(typeField); " +
                "if (typeField.contains(\"String\")) {" +
                "typ = \"TEXT\"; " +
                "typeFieldList.add(\"TEXT\"); } " +
                "if (typeField.contains(\"boolean\")) { " +
                "typ = \"BOOLEAN\"; " +
                "typeFieldList.add(\"BOOLEAN\"); } " +
                "if (typeField.contains(\"double\")) { " +
                "typ = \"BIGINT\"; " +
                "typeFieldList.add(\"BIGINT\"); } " +
                "if (typeField.contains(\"float\")) { " +
                "typ = \"REAL\"; " +
                "typeFieldList.add(\"REAL\"); } " +
                "if (typeField.contains(\"int\")) { " +
                "typ = \"integer\"; " +
                "typeFieldList.add(\"integer\"); } " +
                "try { myField = myClass.getDeclaredField(nameField); " +
                "myField.setAccessible(true); " +
                "if (typ.equals(\"TEXT\")) { " +
                "valueInsert.add(\"\\'\" + myField.get(obj.get(iobj)).toString() + \"\\'\"); " +
                "} else { " +
                "valueInsert.add(myField.get(obj.get(iobj)).toString()); " +
                "} } catch (NoSuchFieldException c) { valueInsert.add(\"null\");} " +
                "catch (NullPointerException c) { " +
                "valueInsert.add(\"null\"); } " +
                "catch (IllegalAccessException e) { " +
                "e.printStackTrace(); } } } " +
                "System.out.println(typeFieldList); " +
                "System.out.println(nameFieldList); " +
                "ArrayList argumentsCrateTable = new ArrayList(); " +
                "for (int i = 0; i < typeFieldList.size(); i = i + 1) { " +
                "argumentsCrateTable.add(nameFieldList.get(i) + \" \" + typeFieldList.get(i)); }" +
                "DataBase dataBase = new DataBase(); " +
                "dataBase.connection(); " +
              //  "dataBase.dropDatabase(classList.get(0).toString()); " +
                "dataBase.createNewTable(classList.get(0).toString(), argumentsCrateTable); " +
                "if(oneToOne.size()>0) { " +
                "for(int i = 0; i< oneToOne.size(); i++) " +
                "dataBase.addOneToOne(classList.get(0).toString(), oneToOne.get(i).toString()); }" +
                "if(oneToManyField.size()>0) { " +
                "for(int i = 0; i< oneToManyTable.size(); i++) " +
                "dataBase.addOneToMany(classList.get(0).toString(), oneToManyField.get(i).toString(), oneToManyTable.get(i).toString()); } " +
                "String type = \"\\'\"; " +
                "for (int i = 0; i < classList.size(); i++) { " +
                "type += classList.get(i) + \".\"; }" +
                "type += \"\\'\"; " +
                "dataBase.insertDatabase(obj.get(iobj).getClass().getSimpleName(), valueInsert, type); }}";

    }
}
