package com.example.projectlist.entites;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long project_id;

    @Column(name = "name")
    private String name;
    @Column(name = "head")
    private String head;
    @Column(name = "date_finish")
    private Calendar date_finish;
    @Column(name = "budget")
    private int budget;
    @Column(name = "problem_id")
    private long problem_id;
    @Column(name = "is_delete")
    private boolean is_delete;

    public long getProject_id() {
        return project_id;
    }
    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }
    public void setHead(String head) {
        this.head = head;
    }

    public Calendar getDate_finish() {
        return date_finish;
    }
    public void setDate_finish(Calendar date_finish) {
        this.date_finish = date_finish;
    }

    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }

    public long getProblem_id() {
        return problem_id;
    }
    public void setProblem_id(long problem_id) {
        this.problem_id = problem_id;
    }

    public boolean isIs_delete() {
        return is_delete;
    }
    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
