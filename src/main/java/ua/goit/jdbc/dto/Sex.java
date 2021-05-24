package ua.goit.jdbc.dto;

import java.util.Arrays;

public enum Sex {
    MALE("male"),
    FEMALE("female");

    private final String name;

    Sex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Sex findByName(String name) {
        return Arrays.stream(Sex.values())
                .filter(gender -> gender.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gender with name " + name + " doesn't exists"));
    }
}
