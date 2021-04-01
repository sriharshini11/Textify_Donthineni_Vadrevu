//Sri Harshini Donthineni(sd17d), Venkata Vadrevu(vv18d)

package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LogoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                goToMainActivity();
            }
        }, 4000);

    }
    public void goToMainActivity()
    {
        Intent i=new Intent(LogoPage.this,MainActivity.class);
        startActivity(i);
    }
}
