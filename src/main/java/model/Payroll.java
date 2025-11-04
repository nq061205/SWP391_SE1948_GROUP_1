/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class Payroll {

    private int payrollId;
    private Employee employee;
    private double totalWorkDay;
    private double totalOTHours;
    private double regularSalary;
    private double otEarning;
    private double insuranceBase;
    private double si;
    private double hi;
    private double ui;
    private double taxIncome;
    private double tax;
    private int month;
    private int year;
    private boolean isPaid;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Payroll() {
    }

    public Payroll(int payrollId, Employee employee, double totalWorkDay, double totalOTHours, double regularSalary, double otEarning, double insuranceBase, double si, double hi, double ui, double taxIncome, double tax, int month, int year, boolean isPaid, Timestamp createdAt, Timestamp updatedAt) {
        this.payrollId = payrollId;
        this.employee = employee;
        this.totalWorkDay = totalWorkDay;
        this.totalOTHours = totalOTHours;
        this.regularSalary = regularSalary;
        this.otEarning = otEarning;
        this.insuranceBase = insuranceBase;
        this.si = si;
        this.hi = hi;
        this.ui = ui;
        this.taxIncome = taxIncome;
        this.tax = tax;
        this.month = month;
        this.year = year;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getTotalWorkDay() {
        return totalWorkDay;
    }

    public void setTotalWorkDay(double totalWorkDay) {
        this.totalWorkDay = totalWorkDay;
    }

    public double getTotalOTHours() {
        return totalOTHours;
    }

    public void setTotalOTHours(double totalOTHours) {
        this.totalOTHours = totalOTHours;
    }

    public double getRegularSalary() {
        return regularSalary;
    }

    public void setRegularSalary(double regularSalary) {
        this.regularSalary = regularSalary;
    }

    public double getOtEarning() {
        return otEarning;
    }

    public void setOtEarning(double otEarning) {
        this.otEarning = otEarning;
    }

    public double getInsuranceBase() {
        return insuranceBase;
    }

    public void setInsuranceBase(double insuranceBase) {
        this.insuranceBase = insuranceBase;
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

    public double getTaxIncome() {
        return taxIncome;
    }

    public void setTaxIncome(double taxIncome) {
        this.taxIncome = taxIncome;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
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
        return "Payroll{" + "payrollId=" + payrollId + ", employee=" + employee + ", totalWorkDay=" + totalWorkDay + ", totalOTHours=" + totalOTHours + ", regularSalary=" + regularSalary + ", otEarning=" + otEarning + ", insuranceBase=" + insuranceBase + ", si=" + si + ", hi=" + hi + ", ui=" + ui + ", taxIncome=" + taxIncome + ", tax=" + tax + ", month=" + month + ", year=" + year + ", isPaid=" + isPaid + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

}
