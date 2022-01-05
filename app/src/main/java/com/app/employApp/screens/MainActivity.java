package com.app.employApp.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.core.FileHelper;
import com.app.core.PassData;
import com.app.core.domain.Employee;
import com.app.employApp.R;
import com.app.employApp.adapter.EmployeeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PassData {
    public int request_code = 1;
    private Button btnChooser;
    public static final String Home_TAG = "MainActivity";
    private RecyclerView rvEmployee;
    private EmployeeAdapter adapter;
    private List<Employee> employeeList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnChooser.setOnClickListener(view -> {
                // start runtime permission
                boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
                    return;
                }
                Log.e(Home_TAG, "get permision-- already granted ");

            //read file
            new FileHelper().showFileChooser(MainActivity.this, this);
        });
    }
    void init(){
        btnChooser = findViewById(R.id.btnChooser);
        rvEmployee = findViewById(R.id.rvEmployee);
        LinearLayoutManager manager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        adapter = new EmployeeAdapter(employeeList,this);
        rvEmployee.setLayoutManager(manager);
        rvEmployee.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == request_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //read file
                new FileHelper().showFileChooser(MainActivity.this, this);
            } else {
                // show a msg to user
                Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void passEmployeeData(List<Employee> employee) {
        // we can use view model
        // and enjoy with your UI
        Log.e("passEmployeeData", "passEmployeeData: " + employee.toString());
        adapter.setData(employee);
        adapter.notifyDataSetChanged();
    }
}

