package com.app.core;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.app.core.date.AppDate;
import com.app.core.domain.Employee;
import java.util.Collections;
import java.util.List;

public class SortEmployee {
    private final AppDate appDate = new AppDate();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Employee> sortEmployee(List<Employee> employees) {
        Collections.sort(employees, (emp1, emp2) -> {
            if (emp1.getProjectId().equals(emp2.getProjectId())) {
                long emp1Days = appDate.daysBetween(emp1.getStartDate(), emp1.getEndDate());
                long emp2Days = appDate.daysBetween(emp2.getStartDate(), emp2.getEndDate());
                return (int) (emp2Days - emp1Days);
            }
            return emp1.getProjectId().compareTo(emp2.getProjectId());
        });
        employees.forEach(employee -> Log.d("sortEmployee", "sortEmployee: " + employee.toString()));
        return employees;
    }

}
