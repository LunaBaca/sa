package vallegrande.edu.pe.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:mysql://angellovg.c0u8dgzrembt.us-east-1.rds.amazonaws.com:3306/dbangello";
    private static final String USER = "admin"; // cámbialo por tu usuario
    private static final String PASSWORD = "CamiloTech"; // cámbialo por tu clave

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la BD del Restaurante");
        } catch (Exception e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
        }
        return con;
    }
}
