package com.example.task;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditTaskDesk extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dpd;
    EditText titleDoes, descDoes;
    TextView adddate2;
    Button btnSaveUpdate, btnDelete,btnGoCalendar;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);

        titleDoes=findViewById(R.id.titleDoes);
        descDoes=findViewById(R.id.descdoes);

        adddate2=findViewById(R.id.adddate2);

        btnSaveUpdate=findViewById(R.id.btnSaveUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        btnGoCalendar=findViewById(R.id.btnGoCalendar);

        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                dpd=new DatePickerDialog(EditTaskDesk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        adddate2.setText(mDay +"/" + (mMonth+1) + "/"+ mYear);

                    }
                },year,month,day);
                dpd.show();
            }
        });


        //Önceki sayfadan veriyi çek

        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        adddate2.setText(getIntent().getStringExtra("adddate2"));
        final String keykeyDoes= getIntent().getStringExtra("keydoes");

        reference= FirebaseDatabase.getInstance().getReference().child("TaskApp").
                child("Plan"+keykeyDoes);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a=new Intent(EditTaskDesk.this,MainActivity.class);
                            startActivity(a);
                        } else{
                            Toast.makeText(getApplicationContext(),"Silinemedi!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //güncelleme butonu içi aktif etme
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descDoes.getText().toString());
                        dataSnapshot.getRef().child("adddate2").setValue(adddate2.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keykeyDoes);

                        Intent a=new Intent(EditTaskDesk.this,MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}