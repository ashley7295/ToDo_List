package com.company;

/**
 * This class is meant to create and maintain a to do Object
 * */

import java.util.Date;

public class ToDo {


    //to do components
    private String description;
    private String dueDate;
    private int priority;
    private String required;
    private String type;
    private String notes;


    //To do constructor
    public ToDo (String description, String dueDate, int priority, String required, String type, String notes){
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.required = required;
        this.type = type;
        this.notes = notes;
    }


    //get and set methods
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getRequired() { return required; }
    public void setRequired(String required) { this.required = required; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }




    public String toString(){
        return "|Task: " + this.description + "| Due: " + this.dueDate + "\n" +
                "| Priority Level: " + this.priority + " and this is " + this.required +
                " | " + "\n" + this.type + " | Notes: " + this.notes + " |";
    }
}
