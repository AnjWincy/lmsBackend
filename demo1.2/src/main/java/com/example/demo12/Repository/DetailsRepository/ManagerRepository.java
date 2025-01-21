package com.example.demo12.Repository.DetailsRepository;


import com.example.demo12.Model.details.manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<manager,String> {
    @Query("SELECT l FROM manager l  WHERE l.email = ?1 AND l.password = ?2")
    manager validateManager(String email, String password);

    manager findByEmail(String email);
}
