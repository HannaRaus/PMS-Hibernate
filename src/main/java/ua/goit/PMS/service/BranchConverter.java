package ua.goit.PMS.service;

import ua.goit.PMS.model.entity.Branch;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BranchConverter implements AttributeConverter<Branch, String> {
    @Override
    public String convertToDatabaseColumn(Branch attribute) {

        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case JAVA -> "Java";
            case CPLUS -> "C++";
            case CSHARP -> "C#";
            case JS -> "JS";
        };

    }

    @Override
    public Branch convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return switch (dbData) {
            case "Java" -> Branch.JAVA;
            case "C++" -> Branch.CPLUS;
            case "C#" -> Branch.CSHARP;
            case "JS" -> Branch.JS;
            default -> throw new IllegalArgumentException(dbData + " not supported.");
        };

    }
}
