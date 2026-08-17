package com.mayank.hospitalrecordsscraper.scraper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mayank.hospitalrecordsscraper.entity.Hospital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HospitalScraper {

    private final HttpClient httpClient;

    public HospitalScraper() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String fetchPage(String url) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        return response.body();
    }
    public String extractTitle(String html) {

    Document document = Jsoup.parse(html);

    return document.title();
}
public List<Hospital> extractHospitals(String html) {

    Document document = Jsoup.parse(html);

    Elements hospitalCards = document.select(".hospital-card");

    List<Hospital> hospitals = new ArrayList<>();

    for (Element card : hospitalCards) {

        String name = card.select(".hospital-name").text();
        String city = card.select(".city").text();
        String address = card.select(".address").text();
        String phone = card.select(".phone").text();

        Hospital hospital = new Hospital();

        hospital.setName(name);
        hospital.setCity(city);
        hospital.setAddress(address);
        hospital.setPhone(phone);

        hospitals.add(hospital);
    }

    return hospitals;
}
}