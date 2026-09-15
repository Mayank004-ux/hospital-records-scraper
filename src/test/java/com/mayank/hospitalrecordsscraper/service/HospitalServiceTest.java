package com.mayank.hospitalrecordsscraper.service;

import com.mayank.hospitalrecordsscraper.dto.ImportResponse;
import com.mayank.hospitalrecordsscraper.entity.Hospital;
import com.mayank.hospitalrecordsscraper.exception.ScraperException;
import com.mayank.hospitalrecordsscraper.repository.HospitalRepository;
import com.mayank.hospitalrecordsscraper.scraper.HospitalScraper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HospitalServiceTest {

    @Test
    void getAllHospitals_shouldReturnHospitals() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        Hospital hospital = new Hospital();
        hospital.setName("Apollo Hospital");
        hospital.setCity("Indore");

        when(repository.findAll())
                .thenReturn(List.of(hospital));

        HospitalService service =
                new HospitalService(repository, null);

        List<Hospital> result =
                service.getAllHospitals();

        assertEquals(1, result.size());

        assertEquals(
                "Apollo Hospital",
                result.get(0).getName()
        );
    }

    @Test
    void getHospitalById_shouldReturnHospital() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        Hospital hospital = new Hospital();
        hospital.setId(1);
        hospital.setName("Apollo Hospital");
        hospital.setCity("Indore");

        when(repository.findById(1))
                .thenReturn(Optional.of(hospital));

        HospitalService service =
                new HospitalService(repository, null);

        Hospital result =
                service.getHospitalById(1);

        assertEquals(1, result.getId());

        assertEquals(
                "Apollo Hospital",
                result.getName()
        );
    }

    @Test
    void getHospitalById_whenHospitalDoesNotExist_shouldReturnNull() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        when(repository.findById(999))
                .thenReturn(Optional.empty());

        HospitalService service =
                new HospitalService(repository, null);

        Hospital result =
                service.getHospitalById(999);

        assertNull(result);
    }

    @Test
    void createHospital_shouldSaveHospital() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        Hospital hospital = new Hospital();
        hospital.setName("Fortis Hospital");
        hospital.setCity("Indore");

        when(repository.save(hospital))
                .thenReturn(hospital);

        HospitalService service =
                new HospitalService(repository, null);

        Hospital result =
                service.createHospital(hospital);

        assertEquals(
                "Fortis Hospital",
                result.getName()
        );

        assertEquals(
                "Indore",
                result.getCity()
        );
    }

    @Test
    void deleteHospital_whenHospitalExists_shouldReturnTrue() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        when(repository.existsById(1))
                .thenReturn(true);

        HospitalService service =
                new HospitalService(repository, null);

        boolean result =
                service.deleteHospital(1);

        assertEquals(true, result);
    }

    @Test
    void deleteHospital_whenHospitalDoesNotExist_shouldReturnFalse() {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        when(repository.existsById(999))
                .thenReturn(false);

        HospitalService service =
                new HospitalService(repository, null);

        boolean result =
                service.deleteHospital(999);

        assertEquals(false, result);
    }

    @Test
    void scrapeAndImport_whenHospitalsFound_shouldSaveHospitals() throws Exception {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        HospitalScraper scraper =
                mock(HospitalScraper.class);

        Hospital hospital = new Hospital();
        hospital.setName("Apollo Hospital");
        hospital.setCity("Indore");

        when(scraper.scrapeWebsite(null))
                .thenReturn(List.of(hospital));

        when(repository.existsByNameAndCity(
                "Apollo Hospital",
                "Indore"))
                .thenReturn(false);

        when(repository.saveAll(List.of(hospital)))
                .thenReturn(List.of(hospital));

        HospitalService service =
                new HospitalService(repository, scraper);

        ImportResponse result =
                service.scrapeAndImport();

        assertEquals(1, result.getTotalScraped());
        assertEquals(1, result.getSaved());
        assertEquals(0, result.getDuplicates());
        assertEquals(0, result.getInvalid());
    }

    @Test
    void scrapeAndImport_whenNoHospitalsFound_shouldThrowException()  throws Exception {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        HospitalScraper scraper =
                mock(HospitalScraper.class);

        when(scraper.scrapeWebsite(null))
                .thenReturn(List.of());

        HospitalService service =
                new HospitalService(repository, scraper);

        ScraperException exception =
                assertThrows(
                        ScraperException.class,
                        service::scrapeAndImport
                );

        assertEquals(
                "No hospitals found on scraper website",
                exception.getMessage()
        );
    }

    @Test
    void scrapeAndImport_whenScraperFails_shouldThrowException()  throws Exception {

        HospitalRepository repository =
                mock(HospitalRepository.class);

        HospitalScraper scraper =
                mock(HospitalScraper.class);

        when(scraper.scrapeWebsite(null))
                .thenThrow(
                        new RuntimeException("Connection failed")
                );

        HospitalService service =
                new HospitalService(repository, scraper);

        ScraperException exception =
                assertThrows(
                        ScraperException.class,
                        service::scrapeAndImport
                );

        assertEquals(
                "Unable to fetch hospital data from scraper website",
                exception.getMessage()
        );
    }
    @Test
