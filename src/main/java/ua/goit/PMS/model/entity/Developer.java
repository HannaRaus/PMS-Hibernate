package ua.goit.PMS.model.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developers")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Developer")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "developer_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(name = "salary")
    private double salary;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Skill.class)
    @JoinTable(name = "developer_skills",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Project.class)
    @JoinTable(name = "project_developers",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    public Developer() {
    }

    public Developer(int id, String firstName, String lastName, Sex sex, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
    }

    public Developer(String firstName, String lastName, Sex sex, double salary) {
        this(0, firstName, lastName, sex, salary);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && Double.compare(developer.salary, salary) == 0 &&
                Objects.equals(firstName, developer.firstName) &&
                Objects.equals(lastName, developer.lastName) &&
                sex == developer.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, sex, salary);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex.getName() +
                ", salary=" + salary +
                '}';
    }
}
