package com.mayank.hospitalrecordsscraper.controller;

import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.service.HospitalService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hospital Records Scraper is running!";
    }

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
@DeleteMapping("/api/hospitals/{id}")
public String deleteHospital(@PathVariable Integer id) {

    boolean deleted = hospitalService.deleteHospital(id);

    if (!deleted) {
        return "Hospital not found";
    }

    return "Hospital deleted successfully";
}
@PutMapping("/api/hospitals/{id}")
public Hospital updateHospital(
        @PathVariable Integer id,
        @RequestBody Hospital hospital) {

    return hospitalService.updateHospital(id, hospital);
}

}