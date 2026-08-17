package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;
import com.mayank.hospitalrecordsscraper.scraper.HospitalScraper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalScraper hospitalScraper;

    public HospitalService(HospitalRepository hospitalRepository ,  HospitalScraper hospitalScraper) {
        this.hospitalRepository = hospitalRepository;
         this.hospitalScraper = hospitalScraper;
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
    public Hospital saveHospital(Hospital hospital) {
    return hospitalRepository.save(hospital);
}
public List<Hospital> saveHospitals(List<Hospital> hospitals) {
    return hospitalRepository.saveAll(hospitals);
}
    public List<Hospital> scrapeAndSave(String html) {

    List<Hospital> hospitals =
            hospitalScraper.extractHospitals(html);

    return hospitalRepository.saveAll(hospitals);
}
}