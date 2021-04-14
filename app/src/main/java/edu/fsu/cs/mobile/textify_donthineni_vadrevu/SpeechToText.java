package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity {
    public ImageButton mic;
    public TextView editText;
    public Button textbutton;
    public static final int REQUEST_CODE_SPEECH_INPUT=1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        mic=(ImageButton)findViewById(R.id.voiceBtn);
        editText=(TextView)findViewById(R.id.textTv);
        textbutton=(Button)findViewById(R.id.speechtextbutton);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                textbutton.setVisibility(View.VISIBLE);

            }
        });
        textbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finaltext=editText.getText().toString();
                Intent generatedtext = new Intent(SpeechToText.this, GeneratedText.class);
                generatedtext.putExtra("text",finaltext);
                startActivity(generatedtext);

            }
        });

    }
    private void speak()
    {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi, Speak something");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e)
        {
            Toast.makeText(this," " +e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode,resultCode,data);
       switch(requestCode)
       {
           case REQUEST_CODE_SPEECH_INPUT: {
               if(resultCode==RESULT_OK && null!=data)
               {
                   ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   editText.setText(result.get(0));
               }
               break;
           }
       }
    }
}
