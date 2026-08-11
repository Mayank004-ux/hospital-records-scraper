package com.mayank.hospitalrecordsscraper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospitals") // This annotation specifies the name of the database table that this entity maps to. In this case, it maps to the "hospitals" table.
public class Hospital {

    @Id // This field is the primary key of the entity. It uniquely identifies each hospital record in the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Don't ask the programmer to assign IDs manually. Database will generate them.
    private Integer id;

    private String name;

    private String city;

    private String address;

    private String phone;

    public Hospital() {
     }
     public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getCity() {
    return city;
}

public void setCity(String city) {
    this.city = city;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

}
