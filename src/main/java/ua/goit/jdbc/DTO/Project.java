package ua.goit.jdbc.DTO;

public class Project {
    private Integer id;
    private String name;
    private String description;
    private Integer cost;

    public Project() {
    }

    public Project(Integer id, String name, String description, Integer cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Project(String name, String description, Integer cost) {
        this(null, name, description, cost);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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
}
