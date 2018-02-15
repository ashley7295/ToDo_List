package com.company;

import java.sql.*;

/**
 *This class is meant to connect to and communicate with the database
 **/


public class ToDoDB {

    //adding the driver (not sure if needed)
    //static String jdbc_driver = "com.mysql.cj.jdbc.Driver";
    static String db_url = "jdbc:mysql://localhost:3306/todo";
    static String user = System.getenv("MYSQL_USER");      // TODO set this environment variable
    static String password = System.getenv("MYSQL_PASS");   // TODO set this environment variable


    ToDoGUI showMessage;

    public void addNewData(String task, String date, int priority, String required, String type, String notes){
        try(Connection conn = DriverManager.getConnection(db_url, user, password);

            Statement statement = conn.createStatement()){


            //this is the statement that is looking for the product in the database to ensure it exsists before it is updated.
            PreparedStatement findTask = conn.prepareStatement("SELECT * FROM inventory WHERE UPPER(description) = UPPER (?)");
            findTask.setString(1, task);

            ResultSet rs = findTask.executeQuery();

            boolean toDoFound = false;
            while(rs.next()){
                toDoFound = true;
                //this is creating a new Product Object

                    //the statement for the database that adds or inserts new data into the inventory database
                    String addNew = "INSERT INTO todo VALUES (? VARCHAR(500),? VARCHAR(50),? int,? VARCHAR(50),? VARCHAR(50),? VARCHAR(500))";
                    PreparedStatement addNewPS = conn.prepareStatement(addNew);

                    addNewPS.setString(1, task);
                    addNewPS.setString(2, date);
                    addNewPS.setInt(3, priority);
                    addNewPS.setString(4, required);
                    addNewPS.setString(5, type);
                    addNewPS.setString(6, notes);

                    statement.executeUpdate(addNew);

                    System.out.println("Item added to database");

                }
                if (!toDoFound){
                    showMessage.showMessageDialog("Item already in Database");
                }

        } catch (SQLException e) {
                 e.printStackTrace(); }}


    public void deleteData(String task){

        try(Connection conn = DriverManager.getConnection(db_url, user, password);

            Statement statement = conn.createStatement()) {



            //this is the statement that is looking for the product in the database to ensure it exsists before it is updated.
            PreparedStatement findProduct = conn.prepareStatement("SELECT * FROM inventory WHERE UPPER(task) = UPPER (?)");
            findProduct.setString(1, task);

            ResultSet rs = findProduct.executeQuery();

            boolean productFound = false;
            while (rs.next()) {
                productFound = true;
                String deleteData = "DELETE FROM inventory WHERE product_name = ?";
                PreparedStatement deletePS = conn.prepareStatement(deleteData);

                deletePS.setString(1, task);

                statement.executeQuery(deleteData);

                System.out.println("Product has been deleted from database");

            }
            if (!productFound){
                System.out.println("Product not found in Database");
            }


        }catch (SQLException e1) {
            e1.printStackTrace(); }

    }



}



