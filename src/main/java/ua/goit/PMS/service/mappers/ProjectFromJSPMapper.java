package ua.goit.PMS.service.mappers;

import ua.goit.PMS.exceptions.DAOException;
import ua.goit.PMS.model.entity.Company;
import ua.goit.PMS.model.entity.Customer;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectFromJSPMapper implements EntityFromJSPMapper<Project> {
    private final Service<Customer> customerService;
    private final Service<Company> companyService;
    private final Service<Developer> developerService;

    public ProjectFromJSPMapper(Service<Customer> customerService, Service<Company> companyService, Service<Developer> developerService) {
        this.customerService = customerService;
        this.companyService = companyService;
        this.developerService = developerService;
    }

    @Override
    public Project readJSPForm(HttpServletRequest req) {
        Project project = new Project();
        project.setName(req.getParameter("name"));
        project.setDescription(req.getParameter("description"));
        project.setCost(Double.parseDouble(req.getParameter("cost")));
        project.setDate(LocalDate.now());
        return project;
    }

    @Override
    public Project readJSPForm(HttpServletRequest req, Project project) {
        project.setName(req.getParameter("name"));
        project.setDescription(req.getParameter("description"));
        project.setCost(Double.parseDouble(req.getParameter("cost")));

        project.setDate(LocalDate.parse(req.getParameter("date")));
        String[] listOfCustomerId = req.getParameterValues("customers");
        Set<Customer> customers = new HashSet<>();
        if (listOfCustomerId != null && listOfCustomerId.length > 0) {
            customers = Arrays.stream(listOfCustomerId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Customer customer = null;
                        try {
                            customer = customerService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return customer;
                    })
                    .collect(Collectors.toSet());
        }
        project.setCustomers(customers);

        String[] listOfCompanyId = req.getParameterValues("companies");
        Set<Company> companies = new HashSet<>();
        if (listOfCompanyId != null && listOfCompanyId.length > 0) {
            companies = Arrays.stream(listOfCompanyId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Company company = null;
                        try {
                            company = companyService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return company;
                    })
                    .collect(Collectors.toSet());
        }
        project.setCompanies(companies);

        String[] listOfDeveloperId = req.getParameterValues("developers");
        Set<Developer> developers = new HashSet<>();
        if (listOfDeveloperId != null && listOfDeveloperId.length > 0) {
            developers = Arrays.stream(listOfDeveloperId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Developer developer = null;
                        try {
                            developer = developerService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return developer;
                    })
                    .collect(Collectors.toSet());
        }
        project.setDevelopers(developers);

        return project;
    }
}
