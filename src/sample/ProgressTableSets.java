package sample;

import java.net.URL;
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

public class ProgressTableSets
{
    private String dayColumnClass;
    private String nameColumnClass;
    private String timeColumnClass;

    public ProgressTableSets(String dayColumnClass, String nameColumnClass, String timeColumnClass)
    {
        this.dayColumnClass = dayColumnClass;
        this.nameColumnClass = nameColumnClass;
        this.timeColumnClass = timeColumnClass;
    }

    public ProgressTableSets() {
    }


    public String getDayColumnClass() {
        return dayColumnClass;
    }

    public void setDayColumnClass(String dayColumnClass) {
        this.dayColumnClass = dayColumnClass;
    }

    public String getNameColumnClass() {
        return nameColumnClass;
    }

    public void setNameColumnClass(String nameColumnClass) {
        this.nameColumnClass = nameColumnClass;
    }

    public String getTimeColumnClass() {
        return timeColumnClass;
    }

    public void setTimeColumnClass(String timeColumnClass) {
        this.timeColumnClass = timeColumnClass;
    }
}
