package agent;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    // ustawienia dla połączniea z bazą danych
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "ppp";
    private ArrayList firldss;

    public Connection conn = null;

    // połączenie
    public void connection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("failed connect");
        } catch (ClassNotFoundException e) {
            System.out.println("failed connect");
            e.printStackTrace();
        }
    }
// tworzenie nowej tabeli
    public void createNewTable(String nameTable, ArrayList filed) {
        String sqlFilds = "";
        for (Object item : filed) {
            sqlFilds = sqlFilds + ", " + item.toString();
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + nameTable + " ( ID SERIAL PRIMARY KEY , TYPE TEXT " + sqlFilds + " );";
        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//usuwanie tabeli
    public void dropDatabase(String nameTable) {
        String sql = "DROP TABLE IF EXISTS " + nameTable + ";";
        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOneToOne(String nameTable, String tableReference){
        String fkNameTable = "fk_"+nameTable;

        String sql = "ALTER TABLE "+ nameTable + " ADD CONSTRAINT " + fkNameTable +" FOREIGN KEY (id) REFERENCES " + tableReference+"(id)" ;
        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("tabela zawiera już ta relacje");
        }
    }


    public void addOneToMany(String nameTable, String field, String tableName){
        String fkNameTable = "fk_"+nameTable;

        String sql = "ALTER TABLE "+ nameTable + " ADD CONSTRAINT " + fkNameTable +" FOREIGN KEY (" + field + ") REFERENCES " + tableName +"(id)" ;
        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("tabela zawiera już ta relacje");
        }
    }

//insert dla tabeli
    public void insertDatabase(String nameTable, ArrayList arg, String type) {

        String arguments = "";
        for (Object argum : arg) {
            arguments = arguments + " , " + argum.toString();

        }
        String sql = "INSERT INTO " + nameTable + " VALUES ( DEFAULT, " + type + arguments + ") ";

        System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        conn.close();
    }


}
