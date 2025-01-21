package com.example.demo12.Model.details;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class trainer {
    @Id
    private String trainer_id;
    private String name;
    private String role;
    private String ph_no;
    private String email;
    private String password;

    @OneToMany
    @JoinColumn(name="t_id",referencedColumnName = "trainer_id")
    private List<student> Students;

    public List<student> getStudents() {
        return Students;
    }

    public void setStudents(List<student> students) {
        Students = students;
    }

    public String getPh_no() {
        return ph_no;
    }

    public void setPh_no(String ph_no) {
        this.ph_no = ph_no;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }
}
