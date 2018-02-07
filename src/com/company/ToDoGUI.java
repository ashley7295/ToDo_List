package com.company;

/**
 * This class is meant to deal with the GUI interface
 * */


import javax.swing.*;
import javax.swing.text.DateFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ToDoGUI extends JFrame {

    //Action components


    private JPanel mainPanel;

    private JButton deleteButton;
    private JButton completeButton;
    private JButton addButton;
    private JButton quitButton;

    //JLabels for status for the User
    private JLabel statusOfUserAction;
    //need to add todays date lable and

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


    //master Array list of Ticket Objects (may not ultimately need this once DBU is configured
    ArrayList<ToDo> masterToDoList = new ArrayList<>();

    //Just for organizational purposes (not sure if it'll be needed later) i am creating two separate arrayLists for the different task types
    ArrayList<ToDo> todoTask = new ArrayList<>();


    ToDoGUI(ToDoManager manager){
        this.manager = manager;

        setContentPane(mainPanel);
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

                //needing an exception handler to pass the date
                Date dueDate = null;
                try {
                    dueDate = sdf.parse(dateSpinner.getToolTipText());
                } catch (ParseException e1) {
                    e1.printStackTrace(); }

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
                    todoTask.add(newTask);
                    eventListModel.addElement(newTask);
                    statusOfUserAction.setText(ToDoData.TASK_ADDED);}});





        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedEvent = eventJList.getSelectedIndex();

                if(selectedEvent == -1){
                    showMessageDialog("ERROR, please select a Task or Event");
                }else{
                    ToDo toDoToRemove = eventJList.getSelectedValue();
                    eventListModel.removeElement(toDoToRemove);
                    statusOfUserAction.setText(ToDoData.TASK_DELETED);


                }}});

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //get selected item from JList, move it to completed List
                //Maybe add/configure completed Status? (ex: successfully completed, past due, date completed?)

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
        taskTypeComboBox.addItem(ToDoData.taskType[1]); }


    protected void todaysDateLabelConfig(){


        SimpleDateFormat sfd = new SimpleDateFormat("MM-dd-yyy");
        Date todaysDate = new Date();

        //todaysDateLabel.setText(sfd.format(todaysDate));
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
 * Action Listeners:
 * STARTED Delete button - deletes a selected item from the to doJList or the Events JList
 * DONE Add button - Adds a task object to either the to do JList or the Events JList depending on what task Type was selected
 * STARTED Complete button - moves a to do or an event (or Task Object) to the Complete JList
 *
 *
 * Configurations:
 * Deleting Tasks from certain JLists (how to check to see which JList was selected from
 * Adding an overdue to do or Event to the completed JList after the current time is past the due date
 *
 * Misc:
 * Figure out how to get the program to initially start
 * Figure out how to push everything to GitHub
 * Figure out how to connect everything to a database
 * Make an Exit button
 * Remove To do JList
 *
 *
 * */



