package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(Integer id) {
        Optional<Hospital> hospital = hospitalRepository.findById(id);

        return hospital.orElse(null);
    }

    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(Integer id, Hospital hospitalDetails) {

        Hospital hospital = hospitalRepository.findById(id)
                .orElse(null);

        if (hospital == null) {
            return null;
        }

         hospital.setName(hospitalDetails.getName());
         hospital.setCity(hospitalDetails.getCity());
         hospital.setAddress(hospitalDetails.getAddress());
         hospital.setPhone(hospitalDetails.getPhone());


        // We'll update the fields of Hospital here.

        return hospitalRepository.save(hospital);
    }

    public boolean deleteHospital(Integer id) {

        if (!hospitalRepository.existsById(id)) {
            return false;
        }

        hospitalRepository.deleteById(id);
        return true;
    }
    
    
}