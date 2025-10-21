/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author admin
 */
public class AttendanceRaw {
    private int id;
    private Employee employee;
    private Date date;
    private Time checkTime;
    private String checkType; // "IN" or "OUT"

    public AttendanceRaw() {
    }

    public AttendanceRaw(int id, Employee emp, Date date, Time checkTime, String checkType) {
        this.id = id;
        this.employee = emp;
        this.date = date;
        this.checkTime = checkTime;
        this.checkType = checkType;
    }

    public AttendanceRaw(Employee employee, Date date, Time checkTime, String checkType) {
        this.employee = employee;
        this.date = date;
        this.checkTime = checkTime;
        this.checkType = checkType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmp() {
        return employee;
    }

    public void setEmp(Employee emp) {
        this.employee = emp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Time checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    @Override
    public String toString() {
        return "AttendanceRaw{" + "id=" + id + ", employee=" + employee + ", date=" + date + ", checkTime=" + checkTime + ", checkType=" + checkType + '}';
    }
}

