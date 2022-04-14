package com.example.projectlist.entites;

import javax.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long problem_id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_delete")
    private boolean is_delete;

    public long getProblem_id() {
        return problem_id;
    }
    public void setProblem_id(long problem_id) {
        this.problem_id = problem_id;
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

    public boolean isIs_delete() {
        return is_delete;
    }
    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
