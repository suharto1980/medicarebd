package com.example.medi_helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Prescription extends AppCompatActivity implements View.OnClickListener{

   Button current,newPrescriptionAdd;
   DBconnection dBconnection;
   String PID,PNAME;
   private AlertDialog.Builder alertDialogBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("বর্তমান তথ্য");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBconnection = new DBconnection(this);


        current=findViewById(R.id.buttonCurrent);
        newPrescriptionAdd=findViewById(R.id.buttonnewPrescriptionAdd);
        current.setOnClickListener(this);

        viewdata();//method call for data view
    }

        @Override
        public void onClick(View view) {

            Intent intent;

            switch(view.getId()){

                case R.id.buttonCurrent:{

                    findPCurrentPrescription ();
                    break;
                }

            }
        }


    public void viewdata() {
        dBconnection = new DBconnection(this);
        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            PID = (cursor.getString(0));
            PNAME = (cursor.getString(1));

        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }


    }


    public  void findPCurrentPrescription (){
        dBconnection = new DBconnection(this);
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
          String p=PID;

        Cursor cursor =sqLiteDatabase.rawQuery("Select * from medi_problem WHERE _pid='"+p+"' ",null);

        if (cursor.getCount()==0)
        {

            popup();


        }
        else {

            //go to next page
            Intent intent1;
            intent1=new Intent(this,PrescriptionView.class);
            startActivity(intent1);
        }
    }

    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(Prescription.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
       String pName= PNAME.toString();
        alertDialogBuilder.setMessage(pName+",আপনি কোন সমস্যা নিবন্ধন করেনি ");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit
                dialog.cancel();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}