package com.company;

/**
 * This class is meant to create and maintain a to do Object
 * */

import java.util.Date;

public class ToDo {


    //to do components
    private String description;
    private Date dueDate;
    private int priority;
    private String required;
    private String type;


    //To do constructor
    public ToDo (String description, Date dueDate, int priority, String required, String type){
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.required = required;
        this.type = type;
    }


    //get and set methods
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getRequired() { return required; }
    public void setRequired(String required) { this.required = required; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }




}
