/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class LeaveRequest {

    private int leaveId;
    private Employee employee;
    private String leaveType;   
    private String reason;
    private double dayRequested;
    private Date startDate;
    private Date endDate;
    private Employee approvedBy;
    private Timestamp approvedAt;
    private String status;     
    private String note;
    private String systemLog;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public LeaveRequest() {
    }

    public LeaveRequest(int leaveId, Employee employee, String leaveType, String reason, double dayRequested, Date startDate, Date endDate, Employee approvedBy, Timestamp approvedAt, String status, String note, Timestamp createdAt, Timestamp updatedAt) {
        this.leaveId = leaveId;
        this.employee = employee;
        this.leaveType = leaveType;
        this.reason = reason;
        this.dayRequested = dayRequested;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LeaveRequest(int leaveId, Employee employee, String leaveType, String reason, double dayRequested, Date startDate, Date endDate, Employee approvedBy, Timestamp approvedAt, String status, String note, String systemLog, Timestamp createdAt, Timestamp updatedAt) {
        this.leaveId = leaveId;
        this.employee = employee;
        this.leaveType = leaveType;
        this.reason = reason;
        this.dayRequested = dayRequested;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.status = status;
        this.note = note;
        this.systemLog = systemLog;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    
    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getDayRequested() {
        return dayRequested;
    }

    public void setDayRequested(double dayRequested) {
        this.dayRequested = dayRequested;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approveBy) {
        this.approvedBy = approveBy;
    }

    public Timestamp getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Timestamp approveAt) {
        this.approvedAt = approveAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getSystemLog() {
        return systemLog;
    }

    public void setSystemLog(String systemLog) {
        this.systemLog = systemLog;
    }

    @Override
    public String toString() {
        return "LeaveRequest{" + "leaveId=" + leaveId + ", employee=" + employee + ", leaveType=" + leaveType + ", reason=" + reason + ", dayRequested=" + dayRequested + ", startDate=" + startDate + ", endDate=" + endDate + ", approvedBy=" + approvedBy + ", approvedAt=" + approvedAt + ", status=" + status + ", note=" + note + ", systemLog=" + systemLog + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
}