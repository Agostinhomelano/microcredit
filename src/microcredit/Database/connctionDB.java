/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package microcredit.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connctionDB {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app?useSSL=false&serverTimezone=UTC",
                "root",
                "5421");
    }
}