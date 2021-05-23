package ua.goit.jdbc.DTO;

public class Developer {
    private Integer id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private Integer salary;

    public Developer() {
    }

    public Developer(Integer id, String firstName, String lastName, Sex gender, Integer salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = gender;
        this.salary = salary;
    }

    public Developer(String firstName, String lastName, Sex gender, Integer salary) {
        this(null, firstName, lastName, gender, salary);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
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
}
