package ua.goit.PMS.service;

import ua.goit.PMS.model.entity.Sex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SexConverter implements AttributeConverter<Sex, String> {
    @Override
    public String convertToDatabaseColumn(Sex attribute) {

        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case MALE -> "male";
            case FEMALE -> "female";
        };

    }

    @Override
    public Sex convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return switch (dbData) {
            case "male" -> Sex.MALE;
            case "female" -> Sex.FEMALE;
            default -> throw new IllegalArgumentException(dbData + " not supported.");
        };

    }
}
