package com.example.services_novigrad.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.R;


public class NewEmployeePageError extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        setContentView(R.layout.activity_new_employee_page);
        Button btnCreeSuccursale = findViewById(R.id.button_add_succursale);
        btnCreeSuccursale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BranchEditPage.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }

}
