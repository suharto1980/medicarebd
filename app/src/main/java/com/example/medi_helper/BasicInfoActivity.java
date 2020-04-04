package com.example.medi_helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class BasicInfoActivity extends AppCompatActivity  implements View.OnClickListener {


    private CardView BP,BMR,BMI,Daibetes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);



        BP=findViewById(R.id.optionBP);
        BMR=findViewById(R.id.optionBMR);
        BMI=findViewById(R.id.optionBMI);
        Daibetes=findViewById(R.id.optionDaibetes);

        BP.setOnClickListener(this);
        BMR.setOnClickListener(this);
        BMI.setOnClickListener(this);
        Daibetes.setOnClickListener(this);

    }


    public void onClick(View v){

        Intent intent;

        switch(v.getId()){

            case R.id.optionBP:{

                intent=new Intent(this,Current_medi_info.class);
                startActivity(intent);
                break;
            }
            case R.id.optionBMR:{

                intent=new Intent(this,Current_medi_info.class);
                startActivity(intent);
                break;
            }
            case R.id.optionBMI:{

                intent=new Intent(this,Current_medi_info.class);
                startActivity(intent);
                break;
            }

            case R.id.optionDaibetes:{

                intent=new Intent(this,Current_medi_info.class);
                startActivity(intent);
                break;
            }

        }

    }

}
