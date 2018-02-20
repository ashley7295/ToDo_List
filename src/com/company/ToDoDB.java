package com.company;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.Class.forName;

/**
 *This class is meant to connect to and communicate with the database
 **/


public class ToDoDB {

    ToDoGUI showMessage;


    public static void main(String[] args){


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


    public void addNewData(String task, String date, int priority, String required, String type, String notes){

        try(Connection conn = DriverManager.getConnection(ToDoData.db_url, ToDoData.user, ToDoData.password);
            Statement statement = conn.createStatement()){



                    //the statement for the database that adds or inserts new data into the inventory database
                    String addNew = "INSERT INTO tasks VALUES (?,?,?,?,?,?)";
                    PreparedStatement addNewPS = conn.prepareStatement(addNew);

                    addNewPS.setString(1, task);
                    addNewPS.setString(2, date);
                    addNewPS.setInt(3, priority);
                    addNewPS.setString(4, required);
                    addNewPS.setString(5, type);
                    addNewPS.setString(6, notes);

                    System.out.println(addNewPS);

                    int n = addNewPS.executeUpdate(addNew);

                    showMessage.showMessageDialog("Item added to database");

                    if(n == 0){
                        showMessage.showMessageDialog("Item was not added to database.");
                    }



        } catch (SQLException e) {
                 e.printStackTrace(); }}


    public void deleteData(String task){

        try(Connection conn = DriverManager.getConnection(ToDoData.db_url, ToDoData.user,ToDoData.password);

            Statement statement = conn.createStatement()) {


            String deleteTask = "DELETE FROM tasks WHERE description = ?";
            PreparedStatement deletePS = conn.prepareStatement(deleteTask);

            deletePS.setString(1, task);

            int n = deletePS.executeUpdate();

            if(n == 0){
                showMessage.showMessageDialog("item was not removed from database");
            }


        }catch (SQLException e1) {
            e1.printStackTrace(); } }



    public ArrayList<ToDo> showDatabaseContents(){

    ArrayList<ToDo> listOfTasks = new ArrayList<>();

    try(Connection conn = DriverManager.getConnection(ToDoData.db_url,ToDoData.user, ToDoData.password);
        Statement statement = conn.createStatement()){

        String allData = "SELECT * FROM tasks";
        ResultSet rs = statement.executeQuery(allData);


        while (rs.next()){

            String task = rs.getString("task");
            String date = rs.getString("date");
            int priority = rs.getInt("priority");
            String required = rs.getString("required");
            String type = rs.getString("type");
            String notes = rs.getString("notes");

            ToDo newToDo = new ToDo(task, date, priority, required, type, notes);

            listOfTasks.add(newToDo);
        }


    } catch (SQLException e) {
        e.printStackTrace();
    }


    return listOfTasks;}





}



