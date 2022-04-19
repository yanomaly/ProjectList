package com.example.projectlist.entites;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectID;

    @Column(name = "name")
    private String name;
    @Column(name = "head")
    private String head;
    @Column(name = "date_finish")
    private Calendar dateFinish;
    @Column(name = "budget")
    private Integer budget;
    @Column(name = "user_id")
    private Long userID;
    @Column(name = "is_delete")
    private boolean isDelete;

    public Long getProjectID() {
        return projectID;
    }
    public void setProjectID(Long project_id) {
        this.projectID = project_id;
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

    public Calendar getDateFinish() {
        return dateFinish;
    }
    public void setDateFinish(Calendar date_finish) {
        this.dateFinish = date_finish;
    }

    public Integer getBudget() {
        return budget;
    }
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long user_id) {
        this.userID = user_id;
    }

    public boolean getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(boolean is_delete) {
        this.isDelete = is_delete;
    }
}
