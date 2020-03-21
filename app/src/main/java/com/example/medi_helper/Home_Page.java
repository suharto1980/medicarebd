package com.example.medi_helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home_Page extends AppCompatActivity  implements View.OnClickListener{

    private CardView  blood_histroy,problem_registration,current_info,emargency,profile,prescription;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        //for view date
        Calendar calendar = Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        //String currentDate= DateFormat.getDateInstance(SimpleDateFormat.("dd/MM/yyyy")).format(calendar.getTime());
        TextView textViewDate =findViewById(R.id.viewDate);
        textViewDate.setText(currentDate);

        prescription=findViewById(R.id.optionPrescription);
        profile=findViewById(R.id.optionProfile);
        blood_histroy=findViewById(R.id.optionBloodHistory);
        problem_registration=findViewById(R.id.optionProblemRegistration);
        current_info=findViewById(R.id.optionCurrentInfo);
        emargency=findViewById(R.id.optionEmargency);

        blood_histroy.setOnClickListener(this);
        problem_registration.setOnClickListener(this);
        current_info.setOnClickListener(this);
        emargency.setOnClickListener(this);
        profile.setOnClickListener(this);
        prescription.setOnClickListener(this);

    }

    public void onClick(View v){

        Intent intent;

         switch(v.getId()){

             case R.id.optionBloodHistory:{

                 intent=new Intent(this,Blood_History.class);
              startActivity(intent);
              break;
             }
             case R.id.optionProblemRegistration:{

                 intent=new Intent(this,Problem_Registration.class);
                 startActivity(intent);
                 break;
             }
             case R.id.optionCurrentInfo:{

                 intent=new Intent(this,Current_medi_info.class);
                 startActivity(intent);
                 break;
             }

             case R.id.optionEmargency:{

                 intent=new Intent(this,Emergency.class);
                 startActivity(intent);
                 break;
             }
             case R.id.optionProfile:{

                 intent=new Intent(this,UserProfile.class);
                 startActivity(intent);
                 break;

             }
             case R.id.optionPrescription:{

                 intent=new Intent(this,Prescription.class);
                 startActivity(intent);
                 break;

             }


         }

    }
}
