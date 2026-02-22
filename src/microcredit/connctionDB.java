/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package microcredit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connctionDB {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/microcredit?useSSL=false&serverTimezone=UTC",
                "root",
                "1234");       
    }
}