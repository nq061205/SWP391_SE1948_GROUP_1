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
public class Contract {

    private int contractId;
    private Employee employee;
    private String type;
    private Date startDate;
    private Date endDate;
    private String contractImg;
    private Timestamp createdAt;

    public Contract() {
    }

    public Contract(int contractId, Employee employee, String type, Date startDate, Date endDate, String contractImg, Timestamp createdAt) {
        this.contractId = contractId;
        this.employee = employee;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractImg = contractImg;
        this.createdAt = createdAt;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getContractImg() {
        return contractImg;
    }

    public void setContractImg(String contractImg) {
        this.contractImg = contractImg;
    }

    @Override
    public String toString() {
        return "Contract{" + "contractId=" + contractId + ", employee=" + employee + ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + ", createdAt=" + createdAt + ", contractImg=" + contractImg + '}';
    }
    
}
