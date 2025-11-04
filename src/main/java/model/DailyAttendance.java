/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
    private double workDay;      
    private Time checkInTime;
    private Time checkOutTime;
    private double otHours;
    private String status;      
    private boolean isLocked;
    private String note;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public DailyAttendance() {
    }

    public DailyAttendance(int id, Employee employee, Date date, double workDay, Time checkInTime, Time checkOutTime, double otHours, String status, boolean isLocked, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.workDay = workDay;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.otHours = otHours;
        this.status = status;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public DailyAttendance(Employee employee, Date date, double workDay, Time checkInTime, Time checkOutTime, double otHours, String status) {
        this.employee = employee;
        this.date = date;
        this.workDay = workDay;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.otHours = otHours;
        this.status = status;
    }
    
    public DailyAttendance(Employee employee, Date date, double workDay, Time checkInTime, Time checkOutTime, double otHours, String status, String note) {
        this.employee = employee;
        this.date = date;
        this.workDay = workDay;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.otHours = otHours;
        this.status = status;
        this.note = note;
    }

    public DailyAttendance(int id, Employee employee, Date date, double workDay, Time checkInTime, Time checkOutTime, double otHours, String status, boolean isLocked, String note, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.workDay = workDay;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.otHours = otHours;
        this.status = status;
        this.isLocked = isLocked;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DailyAttendance{" + "id=" + id + ", employee=" + employee + ", date=" + date + ", workDay=" + workDay + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime + ", otHours=" + otHours + ", status=" + status + ", isLocked=" + isLocked + ", note=" + note + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
}
