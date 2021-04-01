//Sri Harshini Donthineni(sd17d), Venkata Vadrevu(vv18d)

package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    String username="";
    String password="";
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        this.setTitle("Signup");


        Button signup=(Button)findViewById(R.id.signupbutton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=(EditText)findViewById(R.id.signupuser);
                EditText pass=(EditText)findViewById(R.id.signuppassword);
                EditText passconfirm=(EditText)findViewById(R.id.signuppasswordconfirm);
                EditText emailtext=(EditText)findViewById(R.id.signupemail);

                String checkpass=passconfirm.getText().toString();
                username=name.getText().toString();
                password=pass.getText().toString();
                email=emailtext.getText().toString();

                boolean success=true;
                if(username.equals(""))
                {
                    Toast.makeText(Signup.this,
                            "Error: Username cannot be empty", Toast.LENGTH_LONG).show();
                    success=false;
                }

                if(!checkpass.equals(password))
                {
                    Toast.makeText(Signup.this,
                            "Error: Passwords has to match", Toast.LENGTH_LONG).show();
                    success=false;

                }
                if(!isValidEmail(email))

                {
                    Toast.makeText(Signup.this,
                            "Error: Enter a valid Email Address", Toast.LENGTH_LONG).show();
                    success=false;
                }
                if(success==true)
                {
                    Toast.makeText(Signup.this,
                            "Success: User succesfully registered", Toast.LENGTH_LONG).show();

                    ContentValues newValues = new ContentValues();
                    newValues.put(UserContentProvider.COLUMN_NAME, username);
                    newValues.put(UserContentProvider.COLUMN_PASSWORD, password);
                    newValues.put(UserContentProvider.COLUMN_EMAIL,email);

                    Uri uri;
                    uri = getContentResolver().insert(
                            UserContentProvider.CONTENT_URI, newValues);

                    Toast.makeText(Signup.this,
                            "SUCCESS: User Information stored into the database. ", Toast.LENGTH_LONG).show();

                    Intent Homeactivityintent2 = new Intent(Signup.this, MainActivity.class);
                    Homeactivityintent2.putExtra("uri", uri.toString());
                    startActivity(Homeactivityintent2);
                }
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
