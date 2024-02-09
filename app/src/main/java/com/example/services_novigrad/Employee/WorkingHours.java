package com.example.services_novigrad.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkingHours extends AppCompatActivity {
    String sID, id;
    EditText monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    FirebaseFirestore fStore;
    Button set;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        sID= intent.getStringExtra("sID");
        id= intent.getStringExtra("ID");
        setContentView(R.layout.activity_edit_heures);

        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday =findViewById(R.id.friday);
        saturday=findViewById(R.id.saturday);
        sunday =findViewById(R.id.sunday);

        set =findViewById(R.id.setHours);
        fStore = FirebaseFirestore.getInstance();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Monday = monday.getText().toString().trim();
                String Tuesday = tuesday.getText().toString().trim();
                String Wednesday = wednesday.getText().toString().trim();
                String Thursday = thursday.getText().toString().trim();
                String Friday = friday.getText().toString().trim();
                String Saturday = saturday.getText().toString().trim();
                String Sunday = sunday.getText().toString().trim();
                String timePattern = "\\d{2}\\s*-\\s*\\d{2}";
                if(!Monday.matches(timePattern) ||
                        !Tuesday.matches(timePattern) ||
                        !Wednesday.matches(timePattern) ||
                        !Thursday.matches(timePattern) ||
                        !Friday.matches(timePattern) ||
                        !Saturday.matches(timePattern) ||
                        !Sunday.matches(timePattern)) {
                    if (!Monday.matches(timePattern)) {
                        monday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (!Tuesday.matches(timePattern) ) {
                        tuesday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (!Wednesday.matches(timePattern)) {
                        wednesday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (Thursday.matches(timePattern) ) {
                        thursday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (!Friday.matches(timePattern)) {
                        friday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (!Saturday.matches(timePattern)) {
                        saturday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                    if (!Sunday.matches(timePattern)) {
                        sunday.setError("XX-XX");
                        Toast.makeText(v.getContext(), "Please use the XX-XX format", Toast.LENGTH_SHORT);
                    }
                }else {
                    Map<String, Object> succursaledata = new HashMap<>();
                    succursaledata.put("mMonday", Monday);
                    succursaledata.put("mTuesday", Tuesday);
                    succursaledata.put("mWednesday", Wednesday);
                    succursaledata.put("mThursday", Thursday);
                    succursaledata.put("mFriday", Friday);
                    succursaledata.put("mSaturday", Saturday);
                    succursaledata.put("mSunday", Sunday);


                    fStore.collection("Succursale").document(sID).update(succursaledata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Working Hours saved successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainBranchPage.class);
                            intent.putExtra("ID", id);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
