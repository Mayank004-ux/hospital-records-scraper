package com.mayank.hospitalrecordsscraper.repository;



import com.mayank.hospitalrecordsscraper.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

}