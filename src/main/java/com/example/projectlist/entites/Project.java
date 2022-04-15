package com.example.projectlist.entites;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long project_id;

    @Column(name = "name")
    private String name;
    @Column(name = "head")
    private String head;
    @Column(name = "date_finish")
    private String date_finish;
    @Column(name = "budget")
    private String budget;
    @Column(name = "user_id")
    private long user_id;
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

    public String getDate_finish() {
        return date_finish;
    }
    public void setDate_finish(String date_finish) {
        this.date_finish = date_finish;
    }

    public String getBudget() {
        return budget;
    }
    public void setBudget(String budget) {
        this.budget = budget;
    }

    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long problem_id) {
        this.user_id = problem_id;
    }

    public boolean isIs_delete() {
        return is_delete;
    }
    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
