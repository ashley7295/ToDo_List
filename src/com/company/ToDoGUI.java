package com.company;

/**
 * This class is meant to deal with the GUI interface
 * */


import javax.swing.*;
import javax.swing.text.DateFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ToDoGUI extends JFrame {


    //this is the mainJPanel
    private JPanel mainPanel;

    //Buttons or User actions
    private JButton deleteButton;
    private JButton completeButton;
    private JButton addButton;
    private JButton quitButton;

    //JLabels or status for the User
    private JLabel statusOfUserAction;
    private JLabel todaysDateLabel;


    //Task components
    private JComboBox priorityComboBox;
    private JComboBox taskTypeComboBox;
    private JCheckBox requiredCheckBox;
    private JSpinner dateSpinner;
    private JTextField descriptionTextField;
    private JTextField notesTextField;

    //JList's
    private JList<ToDo> eventJList;
    private JList<ToDo> completeJList;



    private DefaultListModel<ToDo> eventListModel;
    private DefaultListModel<ToDo> completeListModel;


    ToDoManager manager;
    ToDoDB database;


    //master Array list of Ticket Objects (may not ultimately need this once DBU is configured
    ArrayList<ToDo> masterToDoList = new ArrayList<>();



    ToDoGUI(ToDoManager manager){
        this.manager = manager;

        setContentPane(mainPanel);
        setPreferredSize(new Dimension(1000, 500));
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //adding the methods that configure the JLists
        eventListModelConfig();
        completeListModelConfig();

        //methods to configure the ComboBoxes, Date Spinner & today's Date Label
        priorityComboBoxConfig();
        taskTypeComboBoxConfig();
        dateSpinnerConfig();
        todaysDateLabelConfig();



        //Action listener for the Add Button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //getting the description text to add as the Task description
                String task = descriptionTextField.getText();

                //getting the priority
                int priority = priorityComboBox.getSelectedIndex() + 1;

                //getting the task type (this is also used for determining which JList the ticket goes into)
                String taskType = String.valueOf(taskTypeComboBox.getSelectedItem());

                //setting up the date formatting to get the date from the spinner
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyy");

                String dueDate = String.valueOf(sdf.format(dateSpinner.getValue()));
                //needing an exception handler to pass the date



                //checking to see if the required check box is checked
                String required;

                if(requiredCheckBox.isSelected()){
                    required = ToDoData.required[0];
                }else
                    required = ToDoData.required[1];

                //this is getting the notes from the user, i wanted to make this optional so if there isn;t any input in the TextField it will be null
                String notes;

                if(notesTextField.getText().isEmpty()){
                    notes = null;
                }else
                    notes = notesTextField.getText();

                //creating a new To do task Object
                ToDo newTask = new ToDo(task, dueDate, priority, required, taskType, notes);


                //once the object is created check to ensure there was data entered in the description text field, if not display an error
                if(descriptionTextField.getText().isEmpty() || descriptionTextField.getText() == null){
                    showMessageDialog("ERROR, Please enter your Task");
                }else
                    masterToDoList.add(newTask);
                    eventListModel.addElement(newTask);

                    statusOfUserAction.setText(ToDoData.TASK_ADDED);

                    database.addNewData(task, dueDate, priority, required, taskType, notes);
            }});





        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedEvent = eventJList.getSelectedIndex();
                int selectedCompletedEvent = completeJList.getSelectedIndex();


                if(selectedEvent == -1 && selectedCompletedEvent == -1){
                    showMessageDialog("ERROR, please select an item to Delete.");
                }else{
                    ToDo toDoToRemove = eventJList.getSelectedValue();
                    eventListModel.removeElement(toDoToRemove);

                    statusOfUserAction.setText(ToDoData.TASK_DELETED);

                    ToDo toDoToRemoveCompleted = completeJList.getSelectedValue();
                    completeListModel.removeElement(toDoToRemoveCompleted);

                    statusOfUserAction.setText(ToDoData.TASK_PERM_DELETED);

                    String task = toDoToRemove.getDescription();
                    database.deleteData(task);
                }


            }});

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int selectedEventToComplete = eventJList.getSelectedIndex();

                if(selectedEventToComplete == -1){
                    showMessageDialog("Please Select an item to Complete.");
                }else{
                    ToDo toDoToComplete = eventJList.getSelectedValue();
                    eventListModel.removeElement(toDoToComplete);
                    completeListModel.addElement(toDoToComplete);
                    statusOfUserAction.setText(ToDoData.TASK_COMPLETED);
                }

                //get selected item from JList, move it to completed List
                //Maybe add/configure completed Status? (ex: successfully completed, past due, date completed?)

            }});

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            System.exit(0);

        }
    });

    }

    //insert methods required for configuring combo box's and date spinners

    protected void eventListModelConfig(){
        eventListModel = new DefaultListModel<>();
        eventJList.setModel(eventListModel);
        eventJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); }


    protected void completeListModelConfig(){
        completeListModel = new DefaultListModel<>();
        completeJList.setModel(completeListModel);
        completeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); }



    protected void priorityComboBoxConfig(){

        priorityComboBox.addItem(ToDoData.priorityLevel[0]);
        priorityComboBox.addItem(ToDoData.priorityLevel[1]);
        priorityComboBox.addItem(ToDoData.priorityLevel[2]);
        priorityComboBox.addItem(ToDoData.priorityLevel[3]);
        priorityComboBox.addItem(ToDoData.priorityLevel[4]); }

    protected void taskTypeComboBoxConfig(){
        taskTypeComboBox.addItem(ToDoData.taskType[0]);
        taskTypeComboBox.addItem(ToDoData.taskType[1]);
        taskTypeComboBox.addItem(ToDoData.taskType[2]);
        taskTypeComboBox.addItem(ToDoData.taskType[3]);
        taskTypeComboBox.addItem(ToDoData.taskType[4]);
        taskTypeComboBox.addItem(ToDoData.taskType[5]); }


    protected void todaysDateLabelConfig(){


        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyy");
        Date todaysDate = new Date();

        todaysDateLabel.setText(sfd.format(todaysDate));
    }


    protected void showMessageDialog(String message) {JOptionPane.showMessageDialog(this, message);}


    protected void dateSpinnerConfig(){


        SpinnerDateModel spinnerDateModel = new SpinnerDateModel(new Date(), new Date(0), new Date(30000000000000L), Calendar.DAY_OF_YEAR);
        dateSpinner.setModel(spinnerDateModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "MM-dd-yyyy");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();

        formatter.setAllowsInvalid(false);

        formatter.setOverwriteMode(true);

        dateSpinner.setEditor(editor);

        //date spinner config code from Lab. 8 Garden Services Data

    }

}




/**
 *
 * Things to do for my to do program:
 *
 * DATABASE:
 * Configure add and delete Methods for a database
 * -outline or skeleton is set for the methods, refer to Chapter 10 once methods are resolved there
 *
 * ADD COMMENTS
 *
 * WRITE READ ME
 *
 *
 * */



