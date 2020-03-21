package com.example.medi_helper;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Problem_Registration extends AppCompatActivity implements   View.OnClickListener {

    private EditText Bp, Weight, Hight;
    private TextView Bmr, Bmi,Uage,Uname;
    String PID,PNAME;
    private TextInputEditText plmDe;
    Button updateButton;
    DBconnection dBconnection;
    private AlertDialog.Builder alertDialogBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem__registration);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("সমস্যা নিবন্ধন ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dBconnection = new DBconnection(this);


        Uname=findViewById(R.id.ViewpName);
        Uage=findViewById(R.id.ViewAge);
        plmDe=findViewById(R.id.inputPlm);


        updateButton = findViewById(R.id.buttonSubmit);
        updateButton.setOnClickListener(this);

        //this is the filed view emergency Number  from database
        // data view for profile
        viewdata();




    }

    @Override
    public void onClick(View view) {

        inseartdata();


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
            PID=(cursor.getString(0));
            PNAME=(cursor.getString(1));


        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }

    public void inseartdata(){

        dBconnection = new DBconnection(this);
        String slv="0",doctorId="0";
        String Plm_DETAILS=plmDe.getText().toString().trim();

        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("plm_details",Plm_DETAILS);
        contentValues.put("plm_slv",slv);
        contentValues.put("doctor_id",doctorId);
        contentValues.put("_pid",PID);
        sqLiteDatabase.insert("medi_problem",null,contentValues);

        popup();
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

            sqLiteDB.execSQL("UPDATE  patient SET pbp='"+ updateBP +"',pheight='"+updateHight+"',pweight='"+updateWeight+"' WHERE pphone_number='"+phonenumber+"'");

            // sqLiteDB.execSQL("UPDATE  patient SET pbp='"+ updateBP +"',pheight='"+updateHight+"',pweight='"+updateWeight+"',pbmr='"+updateHight+"',pbmi='"+updateWeight+"' WHERE pphone_number='"+phonenumber+"'");

            Toast.makeText(this,"UPDATE",Toast.LENGTH_SHORT).show();
        }
    }


    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(Problem_Registration.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
        alertDialogBuilder.setMessage(PNAME+",আপনার সমস্যা নিবন্ধন করা হয়েছে");

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



