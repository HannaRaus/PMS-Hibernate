package ua.goit.jdbc.dto;

import java.util.Arrays;

public enum SkillLevel {
    JUNIOR("Junior"),
    MIDDLE("Middle"),
    SENIOR("Senior");

    private final String name;

    SkillLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SkillLevel findByName(String name) {
        return Arrays.stream(SkillLevel.values())
                .filter(level -> level.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Level with name " + name + " doesn't exists"));
    }
}
