//Sri Harshini Donthineni(sd17d), Venkata Vadrevu(vv18d)

package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Homepage extends AppCompatActivity {

    public ImageView cameraview;
    public ImageView camerabutton;
    public Bitmap captureimage;
    public Button textbutton;
    public Button retake;
    File imagefile;

    public Homepage()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        this.setTitle("Image To Text");
        cameraview=(ImageView)findViewById(R.id.camera);
        camerabutton=(ImageView) findViewById(R.id.camerabutton);
        //camerabutton.setBackgroundColor(Color.BLACK);
        textbutton=(Button)findViewById(R.id.textbutton);
        retake=(Button)findViewById(R.id.retake);

        if(ContextCompat.checkSelfPermission(Homepage.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            String [] permissionarray={Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(Homepage.this,permissionarray,100);
        }

        camerabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);

            }
        });
        textbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              StringBuilder finaltext=getTextFromImage();
              Intent generatedtext = new Intent(Homepage.this, GeneratedText.class);
              generatedtext.putExtra("text",finaltext.toString());
              startActivity(generatedtext);



            }
        });
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);

            }
        });


    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, @Nullable Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode==100)
        {
            this.captureimage=(Bitmap) data.getExtras().get("data");
            camerabutton.setVisibility(View.INVISIBLE);
            cameraview.setVisibility(View.VISIBLE);
            textbutton.setVisibility(View.VISIBLE);
            retake.setVisibility(View.VISIBLE);
            cameraview.setImageBitmap(captureimage);

        }
    }

    public StringBuilder getTextFromImage()
    {
        TextRecognizer textrecognizer=new TextRecognizer.Builder(getApplicationContext()).build();
        StringBuilder result= new StringBuilder();

        if(!textrecognizer.isOperational())
        {
            Toast.makeText(getApplicationContext(),
                    "ERROR: couldnot recognize", Toast.LENGTH_LONG).show();
        }
        else {
            System.out.println("In else statement*********************");
            //Bitmap src= BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            Frame frame= new Frame.Builder().setBitmap(captureimage).build();
            SparseArray<TextBlock>items=textrecognizer.detect(frame);
            for(int i=0;i<items.size();i++)
            {
                TextBlock myitem=items.valueAt(i);
                result.append(myitem.getValue());
                result.append("\n");
            }
            System.out.println(result+"*****************");
        }
        return result;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                //logs out the current user
                Intent main = new Intent(Homepage.this, MainActivity.class);
                startActivity(main);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}
