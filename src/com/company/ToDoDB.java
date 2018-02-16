package com.company;

import java.sql.*;
import java.util.ArrayList;

/**
 *This class is meant to connect to and communicate with the database
 **/


public class ToDoDB {

    //adding the driver (not sure if needed)
    static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    static String db_url = "jdbc:mysql://localhost:3306/todo";
    static String user = System.getenv("MYSQL_USER");      // TODO set this environment variable
    static String password = System.getenv("MYSQL_PASS");   // TODO set this environment variable


    ToDoGUI showMessage;



    public void addNewData(String task, String date, int priority, String required, String type, String notes){

        try(Connection conn = DriverManager.getConnection(db_url, user, password);

            Statement statement = conn.createStatement()){


            //this is the statement that is looking for the product in the database to ensure it exsists before it is updated.


                //this is creating a new Product Object

                    //the statement for the database that adds or inserts new data into the inventory database
                    String addNew = "INSERT INTO todo VALUES (?,?,?,?,?,?)";
                    PreparedStatement addNewPS = conn.prepareStatement(addNew);

                    addNewPS.setString(1, task);
                    addNewPS.setString(2, date);
                    addNewPS.setInt(3, priority);
                    addNewPS.setString(4, required);
                    addNewPS.setString(5, type);
                    addNewPS.setString(6, notes);

                    System.out.println(addNewPS);

                    int n = statement.executeUpdate(addNew);

                    showMessage.showMessageDialog("Item added to database");

                    if(n == 0){
                        showMessage.showMessageDialog("Item was not added to database.");
                    }



                //if (toDoFound == 0){
                //    showMessage.showMessageDialog("Item already in Database");
                //}

        } catch (SQLException e) {
                 e.printStackTrace(); }}


    public void deleteData(String task){

        try(Connection conn = DriverManager.getConnection(db_url, user, password);

            Statement statement = conn.createStatement()) {


            String deleteTask = "DELETE FROM todo WHERE description = ?";
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

    try(Connection conn = DriverManager.getConnection(db_url, user, password);
        Statement statement = conn.createStatement()){

        String allData = "SELECT * FROM todo";
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



