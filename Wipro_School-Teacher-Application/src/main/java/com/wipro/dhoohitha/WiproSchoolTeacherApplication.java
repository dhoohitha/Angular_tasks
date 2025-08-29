package com.wipro.dhoohitha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wipro.dhoohitha.entities.School;
import com.wipro.dhoohitha.repo.SchoolRepo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class WiproSchoolTeacherApplication implements ApplicationRunner {
    @Autowired
    private SchoolRepo schoolRepository;

    public static void main(String[] args) {
        SpringApplication.run(WiproSchoolTeacherApplication.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Sample data
        School s1 = new School(null, "SRI CHAITANYA SCHOOL", "VIJAYAWADA");
        School s2 = new School(null, "Delhi public school", "Vij");
        schoolRepository.save(s1);
        schoolRepository.save(s2);
    }
}