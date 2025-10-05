/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Department;
import Models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Nguyen Dinh Quy HE190184
 */
public class UserDAO extends DBContext {

    
    
    private Connection connection;

    public UserDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
        }
    }

    
    
    
    
    
}
