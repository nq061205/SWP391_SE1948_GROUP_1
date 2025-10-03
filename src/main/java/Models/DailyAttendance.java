/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class DailyAttendance {

    private int id;
    private Employee employee;
    private Date date;
    private double workDay;      // DECIMAL(3,2)
    private Time checkInTime;
    private Time checkOutTime;
    private double otHours;
    private String status;       // Present, Absent, Holiday, Leave
    private boolean isLocked;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public DailyAttendance() {
    }

    public DailyAttendance(int id, Employee emp, Date date, double workDay, Time checkInTime, Time checkOutTime,
            double otHours, String status, boolean isLocked, Timestamp createdAt) {
        this.id = id;
        this.employee = emp;
        this.date = date;
        this.workDay = workDay;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.otHours = otHours;
        this.status = status;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmpId() {
        return employee;
    }

    public void setEmpId(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWorkDay() {
        return workDay;
    }

    public void setWorkDay(double workDay) {
        this.workDay = workDay;
    }

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public double getOtHours() {
        return otHours;
    }

    public void setOtHours(double otHours) {
        this.otHours = otHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "DailyAttendance{" + "id=" + id + ", employee=" + employee + ", date=" + date + ", workDay=" + workDay + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime + ", otHours=" + otHours + ", status=" + status + ", isLocked=" + isLocked + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
