package com.wipro.dhoohitha.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.dhoohitha.entities.*;
import com.wipro.dhoohitha.repo.*;

import java.util.*;

@Service
public class SchoolServiceImp implements SchoolService {

    @Autowired
    private SchoolRepo schoolRepo;

    @Override
    public School save(School school) {
        return schoolRepo.save(school);
    }

    @Override
    public List<School> findAll() {
        return schoolRepo.findAll();
    }

    @Override
    public School findById(Long id) {
        return schoolRepo.findById(id).orElse(null);
    }

    @Override
    public School update(Long id, School school) {
        school.setId(id);
        return schoolRepo.save(school);
    }

    @Override
    public School patchUpdate(Long id, String schoolName) {
        School existing = schoolRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setSchoolName(schoolName);
            return schoolRepo.save(existing);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        schoolRepo.deleteById(id);
    }

	@Override
	public List<School> findBySchoolName(String name) {
		return null;
	}

	@Override
	public List<School> findBySchoolAddress(String address) {
		return null;
	}
}