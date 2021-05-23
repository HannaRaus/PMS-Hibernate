package ua.goit.jdbc.DTO;

public class Customer {
    private Integer id;
    private String name;
    private String industry;

    public Customer() {
    }

    public Customer(Integer id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
    }

    public Customer(String name, String industry) {
        this(null, name, industry);
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
