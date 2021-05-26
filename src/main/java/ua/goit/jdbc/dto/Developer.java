package ua.goit.jdbc.dto;

import java.util.List;
import java.util.Objects;

public class Developer {
    private long id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private double salary;
    List<Skill> skills;

    public Developer() {
    }

    public Developer(long id, String firstName, String lastName, Sex gender, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = gender;
        this.salary = salary;
    }

    public Developer(String firstName, String lastName, Sex gender, double salary) {
        this(0, firstName, lastName, gender, salary);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + sex +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && Objects.equals(firstName, developer.firstName) &&
                Objects.equals(lastName, developer.lastName) && sex == developer.sex &&
                Objects.equals(salary, developer.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, sex, salary);
    }
}
