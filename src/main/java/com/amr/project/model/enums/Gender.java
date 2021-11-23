package com.amr.project.model.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");
    @Enumerated(EnumType.STRING)
    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}