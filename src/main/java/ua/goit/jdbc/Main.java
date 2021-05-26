package ua.goit.jdbc;

import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.servises.Service;
import ua.goit.jdbc.servises.util.PropertiesLoader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager(propertiesLoader);

        Service<Developer> devService = new Service<>(new DeveloperDAO(connectionManager));

        //CREATE
        Developer created = new Developer("Hanna", "Raus", Sex.FEMALE, 1000);
        System.out.println("Created " + devService.create(created));

        //READ
        System.out.println("Find by id " + devService.findById(5));

        //UPDATE
        Developer updated = new Developer("Hanna", "Raus", Sex.MALE, 2000);
        List<Skill> devSkills = new ArrayList<>();
        devSkills.add(new Skill(Branch.JAVA, SkillLevel.JUNIOR));
        updated.setSkills(devSkills);
        System.out.println("Updated " + devService.update(52, updated));

        //DELETE
        devService.delete(51);

//        List<Developer> developers = devService.readAll();
//        developers.forEach(System.out::println);

//        Service<Skill> skillService = new Service<>(new SkillDAO(connectionManager));
//        List<Skill> skills = skillService.readAll();
//        skills.forEach(System.out::println);
//
//        Service<Project> projectService = new Service<>(new ProjectDAO(connectionManager));
//        List<Project> projects = projectService.readAll();
//        projects.forEach(System.out::println);
//
//        Service<Customer> customerService = new Service<>(new CustomerDAO(connectionManager));
//        List<Customer> customers = customerService.readAll();
//        customers.forEach(System.out::println);
//
//        Service<Company> companyService = new Service<>(new CompanyDAO(connectionManager));
//        List<Company> companies = companyService.readAll();
//        companies.forEach(System.out::println);
    }

}
