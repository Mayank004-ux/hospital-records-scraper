package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // This class contains business logic and is a service component in the Spring context so manage it   
public class HospitalService {

    private final HospitalRepository hospitalRepository; // Our Service depends on the Repository. 

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }
}