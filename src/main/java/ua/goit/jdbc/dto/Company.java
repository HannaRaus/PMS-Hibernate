package ua.goit.jdbc.dto;

import java.util.Objects;

public class Company {
    private long id;
    private String name;
    private String headquarters;

    public Company() {
    }

    public Company(long id, String companyName, String headquarters) {
        this.id = id;
        this.name = companyName;
        this.headquarters = headquarters;
    }

    public Company(String companyName, String headquarters) {
        this(0, companyName, headquarters);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + name + '\'' +
                ", headquarters='" + headquarters + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(headquarters, company.headquarters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, headquarters);
    }
}
