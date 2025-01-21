package com.example.demo12.Model.details;

import com.example.demo12.Model.Mark.marks;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="student")
public class student {
    @Id
    private String rn_id;
    private String student_name;
    private String skills;
    private String role;
    private String email;
    private String password;
    private String t_id;

    @OneToMany
    @JoinColumn(name="stdent_id",referencedColumnName = "rn_id")
    private List<marks> Marks;


    public List<marks> getMarks() {
        return Marks;
    }

    public void setMarks(List<marks> marks) {
        Marks = marks;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }
    public String getRn_id() {
        return rn_id;
    }

    public void setRn_id(String rn_id) {
        this.rn_id = rn_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
