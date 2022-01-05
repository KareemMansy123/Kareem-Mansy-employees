package com.app.core;

import android.util.Log;

import com.app.core.date.AppDate;
import com.app.core.domain.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileParser {

   public enum Pattern {
        Comma(",");

        String regex;

        Pattern(String regex) {
            this.regex = regex;
        }
    }


   public List<Employee> parse(File file, Pattern pattern) {
        Scanner scanner = null;
        List<Employee> list = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            scanner.useDelimiter(pattern.regex + "|\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scanner == null) return list;
        while (scanner.hasNext()) {
            String id = scanner.next();
            String projectID = scanner.next();
            String startDate = scanner.next();
            String endDate = scanner.next();

            Employee employee = new Employee();
            employee.setId(id);
            employee.setProjectId(projectID);
            employee.setStartDate(startDate);
            employee.setEndDate(endDate);
            list.add(employee);
            Log.d("FileParser", "parse: " + employee.getEndDate());
            Log.d("FileParser", "parse: " + id + " " + projectID);
        }
        return list;
    }
}
