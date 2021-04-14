//Sri Harshini Donthineni(sd17d), Venkata Vadrevu(vv18d)

package edu.fsu.cs.mobile.textify_donthineni_vadrevu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GeneratedText extends AppCompatActivity {

    private final String filenameInternal = "couponsFile";
    private final String filenameExternal = "cashbackFile";

    private TextView text;
    private EditText filenametext;
    private Button gohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_text);
        Bundle B=getIntent().getExtras();
        final String finaltext=B.getString("text");
        this.text=(TextView)findViewById(R.id.finaltext);
        System.out.println("^^^^^^^^^^^^^^^^^^^"+finaltext);
        text.setText(finaltext);
        Button createfile=(Button)findViewById(R.id.createfile);
        filenametext=(EditText)findViewById(R.id.filename);
        gohome=(Button)findViewById(R.id.gotohome);


        createfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename=filenametext.getText().toString();
                if(filename.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),
                            "ERROR: Filename can not be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    filename=filename+".txt";
                writeFileInternalStorage(v,finaltext,filename);
                    Toast.makeText(getApplicationContext(),
                            "SUCCESS: File created", Toast.LENGTH_LONG).show();}

            }
        });
        gohome.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent i=new Intent(GeneratedText.this,Options.class);
                                          startActivity(i);
                                      }
                                  }

        );
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                //logs out the current user
                Intent main = new Intent(GeneratedText.this, MainActivity.class);
                startActivity(main);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void writeFileInternalStorage(View view,String finaltext,String Filename) {


        String coupons = finaltext;
        createUpdateFile(Filename, coupons, false);

    }

    public void appendFileInternalStorage(View view) {
        String coupons = "Get upto 50% off fashion @ xyx shop \n Get upto 80% off on beauty @ yuu shop";
        createUpdateFile(filenameInternal, coupons, true);
    }

    private void createUpdateFile(String fileName, String content, boolean update) {
        FileOutputStream outputStream;
        try {
            if (update) {

                outputStream = openFileOutput(fileName, Context.MODE_APPEND);
            } else {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            }

            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFileInternalStorage(View view) {
        try {
            FileInputStream fileInputStream = openFileInput(filenameInternal);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            text.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTemporaryFile(View view) {
        try {
            String fileName = "couponstemp";
            String coupons = "Get upto 50% off shoes @ xyx shop \n Get upto 80% off on shirts @ yuu shop";

            File file = File.createTempFile(fileName, null, getCacheDir());

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(coupons.getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
        }
    }

    public void deleteFile(View view) {
        try {
            String fileName = "couponstemp";
            File file = File.createTempFile(fileName, null, getCacheDir());

            file.delete();
        } catch (IOException e) {
        }
    }

    public void writeFileExternalStorage(View view,String text) {
        //String cashback = "Get 2% cashback on all purchases from xyz \n Get 10% cashback on travel from dhhs shop";
        String cashback=text;
        String state = Environment.getExternalStorageState();
        //external storage availability check
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), filenameExternal);


        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether to append or create new file if one exists
            outputStream = new FileOutputStream(file, true);

            outputStream.write(cashback.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
