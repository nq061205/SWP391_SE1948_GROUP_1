/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 *
 * @author admin
 */
public class HolidayDAO {

    private Connection con;

    public List<Holiday> getAllHolidays() {
        List<Holiday> list = new ArrayList<>();
        String sql = "SELECT * FROM holiday";
        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Holiday h = new Holiday(
                        rs.getInt("holiday_id"),
                        rs.getDate("date"),
                        rs.getString("name"),
                        rs.getString("source"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Holiday> getAllHolidayByYear(int year) {
        List<Holiday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM holiday WHERE YEAR(date) = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Holiday h = new Holiday(
                        rs.getInt("holiday_id"),
                        rs.getDate("date"),
                        rs.getString("name"),
                        rs.getString("source"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                holidays.add(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holidays;
    }
}
