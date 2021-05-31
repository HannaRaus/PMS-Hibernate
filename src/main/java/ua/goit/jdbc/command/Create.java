package ua.goit.jdbc.command;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.view.View;

public class Create extends AbstractCommand implements Command {
    private static final String SECTION_MENU = """
            Please, enter the number according to list below
            1 - create customer
            2 - create company
            3 - create project
            4 - create developer
            5 - create skill
            return - go back to main menu
            """;
    private final View view;

    public Create(View view, DatabaseConnectionManager connectionManager) {
        super(view, connectionManager);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "create";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(SECTION_MENU);
            String section = view.read();
            switch (section) {
                case "1" -> createCustomer();
                case "2" -> createCompany();
                case "3" -> createProject();
                case "4" -> createDeveloper();
                case "5" -> createSkill();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void createCustomer() {
        view.write("Enter the customer name:");
        String name = view.read();
        view.write("Enter the customer industry:");
        String industry = view.read();
        Customer customer = new Customer(name, industry);
        try {
            Customer created = getCustomerService().create(customer);
            view.write("Created customer\n" + created + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createCompany() {
        view.write("Enter the company name:");
        String name = view.read();
        view.write("Enter the company headquarters:");
        String headquarters = view.read();
        Company company = new Company(name, headquarters);
        try {
            Company created = getCompanyService().create(company);
            view.write("Created customer\n" + created + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createProject() {
        view.write("Enter the project name:");
        String name = view.read();
        view.write("Enter the project description:");
        String description = view.read();
        double cost = getDoubleFromConsole("Enter the project cost:");
        Project project = new Project(name, description, cost);
        try {
            Project created = getProjectService().create(project);
            view.write("Created project\n" + created + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createDeveloper() {
        view.write("Enter the developer first name:");
        String firstName = view.read();
        view.write("Enter the developer last name:");
        String lastName = view.read();
        Sex sex = getGenderFromConsole();
        double salary = getDoubleFromConsole("Enter the developer salary:");
        Developer developer = new Developer(firstName, lastName, sex, salary);
        try {
            Developer created = getDeveloperService().create(developer);
            view.write("Created developer\n" + created + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createSkill() {
        Branch branch = getBranchFromConsole();
        SkillLevel level = getLevelFromConsole();
        Skill skill = new Skill(branch, level);
        try {
            Skill created = getSkillService().create(skill);
            view.write("Created skill\n" + created + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }
}
