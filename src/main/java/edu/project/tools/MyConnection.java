package edu.project.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    public String url = "jdbc:mysql://localhost:3306/pidev?useSSL=false&serverTimezone=UTC";
    public String login = "root";
    public String pwd = "";

    private Connection cnx;

    public MyConnection() {
        try {
            // charge le driver (optionnel avec JDBC 4+, mais explicite)
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("✓ Connexion établie!");
        } catch (ClassNotFoundException e) {
            System.out.println("✗ Driver JDBC introuvable: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("✗ Erreur de connexion: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return cnx;
    }

    public void closeConnection() {
        try {
            if (cnx != null && !cnx.isClosed()) {
                cnx.close();
                System.out.println("✓ Connexion fermée!");
            }
        } catch (SQLException e) {
            System.out.println("✗ Erreur fermeture connexion: " + e.getMessage());
        }
    }

}
