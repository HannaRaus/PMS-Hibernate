package ua.goit.PMS.model.entity;


import ua.goit.PMS.service.BranchConverter;
import ua.goit.PMS.service.SkillLevelConverter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int id;
    @Column(name = "branch")
    @Convert(converter = BranchConverter.class)
    private Branch branch;
    @Column(name = "skill_level")
    @Convert(converter = SkillLevelConverter.class)
    private SkillLevel level;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ua.goit.PMS.model.entity.Developer.class)
    @JoinTable(name = "developer_skills",
            joinColumns = @JoinColumn(name = "developer_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Developer> developers;

    public Skill() {
    }

    public Skill(int id, Branch branch, SkillLevel level) {
        this.id = id;
        this.branch = branch;
        this.level = level;
    }

    public Skill(Branch branch, SkillLevel level) {
        this(0, branch, level);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", branch=" + branch.getName() +
                ", level=" + level.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && branch == skill.branch && level == skill.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch, level);
    }

    public String simpleString() {
        return "branch-" + branch.getName() + ", level-" + level.getName();
    }
}
