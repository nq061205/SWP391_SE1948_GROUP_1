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
    private Employee employee;
    private Date date;
    private double otHours;
    private Employee approvedBy;   // can be null
    private Timestamp approvedAt; // can be null
    private String status;       // Pending, Approved, Rejected
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public OTRequest() {
    }

    public OTRequest(int otId, Employee employee, Date date, double otHours, Employee approvedBy, Timestamp approvedAt, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.otId = otId;
        this.employee = employee;
        this.date = date;
        this.otHours = otHours;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getOtId() {
        return otId;
    }

    public void setOtId(int otId) {
        this.otId = otId;
    }

    public Employee getEmpId() {
        return employee;
    }

    public void setEmpId(Employee empId) {
        this.employee = empId;
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

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OTRequest{" + "otId=" + otId + ", employee=" + employee + ", date=" + date + ", otHours=" + otHours + ", approvedBy=" + approvedBy + ", approvedAt=" + approvedAt + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
}
