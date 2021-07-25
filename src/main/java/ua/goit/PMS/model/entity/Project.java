package ua.goit.PMS.model.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;
    @Column(name = "project_name")
    private String name;
    @Column(name = "project_description")
    private String description;
    @Column(name = "cost")
    private double cost;
    @Column(name = "create_date")
    private LocalDate date;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Developer.class)
    @JoinTable(name = "project_developers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private Set<Developer> developers;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Company.class)
    @JoinTable(name = "company_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Customer.class)
    @JoinTable(name = "customer_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> customers;

    public Project() {
    }

    public Project(int id, String name, String description, double cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.date = LocalDate.now();
    }

    public Project(String name, String description, double cost) {
        this(0, name, description, cost);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Double.compare(project.cost, cost) == 0 &&
                Objects.equals(name, project.name) && Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cost);
    }
}
