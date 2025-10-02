/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class OTRequest {

    private int otId;
    private int empId;
    private Date date;
    private double otHours;
    private Integer approvedBy;   // can be null
    private Timestamp approvedAt; 
    private String status;       
    private Timestamp createdAt;

    public OTRequest() {
    }

    public OTRequest(int otId, int empId, Date date, double otHours, Integer approvedBy, Timestamp approvedAt, String status, Timestamp createdAt) {
        this.otId = otId;
        this.empId = empId;
        this.date = date;
        this.otHours = otHours;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getOtId() {
        return otId;
    }

    public void setOtId(int otId) {
        this.otId = otId;
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

    public double getOtHours() {
        return otHours;
    }

    public void setOtHours(double otHours) {
        this.otHours = otHours;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approvedAt) {
        this.approvedAt = approvedAt;
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
}
