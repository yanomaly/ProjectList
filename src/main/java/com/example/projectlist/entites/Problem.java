package com.example.projectlist.entites;

import javax.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemID;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_delete")
    private boolean isDelete;
    @Column(name = "project_id")
    private Long projectID;

    public Problem() {
    }
    public Problem(String name) {
        this.name = name;
    }

    public Long getProblemID() {
        return problemID;
    }
    public void setProblemID(Long problem_id) {
        this.problemID = problem_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(boolean is_delete) {
        this.isDelete = is_delete;
    }

    public Long getProjectID() {
        return projectID;
    }
    public void setProjectID(Long project_id) {
        this.projectID = project_id;
    }
}
