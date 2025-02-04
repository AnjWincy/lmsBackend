package com.example.demo12.Repository.DetailsRepository;


import com.example.demo12.Model.details.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {
    @Query("SELECT l FROM Manager l  WHERE l.email = ?1 AND l.password = ?2")
    Manager validateManager(String email, String password);

    Manager findByEmail(String email);
}
