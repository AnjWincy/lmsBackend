package com.example.demo12.Model.details;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Manager {

    @Id
    private String m_id;
    private String manager_name;
    private String email;

    private String password;


    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }
}
