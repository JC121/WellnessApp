package com.example.testapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import DataModels.Questions;
import DataModels.Quiz;

public class listOfQuizActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_quiz);
        final String userAns[] = new String[2];
        final ArrayList<String> quest1Ans = new ArrayList<>(Arrays.asList(new String[]{"4","1", "2", "3"}));
        Questions question1 = new Questions("What is 2+2?",quest1Ans);


        final ArrayList<String> quest2ans = new ArrayList<>(Arrays.asList(new String[]{"2","1", "4", "3"}));
        Questions question2 = new Questions("What is 1+1?",quest2ans);
        ArrayList<Questions> questions1 = new ArrayList<>();
        questions1.add(question1);
        questions1.add(question2);
        Quiz quiz1 = new Quiz("Math",questions1);


        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(200,80,80,200);
        int RBCount = 0;

        for( int i = 0; i < quiz1.getListOfQuestions().size(); i++ )
        {
            TextView textView = new TextView(this);
            textView.setTypeface(Typeface.create("sans-serif-medium",Typeface.BOLD));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,32);
            textView.setText(quiz1.getListOfQuestions().get(i).getQuestion());
            linearLayout.addView(textView);

            RadioGroup rg = new RadioGroup(this); //create the RadioGroup
            RadioButton rb ;
            rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
            for(int j=0; j<quiz1.getListOfQuestions().get(i).getAnswers().size(); j++){
                rb = new RadioButton(this);
                rb.setText( quiz1.getListOfQuestions().get(i).getAnswers().get(j));
                rb.setId(RBCount);
                rg.addView(rb);
                RBCount++;
            }
            linearLayout.addView(rg);//you add the whole RadioGroup to the layout
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @SuppressLint("ResourceType")
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                    RadioButton rb=(RadioButton)findViewById(checkedId);
                    // 0-3 index 0, meaning question 1
                    // 4-7 index 1, meaning question 2
                    if(rb.getId() < 4 && rb.getId() >= 0){
                        //Toast.makeText(getApplicationContext(),"Question 1:" + String.valueOf(rb.getId()),Toast.LENGTH_SHORT).show();
                        userAns[0]= rb.getText().toString();
                    }else if(rb.getId() >= 4 && rb.getId() <8){
                        //Toast.makeText(getApplicationContext(),"Question 2:" + String.valueOf(rb.getId()),Toast.LENGTH_SHORT).show();
                        userAns[1]= rb.getText().toString();
                    }
                    //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        Button submit = new Button(this);
        submit.setText("Submit");

        linearLayout.addView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double count = 0;
                if(userAns[0].equalsIgnoreCase(quest1Ans.get(0))){
                    count++;
                }
                if(userAns[1].equalsIgnoreCase(quest2ans.get(0))){
                    count++;
                }
                System.out.println(count);
                System.out.println(quest2ans.get(0));
                Toast.makeText(getApplicationContext(),"Percent of correct: " +((count/2)*100) + "%",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
