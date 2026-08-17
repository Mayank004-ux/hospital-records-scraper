package com.mayank.hospitalrecordsscraper.controller;

import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.scraper.HospitalScraper;
import com.mayank.hospitalrecordsscraper.service.HospitalService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;
    private final HospitalScraper hospitalScraper;

    public HospitalController(
            HospitalService hospitalService,
            HospitalScraper hospitalScraper) {

        this.hospitalService = hospitalService;
        this.hospitalScraper = hospitalScraper;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hospital Records Scraper is running!";
    }

    // =========================
    // Hospital CRUD APIs
    // =========================

    @GetMapping("/api/hospitals")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @PostMapping("/api/hospitals")
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalService.createHospital(hospital);
    }

    @GetMapping("/api/hospitals/{id}")
    public Hospital getHospitalById(@PathVariable Integer id) {
        return hospitalService.getHospitalById(id);
    }

    @PutMapping("/api/hospitals/{id}")
    public Hospital updateHospital(
            @PathVariable Integer id,
            @RequestBody Hospital hospital) {

        return hospitalService.updateHospital(id, hospital);
    }

    @DeleteMapping("/api/hospitals/{id}")
    public String deleteHospital(@PathVariable Integer id) {

        boolean deleted = hospitalService.deleteHospital(id);

        if (!deleted) {
            return "Hospital not found";
        }

        return "Hospital deleted successfully";
    }

    // =========================
    // Scraper Test API
    // =========================

   @GetMapping("/api/scraper/test")
public String testScraper() throws Exception {

    String html = hospitalScraper.fetchPage("https://example.com");

    return hospitalScraper.extractTitle(html);
}
@GetMapping("/api/scraper/local-test")
public List<Hospital> localScraperTest() throws Exception {

    String html = """
            <div class="hospital-card">

                <h2 class="hospital-name">
                    Apollo Hospital
                </h2>

                <p class="city">
                    Delhi
                </p>

                <p class="address">
                    Sarita Vihar, New Delhi
                </p>

                <p class="phone">
                    011-26925858
                </p>

            </div>

            <div class="hospital-card">

                <h2 class="hospital-name">
                    Fortis Hospital
                </h2>

                <p class="city">
                    Gurugram
                </p>

                <p class="address">
                    Sector 44, Gurugram
                </p>

                <p class="phone">
                    0124-4962200
                </p>

            </div>
            """;

    return hospitalScraper.extractHospitals(html);
}
@PostMapping("/api/scraper/save-test")
public List<Hospital> saveScraperTest() throws Exception {

    String html = """
            <div class="hospital-card">

                <h2 class="hospital-name">
                    Apollo Hospital
                </h2>

                <p class="city">
                    Delhi
                </p>

                <p class="address">
                    Sarita Vihar, New Delhi
                </p>

                <p class="phone">
                    011-26925858
                </p>

            </div>

            <div class="hospital-card">

                <h2 class="hospital-name">
                    Fortis Hospital
                </h2>

                <p class="city">
                    Gurugram
                </p>

                <p class="address">
                    Sector 44, Gurugram
                </p>

                <p class="phone">
                    0124-4962200
                </p>

            </div>
            """;

    List<Hospital> hospitals =
            hospitalScraper.extractHospitals(html);

    return hospitalService.saveHospitals(hospitals);
}
}