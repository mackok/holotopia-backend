package nhl.stenden.util;

public class DatabaseConstants {

    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:mysql://localhost:3306/holotopia";

    private DatabaseConstants(){
        throw new IllegalStateException("Utility class");
    }
}
