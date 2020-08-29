package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class NewTaskAct extends AppCompatActivity {
    Calendar c;
    DatePickerDialog dpd;

    TextView titlepage, addTitle,adddesc,adddate2;
    EditText titleDoes,descdoes;
    Button btnSaveTask, btnCancel,btnGoCalendar;
    DatabaseReference reference;
    //Rastgele sayı üret veritabanı ekleme işi için karışıklık olmasın index ver.
    Integer doesNum= new Random().nextInt();
    String keydoes= Integer.toString(doesNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        //layoutta tanımlanması için atama işlemi
        titlepage=findViewById(R.id.titlepage);

        addTitle=findViewById(R.id.addtitle);
        adddesc=findViewById(R.id.adddesc);

        adddate2=findViewById(R.id.adddate2);

        titleDoes=findViewById(R.id.titleDoes);
        descdoes=findViewById(R.id.descdoes);


        btnSaveTask=findViewById(R.id.btnSaveTask);
        btnCancel=findViewById(R.id.btnCancel);
        btnGoCalendar=findViewById(R.id.btnGoCalendar);

        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                dpd=new DatePickerDialog(NewTaskAct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        adddate2.setText(mDay +"/" + (mMonth+1) + "/"+ mYear);

                    }
                },year,month,day);
                dpd.show();
            }
        });



        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VERİTABANINA BUTONA BASILDIKTAN SONRA VERİ EKLENEN KISIM
                reference= FirebaseDatabase.getInstance().getReference().child("TaskApp").
                        child("Plan"+keydoes);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("adddate2").setValue(adddate2.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a=new Intent(NewTaskAct.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(NewTaskAct.this,MainActivity.class);
                startActivity(a);
            }
        });


        //fontları al
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        //yeniden yerleştir
        titlepage.setTypeface(MMedium);

        addTitle.setTypeface(MLight);
        titleDoes.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        descdoes.setTypeface(MMedium);

        adddate2.setTypeface(MLight);
        btnGoCalendar.setTypeface(MMedium);

        btnSaveTask.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);
    }
}