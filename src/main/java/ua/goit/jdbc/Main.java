package ua.goit.jdbc;

import ua.goit.jdbc.controller.MainController;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.service.util.PropertiesLoader;
import ua.goit.jdbc.view.Console;
import ua.goit.jdbc.view.View;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager(propertiesLoader);

        View view = new Console(System.in, System.out);
        MainController controller = new MainController(view, connectionManager);

        controller.run();






//        Service<Developer> devService = new Service<>(new DeveloperDAO(connectionManager));
//        Service<Project> projectService = new Service<>(new ProjectDAO(connectionManager));

//        Arrays.stream(Branch.values()).map(Branch::getName).forEach(System.out::println);

        //CREATE
//        Project createdProject = projectService.create(new Project("new project", "description", 20000));
//        System.out.println("Created " + createdProject + "\n");
//        Developer createdDeveloper = devService.create(new Developer("Hanna", "Raus", Sex.FEMALE, 1000));
//        System.out.println("Created " + createdDeveloper + "\n");
//
//        //Update
//        Developer toUpdate = new Developer(51,"111", "Raus", Sex.MALE, 5000);
//        toUpdate.setProjects(List.of(projectService.findById(18)));
//        toUpdate.setSkills(List.of(new Skill(1, Branch.JAVA, SkillLevel.JUNIOR)));
//
//        Developer updatedDeveloper = devService.update(toUpdate);
//        System.out.println("Updated " + updatedDeveloper + "\n");
//        List<Project> projects = updatedDeveloper.getProjects();
//        projects.forEach(System.out::println);
//        List<Skill> skills = updatedDeveloper.getSkills();
//        skills.forEach(System.out::println);
//
//
//        //READ
//        Developer founded = devService.findById(3);
//        System.out.println("Find by id " + founded + "\n");
//        founded.getSkills().forEach(System.out::println);
//        founded.getProjects().forEach(System.out::println);
//
//        //READALL
//        devService.readAll().forEach(System.out::println);
//        projectService.readAll().forEach(System.out::println);
//
//        //DELETE
//        devService.delete(51);
//        projectService.delete(18);


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
