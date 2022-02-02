package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.*;


import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.*;
import java.lang.System;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import sample.ProgressTableSets;
import sample.DatabaseHandler;
import sample.DatabaseInfo;

public class Controller
{

    private ObservableList<ProgressTableSets> tableData = FXCollections.observableArrayList();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addTimeHours;

    @FXML
    private TableColumn<ProgressTableSets, String> dayColumn;

    @FXML
    private TableColumn<ProgressTableSets, String> timeColumn;

    @FXML
    private Label remainingTimeThisWeek;

    @FXML
    private TextField addTimeMinutes;

    @FXML
    private TableColumn<ProgressTableSets, String> nameColumn;

    @FXML
    private Button confirmAdding;

    @FXML
    private TableView<ProgressTableSets> progressTable;

    @FXML
    private TextField addName;

    @FXML
    private Label currentDayLabel;

    @FXML
    private Label remainingTimeToday;

    @FXML
    private Label leftTimeThisWeek;

    @FXML
    private Label averageTime;

    @FXML
    private Label averageTimeLeft;


    @FXML
    void initialize()
    {
        DatabaseHandler dbHandler = new DatabaseHandler();
        try
        {

            FileReader fr = new FileReader("src//sample//table.txt");
            BufferedReader br = new BufferedReader(fr);

            String str = br.readLine();
            char[] arr;
            int counter = 0;
            while (str != null)
            {
                String str1 = "", str2 = "", str3 = "";
                arr = str.toCharArray();
                for (int j = 0; j < arr.length; j++)
                {
                    if (arr[j] == ',')
                    {
                        counter++;
                        continue;
                    }
                    if (counter == 0)
                        str1 += arr[j];
                    else if (counter == 1)
                        str2 += arr[j];
                    else if (counter == 2)
                        str3 += arr[j];
                }
                counter = 0;
                addRow(str1, str2, str3);

                str = br.readLine();
            }


            fr.close();
            br.close();
        }
        catch (IOException e)
        {
            System.out.println("Error118");
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            FileReader fr = new FileReader("src//sample//log.txt");
                            BufferedReader br = new BufferedReader(fr);

                            LocalTime time = LocalTime.now();
                            LocalDate date = LocalDate.now();
                            if (time.isBefore(LocalTime.of(4, 20)))
                                date = date.minus(Period.ofDays(1));
                            DayOfWeek dayOfWeek = date.getDayOfWeek();
                            String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru-RU"));
                            char[] arr = dayName.toCharArray();
                            arr[0] = Character.toUpperCase(arr[0]);
                            dayName = new String(arr);
                            currentDayLabel.setText(dayName);
                            int week = Integer.parseInt(br.readLine());
                            int day = Integer.parseInt(br.readLine());
                            remainingTimeToday.setText(timingCounter(day));

                            br.readLine();
                            int weekMax = Integer.parseInt(br.readLine());
                            remainingTimeThisWeek.setText(timingCounter(week) + "/" + timingCounter(weekMax));
                            leftTimeThisWeek.setText(timingCounter(weekMax - week) + "/" + timingCounter(weekMax));
                            averageTime.setText(timingCounter(week / date.getDayOfWeek().getValue()));
                            averageTimeLeft.setText(timingCounter((weekMax - week) /
                                    (8 - date.getDayOfWeek().getValue())));

                            fr.close();
                            br.close();


                        }
                        catch (Exception e)
                        {
                            System.out.println("Error165");
                        }
                    }
                });
            }
        }, 0, 1000);


        confirmAdding.setOnAction(event ->
        {
            String addTimeMinutesVar = addTimeMinutes.getText().trim();
            String addTimeHoursVar = addTimeHours.getText().trim();
            String addNameVar = addName.getText().trim();
            if ((!addTimeHoursVar.isEmpty()
                    || !addTimeMinutesVar.isEmpty()) && !addNameVar.isEmpty())
                try
                {
                    if (addTimeHoursVar.isEmpty())
                        addTimeHoursVar = "0";
                    if (addTimeMinutesVar.isEmpty())
                        addTimeMinutesVar = "0";
                    FileReader fr = new FileReader("src//sample//log.txt");
                    BufferedReader br = new BufferedReader(fr);
                    int num3030;
                    int num420;
                    String day;
                    num3030 = Integer.parseInt(br.readLine());
                    if (addTimeHours.getText().trim() == null)
                        addTimeHours.setText("0");
                    if (addTimeMinutes.getText().trim() == null)
                        addTimeMinutes.setText("0");
                    num3030 += Integer.parseInt(addTimeHoursVar) * 60
                            + Integer.parseInt(addTimeMinutesVar);
                    num420 = Integer.parseInt(br.readLine());
                    day = br.readLine();
                    int weekMax = Integer.parseInt(br.readLine());

                    DatabaseInfo dbInfo = new DatabaseInfo(addNameVar,Integer.parseInt(addTimeHoursVar)
                            * 60 + Integer.parseInt(addTimeMinutesVar),
                            timingCounter(Integer.parseInt(addTimeHoursVar)
                            * 60 + Integer.parseInt(addTimeMinutesVar)));

                    if(dbHandler.CheckName(dbInfo).isBeforeFirst())
                    {
                        ResultSet resultSet = dbHandler.CheckName(dbInfo);
                        resultSet.next();
                        int lastTime = resultSet.getInt(Constant.DATABASE_TIME_INT);
                        lastTime+=Integer.parseInt(addTimeHoursVar) * 60 + Integer.parseInt(addTimeMinutesVar);
                        dbInfo.setTime(lastTime);
                        dbInfo.setTime_string(timingCounter(lastTime));
                        DatabaseInfo dbInfo2 = new DatabaseInfo(addNameVar, lastTime, timingCounter(lastTime));
                        dbHandler.ChangeRow(dbInfo2);
                    }
                    else
                        dbHandler.MakeNewRow(dbInfo);

                    if (day.equals(currentDayLabel.getText().trim()))
                        num420 += Integer.parseInt(addTimeHoursVar) * 60 + Integer.parseInt(addTimeMinutesVar);
                    else if (!day.trim().equals("Понедельник") &&
                            currentDayLabel.getText().trim().equals("Понедельник"))
                    {
                        progressTable.getItems().clear();

                        num420 = 0 + Integer.parseInt(addTimeHoursVar) * 60 + Integer.parseInt(addTimeMinutesVar);


                        // WeekMax change


                        if (num3030 > weekMax)
                        {
                            num3030 -= weekMax;
                            num3030 *= 2;
                            weekMax = 1830 - num3030;
                        }
                        else if (num3030 < weekMax)
                        {
                            num3030 -= weekMax;
                            num3030 = -num3030;
                            num3030 /= 2;
                            weekMax = 1830 + num3030;
                        }
                        num3030 = 0 + Integer.parseInt(addTimeHoursVar) * 60 + Integer.parseInt(addTimeMinutesVar);
                    }
                    else
                        num420 = 0 + Integer.parseInt(addTimeHoursVar) * 60 + Integer.parseInt(addTimeMinutesVar);
                    FileWriter fw =
                            new FileWriter("src//sample//log.txt");
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(num3030);
                    pw.println(num420);
                    pw.println(currentDayLabel.getText().trim());
                    pw.println(weekMax);
                    fw.close();
                    pw.close();
                    remainingTimeToday.setText(timingCounter(num420));
                    remainingTimeThisWeek.setText(timingCounter(num3030) + "/" + timingCounter(weekMax));
                    leftTimeThisWeek.setText(timingCounter(weekMax - num3030) + "/" + timingCounter(weekMax));
                    int average = 0;
                    if (day.trim().equals("Понедельник"))
                        average = 1;
                    else if (day.trim().equals("Вторник"))
                        average = 2;
                    else if (day.trim().equals("Среда"))
                        average = 3;
                    else if (day.trim().equals("Четверг"))
                        average = 4;
                    else if (day.trim().equals("Пятница"))
                        average = 5;
                    else if (day.trim().equals("Суббота"))
                        average = 6;
                    else if (day.trim().equals("Воскресенье"))
                        average = 7;

                    averageTime.setText(timingCounter(num3030 / average));
                    averageTimeLeft.setText(timingCounter((weekMax-num3030)   / (8 - average)));
                    addTimeHours.setText("");
                    addTimeMinutes.setText("");
                    addName.setText("");

                    FileWriter fwTable = new FileWriter("src//sample//table.txt", true);
                    PrintWriter pwTable = new PrintWriter(fwTable);
                    pwTable.println
                            (currentDayLabel.getText().trim() + "," +
                                    timingCounter(Integer.parseInt(addTimeHoursVar) * 60
                                            + Integer.parseInt(addTimeMinutesVar)) + "," + addNameVar);

                    fwTable.close();
                    pwTable.close();

                    addRow(currentDayLabel.getText().trim(), timingCounter(Integer.parseInt(addTimeHoursVar) * 60
                            + Integer.parseInt(addTimeMinutesVar)), addNameVar);





                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }
        });

    }

    String timingCounter(int day)
    {
        int dayHours = day / 60;
        day -= dayHours * 60;
        String result;
        if (day >= 0)
        {
            if (day >= 10)
                result = dayHours + ":" + day;
            else
                result = dayHours + ":0" + day;
        }
        else
        {
            if (dayHours == 0)
            {
                if (day > -10)
                    result = "-" + dayHours + ":0" + (-day);
                else
                    result = "-" + dayHours + ":" + (-day);
            }
            else
            {
                if (day > -10)
                    result = dayHours + ":0" + (-day);
                else
                    result = dayHours + ":" + (-day);
            }
        }
        return result;
    }


    void addRow(String str1, String str2, String str3)
    {
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayColumnClass"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumnClass"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeColumnClass"));

        tableData.add(new ProgressTableSets(str1, str2, str3));

        progressTable.setItems(tableData);
        progressTable.scrollTo(progressTable.getItems().size());
    }

}
