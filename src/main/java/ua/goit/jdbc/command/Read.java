package ua.goit.jdbc.command;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.view.View;

import java.util.List;

public class Read extends Commands implements Command {
    private final View view;
    private final DatabaseConnectionManager connectionManager;

    public Read(View view, DatabaseConnectionManager connectionManager) {
        super(view, connectionManager);
        this.view = view;
        this.connectionManager = connectionManager;
    }

    @Override
    public String commandName() {
        return "read";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write("""
                    Please, enter the number according to list below
                    1 - to get info by id
                    2 - to get all info
                    3 - get sum of salary for project
                    4 - get list of developers for project
                    5 - get list of developers by branch
                    6 - get list of developers by skill level
                    7 - get list of projects with date, name and quantity of developers
                    return - go back to main menu
                    """);
            String section = view.read();
            switch (section) {
                case "1" -> infoByID();
                case "2" -> infoAll();
                case "3" -> getSumSalaryForProject();
                case "4" -> getDevelopersByProject();
                case "5" -> getDevelopersByBranch();
                case "6" -> getDevelopersByLevel();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void infoByID() {
        boolean running = true;
        while (running) {
            writeSections();
            String section = view.read();
            switch (section) {
                case "1" -> getByID(getCustomerService());
                case "2" -> getByID(getCompanyService());
                case "3" -> getByID(getProjectService());
                case "4" -> getByID(getDeveloperService());
                case "5" -> getByID(getSkillService());
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private <T> void getByID(Service<T> service) {
        long id = getIntegerFromConsole("Enter id");
        try {
            T byId = service.findById(id);
            view.write("The " + byId.getClass().getSimpleName() + " with id [" + id + "] is \n" + byId + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void infoAll() {
        boolean running = true;
        while (running) {
            writeSections();
            String section = view.read();
            switch (section) {
                case "1" -> getAllInfo(getCustomerService());
                case "2" -> getAllInfo(getCompanyService());
                case "3" -> getAllInfo(getProjectService());
                case "4" -> getAllInfo(getDeveloperService());
                case "5" -> getAllInfo(getSkillService());
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private <T> void getAllInfo(Service<T> service) {
        List<T> all = service.readAll();
        all.forEach(System.out::println);
    }

    private void writeSections() {
        view.write("""
                Choose the section you would like to read. Enter the number according to list below
                1 - customers
                2 - companies
                3 - projects
                4 - developers
                5 - skills
                return - back to the read menu
                """);
    }

    private void getSumSalaryForProject() {
        long id = getIntegerFromConsole("Enter the project id");
        try {
            double sum = getProjectService().findById(id).getDevelopers().stream()
                    .mapToDouble(Developer::getSalary)
                    .sum();
            view.write("Summary of developers salaries for project with id [" + id + "] is " + sum + "\n");
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getDevelopersByProject() {
        long id = getIntegerFromConsole("Enter the project id");
        try {
            List<Developer> developers = getProjectService().findById(id).getDevelopers();
            view.write("Developers for project with id [" + id + "]");
            developers.forEach(System.out::println);
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getDevelopersByBranch() {
        Branch branch = getBranchFromConsole();
        List<Developer> byBranch = new DeveloperDAO(connectionManager).getByBranch(branch);
        view.write("Developers with language [" + branch.getName() + "]");
        byBranch.forEach(System.out::println);
    }

    private void getDevelopersByLevel() {
        SkillLevel level = getLevelFromConsole();
        List<Developer> bySkillLevel = new DeveloperDAO(connectionManager).getBySkillLevel(level);
        view.write("Developers with level [" + level.getName() + "]");
        bySkillLevel.forEach(System.out::println);
    }

}
