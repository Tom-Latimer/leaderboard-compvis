package com.sweets.leaderboard_compvis.challenges.models.mapper;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter()
public class EMimeTypesConverter implements AttributeConverter<EMimeTypes, String> {

    @Override
    public String convertToDatabaseColumn(EMimeTypes attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public EMimeTypes convertToEntityAttribute(String dbData) {
        return Arrays.stream(EMimeTypes.values())
                .filter(e -> e.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid content type: " + dbData));
    }
}
