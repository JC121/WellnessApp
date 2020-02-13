package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DataModels.Quiz;
import DataModels.ResponseModel;

public class FrontPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String response = "";
        try{
            response = HTTPRequestGet(new URL("http://10.0.2.2:8080/quiz/getQuizNames"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        if(!response.isEmpty()){
            Log.e("[ResponseFromServer]", response);
            // converty to object
        }else{
            Log.e("[ResponseFromServer]", "no response");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = new ResponseModel();

        try{
            responseModel = objectMapper.readValue(response,ResponseModel.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(responseModel != null) {
            Log.e("[Object]", responseModel.toString());
        }

        ArrayList<String> nameOfQuizzes = new ArrayList<>();
        // https://www.tutorialspoint.com/how-can-we-convert-a-json-array-to-a-list-using-jackson-in-java
        try {
           assert responseModel != null;
           // "["diabetes quiz"]"
           String[] tempList = objectMapper.readValue(response.substring(response.indexOf("data")+6 ,response.lastIndexOf("}")), String[].class);
           if(tempList.length !=0){
               nameOfQuizzes.addAll(Arrays.asList(tempList));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("[List of quiz Names]",nameOfQuizzes.toString());
        setContentView(R.layout.activity_front_page_actitivity);



        Button quizButton = (Button) findViewById(R.id.quiz_button);
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontPageActivity.this, listOfQuizActivity.class));
            }
        });
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        setContentView(scrollView);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(200,80,80,200);

        for(int i = 0;i<nameOfQuizzes.size();i++){
            final Button tempBtn = new Button(this);
            layout.addView(tempBtn);
            tempBtn.setText(nameOfQuizzes.get(i));
            tempBtn.setWidth(320);
            tempBtn.setHeight(40);
            tempBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String response2 = "";
                    // http://localhost:8080/quiz/getQuiz?name=Diabetes Quiz
                    try{
                        response2 = HTTPRequestGet(new URL("http://10.0.2.2:8080/quiz/getQuiz?name="+tempBtn.getText()));
                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }
                    if(!response2.isEmpty()){
                        Log.e("[ResponseFromServer]", response2);
                        // converty to object
                    }else{
                        Log.e("[ResponseFromServer]", "no response");
                    }

                    //converting tho JSON into POJO
                    ObjectMapper objectMapper = new ObjectMapper();
                    ResponseModel responseModel = new ResponseModel();

                    try{
                        responseModel = objectMapper.readValue(response2,ResponseModel.class);
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if(responseModel != null) {
                        Log.e("[Object]", responseModel.toString());
                    }
                    Quiz tempQuiz = new Quiz(null,null);
                    try {
                        String parsedResp = response2.substring(response2.indexOf("data")+6 ,response2.lastIndexOf("}"));
                        Log.d("[parsed]", parsedResp);
                        tempQuiz = objectMapper.readValue(parsedResp,Quiz.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("[Quiz]",tempQuiz.toString());

                    startActivity(new Intent(FrontPageActivity.this, listOfQuizActivity.class));

                }
            });
        }

        Button LogoutButton = new Button(this);
        layout.addView(LogoutButton);
        scrollView.addView(layout);

        LogoutButton.setText("Logout");
        LogoutButton.setWidth(320);
        LogoutButton.setHeight(40);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FrontPageActivity.this, MainActivity.class));
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
