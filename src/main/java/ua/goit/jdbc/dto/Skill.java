package ua.goit.jdbc.dto;


public class Skill {
    private long id;
    private Branch branch;
    private SkillLevel level;

    public Skill() {
    }

    public Skill(long id, Branch branch, SkillLevel level) {
        this.id = id;
        this.branch = branch;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", branch=" + branch +
                ", level=" + level +
                '}';
    }
}
