package com.company;

/**
 * This class is to keep and maintain constant variables throughout the program
 * */


public class ToDoData {

    //data store for the priority level ComboBox
    static Integer[] priorityLevel = {1,2,3,4,5};

    //data store for the Required ComboBox
    static String[] required = {"Required", "Not Required"};

    static String[] taskType = {"To do", "Event"};

    //Status message constants:
    final static String TASK_ADDED = "Your task has been added.";
    final static String TASK_DELETED = "Your task has been deleted.";
    final static String TASK_COMPLETED = "Congratulations! your task has been completed!";
    final static String TASK_OVERDUE = "Your task is overdue and has been added to the Completed List";






}
