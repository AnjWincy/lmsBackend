package com.example.demo12.Repository.Mark;

import com.example.demo12.Model.Mark.CalendarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarModel,Long> {
}
