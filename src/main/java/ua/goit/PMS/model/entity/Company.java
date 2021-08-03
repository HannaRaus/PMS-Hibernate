package ua.goit.PMS.model.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;
    @Column(name = "company_name")
    private String name;
    @Column(name = "headquarters")
    private String headquarters;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ua.goit.PMS.model.entity.Project.class)
    @JoinTable(name = "company_projects",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    public Company() {
    }

    public Company(int id, String companyName, String headquarters) {
        this.id = id;
        this.name = companyName;
        this.headquarters = headquarters;
    }

    public Company(String companyName, String headquarters) {
        this(0, companyName, headquarters);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
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
        return id == company.id && Objects.equals(name, company.name) &&
                Objects.equals(headquarters, company.headquarters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, headquarters);
    }
}
