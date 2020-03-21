package com.example.medi_helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionView extends AppCompatActivity {

    TextView plmView,plmSlv,paitent_Name,paitent_Age,viewinfo,doctorName,doctorPhn;
    String PID;
    Integer DoctorChecekPlm;
    DBconnection dBconnection;
    RelativeLayout relativeLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);


        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("বর্তমান তথ্য");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBconnection = new DBconnection(this);



        plmView=findViewById(R.id.viewPlm);
        plmSlv=findViewById(R.id.viewPlmSlv);
        paitent_Name=findViewById(R.id.paitentName);
        paitent_Age=findViewById(R.id.paitentAge);
        relativeLayout=findViewById(R.id.doctorinfo);
        viewinfo=findViewById(R.id.view18);
        doctorName=findViewById(R.id.DoctorName);
        doctorPhn=findViewById(R.id.doctorPhn);


        viewdata();//method call


        //int c3= Color.rgb(236, 112, 99);
        int  c4=Color.rgb(231, 76, 60 );

        if(DoctorChecekPlm==0){

            relativeLayout.setBackgroundColor(c4);
            doctorName.setText("ডাক্তার এখন ও সমস্যা দেখেনি ");
            doctorPhn.setTextColor(c4);
            viewinfo.setTextColor(c4);

        }
        else{

            relativeLayout.setBackgroundColor(c4);

        }

    }


    public void viewdata(){
        dBconnection = new DBconnection(this);
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        String phn=DataHolder.Phone;

        //View for patient Table
        Cursor cursor1 =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);


        if (cursor1.moveToNext())
        {
            paitent_Name.setText(cursor1.getString(1));
            paitent_Age.setText(cursor1.getString(4));
            PID=(cursor1.getString(0));

        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }


        //View for Medi PLm Table
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from medi_problem WHERE _pid='"+PID+"' ",null);

        if (cursor.moveToNext())
        {
            plmView.setText(cursor.getString(1));
            plmSlv.setText(cursor.getString(2));
            String DoctorId=(cursor.getString(3));

            DoctorChecekPlm=Integer.parseInt(DoctorId);
        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }
}
