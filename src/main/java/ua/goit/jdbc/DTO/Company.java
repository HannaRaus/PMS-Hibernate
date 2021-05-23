package ua.goit.jdbc.DTO;

public class Company {
    private Integer id;
    private String name;
    private String headquarters;

    public Company() {
    }

    public Company(Integer id, String companyName, String headquarters) {
        this.id = id;
        this.name = companyName;
        this.headquarters = headquarters;
    }

    public Company(String companyName, String headquarters) {
        this(null, companyName, headquarters);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
