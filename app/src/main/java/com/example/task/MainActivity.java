package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage , endpage;
    Button btnAddNew;



    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<Task> list;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //layoutta tanımlanması için atama işlemi
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);
        btnAddNew=findViewById(R.id.btnAddNew);





        //fontları al
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");
        //fontları yerleştir
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);
        endpage.setTypeface(MLight);
        btnAddNew.setTypeface(MLight);

        //eklemek için tıkladığında işleyen kısım
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(MainActivity.this,NewTaskAct.class);
                startActivity(a);
            }
        });

        //veriyle çalışırken
        ourdoes=findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<Task>();

        //firebaseden veri çek
        reference= FirebaseDatabase.getInstance().getReference().child("TaskApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //veriyi alıp layouta yerleştirme kısmı
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Task p=dataSnapshot1.getValue(Task.class);
                    list.add(p);
                }
                taskAdapter=new TaskAdapter(MainActivity.this,list);
                ourdoes.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Veriyi çekme veya yerleştirme kısmında hata varsa gör
                Toast.makeText(getApplicationContext(),"Veri yok",Toast.LENGTH_SHORT).show();

            }
        });
    }
}