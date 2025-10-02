/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author admin
 */
public class AttendanceRaw {
    private int id;
    private int empId;
    private Date date;
    private Time checkTime;
    private String checkType; // "IN" or "OUT"

    public AttendanceRaw() {
    }

    public AttendanceRaw(int id, int empId, Date date, Time checkTime, String checkType) {
        this.id = id;
        this.empId = empId;
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

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
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
}

