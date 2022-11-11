package com.example.landf;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.landf.Model.Listdata;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
EditText title,desc,postedtime, password, urlidtv;
String titlesend,descsend, postedtimesend, passwordsend, urlidtvsend;
private DatabaseReference mDatabase;
private Listdata listdata;
Button updates,delete;
     @RequiresApi(api = Build.VERSION_CODES.O)
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        updates=findViewById(R.id.updatesbutton);
         delete=findViewById(R.id.deletedbutton);
        // postedtime = findViewById(R.id.postedtime);
        final Intent i=getIntent();



        String gettitle=i.getStringExtra("title");
         String getdesc=i.getStringExtra("desc");
         String getPostedtime = i.getStringExtra("postedtime");
         String getPassword = i.getStringExtra("password");
         String getUrlidtv = i.getStringExtra("urlidtv");
         final String id=i.getStringExtra("id");
         title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        postedtime = findViewById(R.id.postedtime);
        password = findViewById(R.id.password);
        urlidtv = findViewById(R.id.urlidtv);
         mDatabase = FirebaseDatabase.getInstance().getReference();
            title.setText(gettitle);
            desc.setText(getdesc);
            postedtime.setText(getPostedtime);
            urlidtv.setText(getUrlidtv);
            String TempPass = getPassword;
            password.setText("");


            updates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   // if(TempPass.trim().equals(password.getText().toString().trim()))
                        UpdateItem(id);

                }
            });
         delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                 String tempS = password.getText().toString();


               //  if(TempPass.trim().equals(password.getText().toString().trim()))
                     deleteItem(id);

             }
         });


     }

    private void UpdateItem(String id)
    {

        CardView cv = (CardView) findViewById(R.id.cv) ;
        titlesend=title.getText().toString();
        descsend=desc.getText().toString();
        passwordsend = password.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        postedtimesend = "Updated At: "+currentDateandTime;
        urlidtvsend = urlidtv.getText().toString();


        Listdata listdata = new Listdata(id,titlesend, descsend, postedtimesend, passwordsend, urlidtvsend );
        mDatabase.child("Item").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditActivity.this, "Item Updated", Toast.LENGTH_SHORT).show();
                //cv.setCardBackgroundColor(Color.GRAY);

                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                //cv.setCardBackgroundColor(Color.GRAY);


            }
        });

    }

    private void deleteItem(String id) {
        mDatabase.child("Item").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this,"Item Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeScreen.class));

                    }
                });
        mDatabase.child("Notes").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this,"Item Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeScreen.class));

                    }
                });
    }
}
