package com.company;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Class.forName;

/**
 *This class is meant to connect to and communicate with the database
 **/


public class ToDoDB {

    //method configureing the database driver
    public static void main(String[] args) {


        //for locating the database driver
        try {
            Class.forName(ToDoData.jdbc_driver);
            System.out.println("Success maybe");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Cannot locate driver");
            System.exit(-1);
        }

    }


    //add new data method
    public void addNewData(String task, String date, int priority, String required, String type, String notes) {

        try (Connection conn = DriverManager.getConnection(ToDoData.db_url, ToDoData.user, ToDoData.password);
             Statement statement = conn.createStatement()) {


            //the statement for the database that adds or inserts new data into the inventory database
            String addNew = "INSERT INTO tasks VALUES (?,?,?,?,?,?)";
            PreparedStatement addNewPS = conn.prepareStatement(addNew);

            //the values for the prepared statement
            addNewPS.setString(1, task);
            addNewPS.setString(2, date);
            addNewPS.setInt(3, priority);
            addNewPS.setString(4, required);
            addNewPS.setString(5, type);
            addNewPS.setString(6, notes);


            //getting the number of returned lines from the executed update
            int n = addNewPS.executeUpdate();

            //if the lines returned are 0 print a message
            if (n == 0) {
                System.out.println("error - 'add new data'");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteData(String task) {

        try (Connection conn = DriverManager.getConnection(ToDoData.db_url, ToDoData.user, ToDoData.password);

             Statement statement = conn.createStatement()) {

            //statement for removing data from a database by the tasks description
            String deleteTask = "DELETE FROM tasks WHERE description = ?";
            PreparedStatement deletePS = conn.prepareStatement(deleteTask);

            deletePS.setString(1, task);


            //same thing as add new data, getting the number of returned lines fromt he update and then listing an error if there are none
            int n = deletePS.executeUpdate();
            //if there are 0 lines returned that means nothing happened
            if (n == 0) {
                System.out.println("error - 'delete data'");
            }


        } catch (SQLException e1) {
            e1.printStackTrace(); }}

}