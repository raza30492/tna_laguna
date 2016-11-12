/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tna.dto;

/**
 *
 * @author Md Zahid Raza
 */
public class User {
    
    private String employeeName;
    private String employeeId;
    private String email;
    private String dob;
    private String role;
    
    public User(String employeeName,String employeeId,String email,String dob,String role){
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.email = email;
        this.dob = dob;
        this.role = role;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
}
