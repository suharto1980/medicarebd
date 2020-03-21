package com.example.medi_helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Emergency extends AppCompatActivity {


    EditText txt_maessage,Emargency_nnumber1,Emargency_nnumber2;
    private String massage;
    //private String testNumber="01675977369";
    private AlertDialog.Builder alertDialogBuilder;
    TextView ViewanotherNumber;
    DBconnection dBconnection;
    String PNAME;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        dBconnection = new DBconnection(this);


        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("জরুরি যোগাযোগ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Emargency_nnumber1=findViewById(R.id.EmargencyNumber_1);
        Emargency_nnumber2=findViewById(R.id.EmargencyNumber_2);

        //this is the filed view emergency Number  from database
              // data view for profile
        String phn=DataHolder.Phone;
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {
            Emargency_nnumber1.setText(cursor.getString(13));
            Emargency_nnumber2.setText(cursor.getString(14));
            PNAME=(cursor.getString(1)).toString();
        }
        else {

            Toast.makeText(this,"No data is found",Toast.LENGTH_LONG);


        }

    }




    public  void btn_sender(View view){
        int permissionChek=ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permissionChek== PackageManager.PERMISSION_GRANTED){

            MyMessage();

        }
        else {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},0);

        }



    }

    private void MyMessage() {
       // String p=PNAME.toString().trim();
        //massage= ("আমি"+p+" হঠাৎ আমার শরীর অসুস্থ অনুভব হচ্ছে, তাড়াতাড়ি যোগাযোগ করেন।  ");

         massage= " হঠাৎ আমার শরীর অসুস্থ অনুভব হচ্ছে, তাড়াতাড়ি যোগাযোগ করেন।  ";

        String phonenumber= Emargency_nnumber1.getText().toString().trim();
        String anothernumber=Emargency_nnumber2.getText().toString().trim();

        //checke mobile is not null
        if(!Emargency_nnumber1.getText().toString().equals("")){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber,null,massage,null,null);
        smsManager.sendTextMessage(anothernumber,null,massage,null,null);
        Toast.makeText(this,"sms send",Toast.LENGTH_SHORT).show();

            //for popup window
            //alertDialogBuilder=new AlertDialog.Builder(Emergency.this);
            //alertDialogBuilder.setMessage(R.string.popup_alert_massage);


            updatedata();
        }
}


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:
                if(grantResults.length >= 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                 MyMessage();
                 updatedata();
                }
                else {

                    Toast.makeText(this,"You Don't have permission to send SMS",Toast.LENGTH_SHORT).show();
                }


        }
    }


    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        String EPhonenumber= Emargency_nnumber1.getText().toString().trim();
        String anothernumber=Emargency_nnumber2.getText().toString().trim();


        if (Emargency_nnumber1.equals("")||Emargency_nnumber2.equals("")){


            Toast.makeText(this,"Must input number-1 and number-2",Toast.LENGTH_SHORT).show();

        }
        else {
            sqLiteDB.execSQL("UPDATE  patient SET EmargencyNumber_1='"+ EPhonenumber +"',EmargencyNumber_2='"+anothernumber+"' WHERE pphone_number='"+phonenumber+"'");

            //Toast.makeText(this,"UPDATE",Toast.LENGTH_SHORT).show();
        }
    }

}

