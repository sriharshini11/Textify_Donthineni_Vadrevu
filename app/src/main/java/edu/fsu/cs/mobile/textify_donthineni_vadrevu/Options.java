package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {
    public Button imagetotext;
    public Button speechtotext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        imagetotext=(Button)findViewById(R.id.imagetotext);
        speechtotext=(Button)findViewById(R.id.speechtotext);

        imagetotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(Options.this, Homepage.class);
                startActivity(gotohome);
            }
        });

        speechtotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotospeech = new Intent(Options.this, SpeechToText.class);
                startActivity(gotospeech);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                //logs out the current user
                Intent main = new Intent(Options.this, MainActivity.class);
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
