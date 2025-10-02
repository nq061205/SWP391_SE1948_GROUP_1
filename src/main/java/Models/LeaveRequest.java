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
public class LeaveRequest {

    private int leaveId;
    private Employee employee;
    private String leaveType;   // Annual Leave, Sick, Unpaid, Maternity, Other
    private String reason;
    private double dayRequested;
    private Date startDate;
    private Date endDate;
    private Employee approvedBy;
    private Timestamp approvedAt;
    private String status;      // Pending, Approved, Rejected
    private String note;
    private Timestamp createdAt;

    public LeaveRequest() {
    }

    public LeaveRequest(int leaveId, Employee emp, String leaveType, String reason, double dayRequested,
            Date startDate, Date endDate, Employee approveBy, Timestamp approveAt,
            String status, String note, Timestamp createdAt) {
        this.leaveId = leaveId;
        this.employee = emp;
        this.leaveType = leaveType;
        this.reason = reason;
        this.dayRequested = dayRequested;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approvedBy = approveBy;
        this.approvedAt = approveAt;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public Employee getEmpId() {
        return employee;
    }

    public void setEmpId(Employee emp) {
        this.employee = emp;
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
}
