package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.exception.ScraperException;
import com.mayank.hospitalrecordsscraper.dto.ImportResponse;
import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;
import com.mayank.hospitalrecordsscraper.scraper.HospitalScraper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalScraper hospitalScraper;

    @Value("${scraper.url}")
    private String scraperUrl;

    public HospitalService(
            HospitalRepository hospitalRepository,
            HospitalScraper hospitalScraper) {

        this.hospitalRepository = hospitalRepository;
        this.hospitalScraper = hospitalScraper;
    }

    // =========================
    // Scraper Import
    // =========================

    public ImportResponse scrapeAndImport() {

    try {
        List<Hospital> hospitals =
                hospitalScraper.scrapeWebsite(scraperUrl);

        if (hospitals.isEmpty()) {
            throw new ScraperException(
                    "No hospitals found on scraper website"
            );
        }

        return saveHospitals(hospitals);

    } catch (ScraperException e) {
        throw e;

    } catch (Exception e) {
        throw new ScraperException(
                "Unable to fetch hospital data from scraper website",
                e
        );
    }
}

    // =========================
    // Get All Hospitals
    // =========================

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    // =========================
    // Get Hospital By ID
    // =========================

    public Hospital getHospitalById(Integer id) {

        Optional<Hospital> hospital =
                hospitalRepository.findById(id);

        return hospital.orElse(null);
    }

    // =========================
    // Create Hospital
    // =========================

    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    // =========================
    // Update Hospital
    // =========================

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

    // =========================
    // Delete Hospital
    // =========================

    public boolean deleteHospital(Integer id) {

        if (!hospitalRepository.existsById(id)) {
            return false;
        }

        hospitalRepository.deleteById(id);
        return true;
    }

    // =========================
    // Save Single Hospital
    // =========================

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    // =========================
    // Save Scraped Hospitals
    // =========================

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

    // =========================
    // Hospital Validation
    // =========================

    private boolean isValidHospital(Hospital hospital) {

        return hospital.getName() != null
                && !hospital.getName().isBlank()
                && hospital.getCity() != null
                && !hospital.getCity().isBlank();
    }
}