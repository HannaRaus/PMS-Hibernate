package ua.goit.PMS.service.mappers;

import ua.goit.PMS.exceptions.DAOException;
import ua.goit.PMS.model.entity.Branch;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.model.entity.SkillLevel;
import ua.goit.PMS.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SkillFromJSPMapper implements EntityFromJSPMapper<Skill> {
    private final Service<Developer> developerService;

    public SkillFromJSPMapper(Service<Developer> developerService) {
        this.developerService = developerService;
    }

    @Override
    public Skill readJSPForm(HttpServletRequest req) {
        Skill skill = new Skill();
        skill.setBranch(Branch.valueOf(req.getParameter("branch")));
        skill.setLevel(SkillLevel.valueOf(req.getParameter("level")));
        return skill;
    }

    @Override
    public Skill readJSPForm(HttpServletRequest req, Skill skill) {
        skill.setBranch(Branch.valueOf(req.getParameter("branch")));
        skill.setLevel(SkillLevel.valueOf(req.getParameter("level")));

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
        skill.setDevelopers(developers);

        return skill;

    }
}
