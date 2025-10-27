package model;

import java.sql.Date;

public class Dependant {
    private int dependantId;
    private Employee employee;
    private String name;
    private String relationship;
    private Date dob;
    private Boolean gender; 
    private String phone;

    public Dependant() {
    }

    public Dependant(int dependantId, Employee employee, String name, String relationship,
                     Date dob, Boolean gender, String phone) {
        this.dependantId = dependantId;
        this.employee = employee;
        this.name = name;
        this.relationship = relationship;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
    }

    public int getDependantId() {
        return dependantId;
    }

    public void setDependantId(int dependantId) {
        this.dependantId = dependantId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Dependant{" + "dependantId=" + dependantId + ", employee=" + employee + ", name=" + name + ", relationship=" + relationship + ", dob=" + dob + ", gender=" + gender + ", phone=" + phone + '}';
    }

   
}
