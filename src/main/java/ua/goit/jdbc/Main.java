package ua.goit.jdbc;

import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.servises.Service;
import ua.goit.jdbc.servises.util.PropertiesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager(propertiesLoader);

        Service<Developer> devService = new Service<>(new DeveloperDAO(connectionManager));
        Service<Project> projectService = new Service<>(new ProjectDAO(connectionManager));

        //CREATE
//        Project createdProject = projectService.create(new Project("new project", "description", 20000));
//        System.out.println("Created " + createdProject);
        Developer createdDeveloper = devService.create(new Developer("Hanna", "Raus", Sex.FEMALE, 1000));
        System.out.println("Created " + createdDeveloper);

//        //Update
//        Developer toUpdate = new Developer(53,"111", "Raus", Sex.MALE, 5000);
//        toUpdate.setProjects(List.of(projectService.findById(18), projectService.findById(19),
//                projectService.findById(20)));
//
//        Developer updatedDeveloper = devService.update(toUpdate);
//        System.out.println("Updated " + updatedDeveloper);
//        List<Project> projects = updatedDeveloper.getProjects();
//        projects.forEach(System.out::println);

//        //READ
//        System.out.println("Find by id " + devService.findById(51));
//
//        //UPDATE
//        Developer updated = new Developer("Hanna", "Raus", Sex.MALE, 2000);
//
//        System.out.println("Updated " + devService.update(52, updated));
//
//        //DELETE
//        devService.delete(51);

//        String selectDevelopersBySkill = "SELECT * FROM developers d WHERE d.developer_id IN" +
//                "(SELECT ds.developer_id  FROM developer_skills ds " +
//                "INNER JOIN skills s ON ds.skill_id = s.skill_id " +
//                "WHERE s.branch= 'Java') ORDER BY d.developer_id;";






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
