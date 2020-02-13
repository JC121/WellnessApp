package com.example.testapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button Register = (Button) findViewById(R.id.RegisterButton);

        Register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override public void onClick(View view) {
                TextView Email = (TextView) findViewById(R.id.editEmailText);
                TextView Password = (TextView) findViewById(R.id.editPasswordText);
                TextView Username = (TextView) findViewById(R.id.editUserNameText);
                //http://localhost:8080/login/createLogin?email=testmail.com&username=test1&password=test
                Log.d("[Link]","http://10.0.2.2:8080/login/createLogin?email="+Email.getText().toString()+
                        "&username="+Username.getText().toString()+
      "&password="+ Password.getText().toString());
                try {
                    HTTPRequestGet(new URL("http://10.0.2.2:8080/login/createLogin?email="+Email.getText().toString()+
                            "&username="+Username.getText().toString()+
                            "&password="+Password.getText().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private String HTTPRequestGet(URL url){
        String response = "";
        HttpURLConnection urlConnection = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream input = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int data = reader.read();
            while(data != -1){
                char currentChar = (char) data;
                data = reader.read();
                response += currentChar;
            }
        }catch(Exception e){
            Log.e("[ERROR]", "HTTPRequestGet: DOESNT CONNECT TO: " + url.getPath());
            Toast errorMessageToast = Toast.makeText(getApplicationContext(),"Can not connect to server",Toast.LENGTH_LONG);
            errorMessageToast.show();
            Log.e("[ERROR]",e.getMessage());
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return response;
    }
}

