package ua.goit.jdbc.command;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.view.View;

import java.util.ArrayList;
import java.util.List;

public class Update extends AbstractCommand implements Command {
    private static final String SECTION_MENU = """
            Please, enter the number according to list below
            1 - update customer
            2 - update company
            3 - update project
            4 - update developer
            5 - update skill
            return - go back to main menu
            """;
    private static final String CUSTOMER_SECTION_MENU = """
            Choose info you would like to update from list below
            1 - update customer name
            2 - update customer industry
            3 - add customer companies
            4 - add customer projects
            ok - when you are ready
            """;
    private static final String COMPANY_SECTION_MENU = "";
    private static final String PROJECT_SECTION_MENU = "";
    private static final String DEVELOPER_SECTION_MENU = "";
    private static final String SKILL_SECTION_MENU = "";
    private final View view;

    public Update(View view, DatabaseConnectionManager connectionManager) {
        super(view, connectionManager);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "update";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(SECTION_MENU);
            String section = view.read();
            switch (section) {
                case "1" -> updateCustomer();
                case "2" -> updateCompany();
                case "3" -> updateProject();
                case "4" -> updateDeveloper();
                case "5" -> updateSkill();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void updateCustomer() {
        Customer toUpdate = null;
        view.write("Which customer you would like to update?");
        while (toUpdate == null) {
            toUpdate = getByID(getCustomerService(), "customer");
        }
        boolean running = true;
        while (running) {
            view.write(CUSTOMER_SECTION_MENU);
            String field = view.read();
            switch (field) {
                case "1" -> {
                    view.write("Write customer new name");
                    toUpdate.setName(view.read());
                }
                case "2" -> {
                    view.write("Write customer new industry");
                    toUpdate.setIndustry(view.read());
                }
                case "3" -> toUpdate.setCompanies(getCompaniesFromConsole());
                case "4" -> toUpdate.setProjects(getProjectsFromConsole());
                case "ok" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
        try {
            Customer updated = getCustomerService().update(toUpdate);
            view.write("Updated customer\n" + updated + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updateCompany() {

    }

    private void updateProject() {

    }

    private void updateDeveloper() {

    }

    private void updateSkill() {

    }

    private List<Company> getCompaniesFromConsole() {
        List<Company> companies = new ArrayList<>();
        boolean running = true;
        while (running) {
            Company company = getByID(getCompanyService(), "company");
            companies.add(company);
            view.write("Successfully added.Enter another company id\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return companies;
    }

    private List<Project> getProjectsFromConsole() {
        List<Project> projects = new ArrayList<>();
        boolean running = true;
        while (running) {
            Project project = getByID(getProjectService(), "project");
            projects.add(project);
            view.write("Successfully added. Enter another project id\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return projects;
    }
}
