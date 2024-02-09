package com.example.services_novigrad;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.services_novigrad.Services.NewServicesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RequestForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private Spinner spinnerTextSize;
    Button timeButton;
    int hour, minute;

    private EditText firstname, lastname;
    private Button submit,upload;

    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        db = FirebaseFirestore.getInstance();

        firstname =findViewById(R.id.customerFirstName);
        lastname = findViewById(R.id.customerLastName);
        upload = findViewById(R.id.customerUploadFile);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText((CharSequence) getTodaysDate());

        timeButton = findViewById(R.id.timeButton);

        spinnerTextSize = findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        String[] textSizes = getResources().getStringArray(R.array.font_sizes);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);

        submit = findViewById(R.id.customerSubmit);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveRequests();
            }

        });


//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String firstName = firstname.getText().toString().trim();
//                String lastName = lastname.getText().toString().trim();
//                String dateBuTTon = dateButton.getText().toString().trim();
//                String timeBuTTon = timeButton.getText().toString().trim();
//                String spinnerTextSIZE= spinnerTextSize.toString().trim(); // a  revoir si probleme
//
//
//
//                    if (TextUtils.isEmpty(firstName)){
//                        firstname.setError ("First name is required");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(lastName)){
//                        lastname.setError ("Last name is required");
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(dateBuTTon)){
//                    dateButton.setError ("First name is required");
//                    return;
//                    }
//
//                    if (TextUtils.isEmpty(timeBuTTon)){
//                    timeButton.setError ("First name is required");
//                    return;
//                    }
//
////                    if (TextUtils.isEmpty(spinnerTextSIZE)){
////                    spinnerTextSize.setError ("First name is required");
////                    return;
////                    }
//
//
//
//                userID = fAuth.getCurrentUser().getUid();
//
//                DocumentReference documentReference = db.collection("RequestForm").document(userID);
//
//                Map<String,Object> requestform = new HashMap<>();
//                requestform.put("firstName" , firstName);
//                requestform.put("lastName" , lastName);
//                requestform.put("dateBuTTon" , dateBuTTon);
//                requestform.put("timeBuTTon" , timeBuTTon);
//                requestform.put("spinnerTextSIZE" , spinnerTextSIZE);
//
//                documentReference.set(requestform).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: Successfully submitted" + userID);
//                    }
//                });
//
//        Intent intent = new Intent(RequestForm.this, ViewRequest.class);
//        startActivity(intent);
//
//
//
//
//
//
//
//
//
//
//
//            }
//        });

    }

    private void saveRequests() {

        String firstName = firstname.getText().toString().trim();
        String lastName = lastname.getText().toString().trim();
        String dateBuTTon = dateButton.getText().toString().trim();
        String timeBuTTon = timeButton.getText().toString().trim();
        String spinnerTextSIZE= spinnerTextSize.toString().trim(); // a  revoir si probleme

        Map<String,Object> requestform = new HashMap<>();
        requestform.put("firstname" , firstName);
        requestform.put("lastname" , lastName);
        requestform.put("datebutton" , dateBuTTon);
        requestform.put("timebutton" , timeBuTTon);
        requestform.put("spinnertextsize" , spinnerTextSIZE);


        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()){
            Toast.makeText(RequestForm.this , "Please, insert firstname,lastname, date ,time" ,  Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("RequestsForm").add(requestform).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                String documentId = task.getResult().getId();
                requestform.put("id", documentId);
                db.collection("RequestsForm").document(documentId).set((requestform));

                Toast.makeText(RequestForm.this , "Successfully submitted" ,  Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RequestForm.this, ViewRequest.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        if (parent.getId() == R.id.spinnerTextSize) {
            String valueFromSpinner = parent.getItemAtPosition(position).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private Object getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private Object makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private Object getMonthFormat(int month) {

        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = (String) makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}
