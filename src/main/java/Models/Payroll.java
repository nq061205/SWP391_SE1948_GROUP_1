/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class Payroll {

    private int payrollId;
    private Employee employee;
    private double totalWorkDay;
    private double totalWorkHours;
    private double si;
    private double hi;
    private double ui;
    private double tax;
    private double totalSalary;
    private Timestamp updatedAt;
    private int month;
    private int year;
    private Timestamp createdAt;

    public Payroll() {
    }

    public Payroll(int payrollId, Employee employee, double totalWorkDay, double totalWorkHours, double si,
            double hi, double ui, double tax, double totalSalary,
            Timestamp updatedAt, int month, int year, Timestamp createdAt) {
        this.payrollId = payrollId;
        this.employee = employee;
        this.totalWorkDay = totalWorkDay;
        this.totalWorkHours = totalWorkHours;
        this.si = si;
        this.hi = hi;
        this.ui = ui;
        this.tax = tax;
        this.totalSalary = totalSalary;
        this.updatedAt = updatedAt;
        this.month = month;
        this.year = year;
        this.createdAt = createdAt;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public Employee getEmpId() {
        return employee;
    }

    public void setEmpId(Employee empId) {
        this.employee = empId;
    }

    public double getTotalWorkDay() {
        return totalWorkDay;
    }

    public void setTotalWorkDay(double totalWorkDay) {
        this.totalWorkDay = totalWorkDay;
    }

    public double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public double getSi() {
        return si;
    }

    public void setSi(double si) {
        this.si = si;
    }

    public double getHi() {
        return hi;
    }

    public void setHi(double hi) {
        this.hi = hi;
    }

    public double getUi() {
        return ui;
    }

    public void setUi(double ui) {
        this.ui = ui;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updateAt) {
        this.updatedAt = updateAt;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Payroll{" + "payrollId=" + payrollId + ", employee=" + employee + ", totalWorkDay=" + totalWorkDay + ", totalWorkHours=" + totalWorkHours + ", si=" + si + ", hi=" + hi + ", ui=" + ui + ", tax=" + tax + ", totalSalary=" + totalSalary + ", updatedAt=" + updatedAt + ", month=" + month + ", year=" + year + ", createdAt=" + createdAt + '}';
    }
    
}