void updateHospital_whenHospitalExists_shouldUpdateHospital() {

    HospitalRepository repository =
            mock(HospitalRepository.class);

    Hospital existingHospital = new Hospital();
    existingHospital.setId(1);
    existingHospital.setName("Apollo Hospital");
    existingHospital.setCity("Indore");

    Hospital hospitalDetails = new Hospital();
    hospitalDetails.setName("Apollo Hospitals");
    hospitalDetails.setCity("Bhopal");
    hospitalDetails.setAddress("MG Road");
    hospitalDetails.setPhone("9999999999");

    when(repository.findById(1))
            .thenReturn(Optional.of(existingHospital));

    when(repository.save(existingHospital))
            .thenReturn(existingHospital);

    HospitalService service =
            new HospitalService(repository, null);

    Hospital result =
            service.updateHospital(1, hospitalDetails);

    assertEquals(
            "Apollo Hospitals",
            result.getName()
    );

    assertEquals(
            "Bhopal",
            result.getCity()
    );

    assertEquals(
            "MG Road",
            result.getAddress()
    );

    assertEquals(
            "9999999999",
            result.getPhone()
    );
}
@Test
void updateHospital_whenHospitalDoesNotExist_shouldReturnNull() {

    HospitalRepository repository =
            mock(HospitalRepository.class);

    when(repository.findById(999))
            .thenReturn(Optional.empty());

    Hospital hospitalDetails = new Hospital();
    hospitalDetails.setName("Apollo Hospital");
    hospitalDetails.setCity("Indore");

    HospitalService service =
            new HospitalService(repository, null);

    Hospital result =
            service.updateHospital(999, hospitalDetails);

    assertNull(result);
}
@Test
void saveHospitals_whenHospitalIsInvalid_shouldCountAsInvalid() {

    HospitalRepository repository =
            mock(HospitalRepository.class);

    Hospital invalidHospital = new Hospital();
    invalidHospital.setName("");
    invalidHospital.setCity("Indore");

    HospitalService service =
            new HospitalService(repository, null);

    ImportResponse result =
            service.saveHospitals(List.of(invalidHospital));

    assertEquals(1, result.getTotalScraped());
    assertEquals(0, result.getSaved());
    assertEquals(0, result.getDuplicates());
    assertEquals(1, result.getInvalid());
}
@Test
void saveHospitals_whenHospitalIsDuplicate_shouldCountAsDuplicate() {

    HospitalRepository repository =
            mock(HospitalRepository.class);

    Hospital duplicateHospital = new Hospital();
    duplicateHospital.setName("Apollo Hospital");
    duplicateHospital.setCity("Indore");

    when(repository.existsByNameAndCity(
            "Apollo Hospital",
            "Indore"))
            .thenReturn(true);

    HospitalService service =
            new HospitalService(repository, null);

    ImportResponse result =
            service.saveHospitals(List.of(duplicateHospital));

    assertEquals(1, result.getTotalScraped());
    assertEquals(0, result.getSaved());
    assertEquals(1, result.getDuplicates());
    assertEquals(0, result.getInvalid());
}
}