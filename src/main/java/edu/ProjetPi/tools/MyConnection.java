package edu.ProjetPi.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    public String url="jdbc:mysql://localhost:3306/pidev";
    public String login="root";
    public String pwd ="";


    private Connection cnx;
    public MyConnection() {
        try {
            cnx = DriverManager.getConnection(url,login,pwd);
            System.out.println("connection établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
