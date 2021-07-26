package ua.goit.PMS.service;

import ua.goit.PMS.model.entity.SkillLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SkillLevelConverter implements AttributeConverter<SkillLevel, String> {
    @Override
    public String convertToDatabaseColumn(SkillLevel attribute) {

        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case JUNIOR -> "Junior";
            case MIDDLE -> "Middle";
            case SENIOR -> "Senior";
        };

    }

    @Override
    public SkillLevel convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return switch (dbData) {
            case "Junior" -> SkillLevel.JUNIOR;
            case "Middle" -> SkillLevel.MIDDLE;
            case "Senior" -> SkillLevel.SENIOR;
            default -> throw new IllegalArgumentException(dbData + " not supported.");
        };

    }
}
