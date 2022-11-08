package com.example.landf;

import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.landf.Model.Listdata;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//////

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
EditText title,desc, posteddate, password;
String titlesend,descsend, posteddatesend, passwordsend, urlidtvsend;
String fileUri;
TextView uploadedImageUrl, progressTextView;
EditText urlidtv;

    UploadTask uploadTask;
    ProgressBar progressBar;

    FirebaseStorage storage;
    StorageReference storageReference;
    private Button btnChoose,btnUpload;
    private ImageView imageView;
    private ImageView imgFirebase;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
private DatabaseReference mDatabase;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        posteddate = findViewById(R.id.postedtime);
        password = findViewById(R.id.password);
         mDatabase = FirebaseDatabase.getInstance().getReference();
         //pic upload buttons
         btnChoose = (Button) findViewById(R.id.btnChoose);
         btnUpload = (Button) findViewById(R.id.btnUpload);
         imageView = (ImageView) findViewById(R.id.imgView);
         imgFirebase = (ImageView) findViewById(R.id.imgFirebase);

         //Firebase init
         storage = FirebaseStorage.getInstance();
         storageReference = storage.getReference();

         urlidtv = findViewById(R.id.urlidtv);

         btnUpload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 uploadImage();
             }
         });

         btnChoose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 chooseImage();
             }
         });



         //get image link


     }

     //Upload img fire
     private void uploadImage() {
         if(filePath != null){
             final ProgressDialog progress = new ProgressDialog(this);
             progress.setTitle("Uploading....");
             progress.show();
             String temp = UUID.randomUUID().toString();
           //  urlidtv.setText(temp);

             StorageReference ref= storageReference.child("images/"+temp);
             ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     progress.dismiss();
                     Toast.makeText(MainActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                     String imageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();






                     //Get URI of uploaded image
                     Uri downloaduri = taskSnapshot.getUploadSessionUri();
                     String link = downloaduri.toString();

                     urlidtv.setText(link);

                     ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {

                             urlidtv.setText(uri.toString());
                         }
                     });
                   //  StorageReference storageReference = = FirebaseStorage.getInstance().getReference().child("yourImageReferencePath");



                     /* context */
                   /*  Glide.with(this)
                            .using(new FirebaseImageLoader())
                             .load(storageReference)
                             .into(image );
*/


                     Picasso.get().load(imageUrl).into(imgFirebase);

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progress.dismiss();
                     Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                     double progres_time = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                     progress.setMessage("Uploaded "+(int)progres_time+" %");
                 }
             });
         }
     }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



    public void AddNotes(View view) {
        titlesend=title.getText().toString();
        descsend=desc.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        posteddatesend = currentDateandTime;
        passwordsend = password.getText().toString();
        urlidtvsend = urlidtv.getText().toString();

        if(TextUtils.isEmpty(titlesend) || TextUtils.isEmpty(descsend)){
            return;
        }


        AddNotes(titlesend,descsend, posteddatesend, passwordsend, urlidtvsend);

    }



    private void AddNotes(String titlesend, String descsend, String posteddatesend, String passwordsend, String urlidtvsend)
    {

        String id=mDatabase.push().getKey();
        Listdata listdata = new Listdata(id,titlesend, descsend, posteddatesend, passwordsend, urlidtvsend);
        mDatabase.child("Notes").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Notes Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            }
        });

    }
}
