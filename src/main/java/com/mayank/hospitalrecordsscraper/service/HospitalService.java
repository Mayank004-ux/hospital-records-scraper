package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.dto.ImportResponse;
import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Optional<Hospital> hospital =
                hospitalRepository.findById(id);

        return hospital.orElse(null);
    }

    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(
            Integer id,
            Hospital hospitalDetails) {

        Hospital hospital =
                hospitalRepository.findById(id)
                        .orElse(null);

        if (hospital == null) {
            return null;
        }

        hospital.setName(hospitalDetails.getName());
        hospital.setCity(hospitalDetails.getCity());
        hospital.setAddress(hospitalDetails.getAddress());
        hospital.setPhone(hospitalDetails.getPhone());

        return hospitalRepository.save(hospital);
    }

    public boolean deleteHospital(Integer id) {

        if (!hospitalRepository.existsById(id)) {
            return false;
        }

        hospitalRepository.deleteById(id);
        return true;
    }

    

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }


    public ImportResponse saveHospitals(
            List<Hospital> hospitals) {

        List<Hospital> newHospitals =
                new ArrayList<>();

        int duplicates = 0;
        int invalid = 0;

        for (Hospital hospital : hospitals) {

            // Step 1: Validate hospital
            if (!isValidHospital(hospital)) {
                invalid++;
                continue;
            }

            // Step 2: Check duplicate
            boolean exists =
                    hospitalRepository
                            .existsByNameAndCity(
                                    hospital.getName(),
                                    hospital.getCity()
                            );

            if (exists) {
                duplicates++;
                continue;
            }

            // Step 3: Add new hospital
            newHospitals.add(hospital);
        }

        // Step 4: Save new hospitals
        List<Hospital> savedHospitals =
                hospitalRepository.saveAll(newHospitals);

        // Step 5: Create import report
        return new ImportResponse(
                hospitals.size(),
                savedHospitals.size(),
                duplicates,
                invalid
        );
    }

    

    private boolean isValidHospital(Hospital hospital) {

        return hospital.getName() != null
                && !hospital.getName().isBlank()
                && hospital.getCity() != null
                && !hospital.getCity().isBlank();
    }
}