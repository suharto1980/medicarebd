package com.example.medi_helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.text.DecimalFormat;

public class Current_medi_info extends AppCompatActivity implements   View.OnClickListener {

      private EditText Bp, Weight, Hight;
      private TextView Bmr, Bmi,Uage,Uname;
      Button updateButton;
      DBconnection dBconnection;
      Float pBMI,pBMAR;
      String pGender,pAge;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_medi_info);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("বর্তমান তথ্য");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBconnection = new DBconnection(this);


        Uname=findViewById(R.id.ViewpName);
        Uage=findViewById(R.id.ViewAge);
        Bp = findViewById(R.id.inputBp);
        Weight=findViewById(R.id.inputWeight);
        Hight = findViewById(R.id.inputHeigth);
        Bmi = findViewById(R.id.textViewBmi);
        Bmr = findViewById(R.id.textViewBmr);

        updateButton = findViewById(R.id.buttonSubmit);
        updateButton.setOnClickListener(this);

        //this is the filed view emergency Number  from database
        // data view for profile
        viewdata();




}

    @Override
    public void onClick(View view) {

        updatedata();


    }

    public void viewdata(){
        dBconnection = new DBconnection(this);
        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            Uname.setText(cursor.getString(1));
            Uage.setText(cursor.getString(4));
            Bp.setText(cursor.getString(12));
            Weight.setText(cursor.getString(7));
            Hight.setText(cursor.getString(8));
            pGender=(cursor.getString(6));
            pAge=(cursor.getString(4));

            BMIcheack();//method call for BMI
            BMRcheack();//method call for BMR

        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }

    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        String updateBP=Bp.getText().toString().trim();
        String updateHight=Hight.getText().toString().trim();
        String updateWeight=Weight.getText().toString().trim();



        if (updateBP.equals("")||updateHight.equals("")){


            Toast.makeText(this,"Must input number-1 and number-2",Toast.LENGTH_SHORT).show();

        }
        else {

            sqLiteDB.execSQL("UPDATE  patient SET pbp='"+ updateBP +"',pheight='"+updateHight+"',pweight='"+updateWeight+"',pbmi='"+pBMI+"',pbmr='"+pBMAR+"' WHERE pphone_number='"+phonenumber+"'");

            // sqLiteDB.execSQL("UPDATE  patient SET pbp='"+ updateBP +"',pheight='"+updateHight+"',pweight='"+updateWeight+"',pbmr='"+updateHight+"',pbmi='"+updateWeight+"' WHERE pphone_number='"+phonenumber+"'");

            Toast.makeText(this,"UPDATE",Toast.LENGTH_SHORT).show();
        }
    }

    public void BMIcheack(){

        String pweight=Weight.getText().toString();
        Float PW=Float.parseFloat(pweight);
        String phight=Hight.getText().toString();
        Float PH=Float.parseFloat(phight);
        Bmr.setText(pweight);
        String Contents= "0.3048";//per foot to meter
        Float Fnumber=Float.parseFloat(Contents);
        pBMI=PW/(PH*PH*Fnumber*Fnumber);//BMI Calculation
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//float formattinfg(like 2.23)
        String viewBMI=String.valueOf(decimalFormat.format(pBMI));

        //Bmi.setText();

        if(pBMI<=18.5){
            Bmi.setText(viewBMI+"   UNDER");
        }
        else if(pBMI>18.55 &&pBMI<=29.99){
            Bmi.setText(viewBMI+"   OK");

        }
        else  {

            Bmi.setText(viewBMI+"   OBESITY");
        }



    }

    public void BMRcheack(){

        String pweight=Weight.getText().toString();
        Float PW=Float.parseFloat(pweight);
        String phight=Hight.getText().toString();
        Float PH=Float.parseFloat(phight);
        Float P_age=Float.parseFloat(pAge);



        if(pGender=="মহিলা" ||pGender=="অন্যান্য" ){

            String pWomanWeightConstrant,pWomanHightConstrant,pWomanAgeConstrant;
            pWomanWeightConstrant= "9.6";
            pWomanHightConstrant="1.8";
            pWomanAgeConstrant="4.78";

            Float PWWC=Float.parseFloat(pWomanWeightConstrant);
            Float PWHC=Float.parseFloat(pWomanHightConstrant);
            Float PWAC=Float.parseFloat(pWomanAgeConstrant);

            pBMAR=655+(PWWC*PW)+(PWHC*PH)-(PWAC*P_age);//BMR Calculation
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//float formattinfg(like 2.23)
            String viewBMR=String.valueOf(decimalFormat.format(pBMAR));

            Bmr.setText(viewBMR);

        }

        else {

            String pManWeightConstrant,pManHightConstrant,pManAgeConstrant;
            pManWeightConstrant= "13.7";
            pManHightConstrant="5";
            pManAgeConstrant="6.8";

            Float PMWC=Float.parseFloat(pManWeightConstrant);
            Float PMHC=Float.parseFloat(pManHightConstrant);
            Float PMAC=Float.parseFloat(pManAgeConstrant);

            pBMAR=665+(PMWC*PW)+(PMHC*PH)-(PMAC*P_age);//BMR Calculation
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//float formattinfg(like 2.23)
            String viewBMR=String.valueOf(decimalFormat.format(pBMAR));

            Bmr.setText(viewBMR);



        }
    }
}