//Sri Harshini Donthineni(sd17d), Venkata Vadrevu(vv18d)

package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.setTitle("Login");

        //Buttons
        Button login=(Button)findViewById(R.id.login);
        Button signup=(Button)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailtext=(EditText)findViewById(R.id.emaillogin);
                EditText passwordtext=(EditText)findViewById(R.id.Password);

                String email=emailtext.getText().toString();
                String password=passwordtext.getText().toString();

                boolean emailexists=emailExists(email);
                if(emailexists) {
                    boolean validlogin=validLogin(email,password);

                    if(validlogin) {

                        int counter = getUserIndex(email, password);
                        String ID = String.valueOf(counter);
                        System.out.println("//////////////" + counter);

                        //making intent to send the uri to homeactivity
                        Intent Homeactivityintent = new Intent(MainActivity.this, Options.class);
                        String uri = UserContentProvider.CONTENT_URI.toString() + "/" + ID;
                        Homeactivityintent.putExtra("uri", uri);
                        startActivity(Homeactivityintent);
                        resetLogin(v);
                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Signup.class);
                startActivity(i);
                resetLogin(v);

            }
        });
    }
    public int getUserIndex(String email,String password)
    {
        //a cursor to get all the entries in the databse
        Cursor Allentries = getContentResolver().query(UserContentProvider.CONTENT_URI,  null, null, null, null);

        //counter to keep track of the ID of each entry
        int counter=1;
        if(Allentries!=null)
        {
            while(Allentries.moveToNext())
            {
                if(Allentries.getString(UserContentProvider.getColumnIndex(UserContentProvider.COLUMN_EMAIL)).equals(email))
                {
                    break;
                }
                else {
                    counter += 1;
                }
            }
        }
        return counter;
    }
    public boolean emailExists(String email)
    {
        String [] columns = {UserContentProvider.COLUMN_EMAIL};
        String selection_clause = UserContentProvider.COLUMN_EMAIL + " = ?";

        String [] comparison_values = {email};
        Cursor cursor1 = getContentResolver().query(UserContentProvider.CONTENT_URI,  columns, selection_clause, comparison_values, null);

        //If there is no match in the database inform the user
        if(cursor1.getCount() < 1)
        {
            Toast.makeText(MainActivity.this,
                    "ERROR: No Match found for this User, Try again", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
    public boolean validLogin(String email,String password)
    {
        String [] columns = {UserContentProvider.COLUMN_EMAIL, UserContentProvider.COLUMN_PASSWORD};
        String selection_clause = UserContentProvider.COLUMN_EMAIL + " = ? AND " +
                UserContentProvider.COLUMN_PASSWORD + " = ?";

        String [] comparison_values = {email, password};
        Cursor c=getContentResolver().query(UserContentProvider.CONTENT_URI,  columns, selection_clause, comparison_values, null);

        if(c.getCount() < 1)
        {
            Toast.makeText(MainActivity.this,
                    "ERROR: Username and password do not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void resetLogin(View view)
    {
        EditText email=(EditText)findViewById(R.id.emaillogin);
        EditText passwordtext=(EditText)findViewById(R.id.Password);

        email.setText("");
        passwordtext.setText("");

    }
}
