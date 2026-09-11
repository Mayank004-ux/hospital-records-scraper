package com.mayank.hospitalrecordsscraper.dto;

public class ImportResponse {

    private int totalScraped;
    private int saved;
    private int duplicates;
    private int invalid;

    public ImportResponse(
            int totalScraped,
            int saved,
            int duplicates,
            int invalid) {

        this.totalScraped = totalScraped;
        this.saved = saved;
        this.duplicates = duplicates;
        this.invalid = invalid;
    }

    public int getTotalScraped() {
        return totalScraped;
    }

    public int getSaved() {
        return saved;
    }

    public int getDuplicates() {
        return duplicates;
    }

    public int getInvalid() {
        return invalid;
    }
}