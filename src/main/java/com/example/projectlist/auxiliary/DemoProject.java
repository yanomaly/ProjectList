package com.example.projectlist.auxiliary;

//used to collect any info about project from html forms
public class DemoProject {
    private String name;
    private String head;
    private String dateFinish;
    private String budget;

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

    public String getDateFinish() {
        return dateFinish;
    }
    public void setDateFinish(String date_finish) {
        this.dateFinish = date_finish;
    }

    public String getBudget() {
        return budget;
    }
    public void setBudget(String budget) {
        this.budget = budget;
    }

}
