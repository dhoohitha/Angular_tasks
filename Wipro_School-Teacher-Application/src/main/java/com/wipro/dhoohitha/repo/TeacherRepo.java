package com.wipro.dhoohitha.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.dhoohitha.entities.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Teacher findByTeacherName(String teachername);
}

