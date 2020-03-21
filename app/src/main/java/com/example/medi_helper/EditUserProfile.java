package com.example.medi_helper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserProfile extends AppCompatActivity implements  View.OnClickListener {

    Button save;
    DBconnection dBconnection;
    private EditText Name,Emergebcy1,Emargency2,Address;
    private TextView pateintId,phone;
   // EditText uname ,uphone,uemargencyNumber1,getUemargencyNumber2,uAddress;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);


        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("তথ্য সংস্কার");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dBconnection = new DBconnection(this);
        save=findViewById(R.id.buttonSave);
        save.setOnClickListener(this);

        Name=findViewById(R.id.editName);
        Emargency2=findViewById(R.id.editEmargencyNumber_2);
        Emergebcy1=findViewById(R.id.editEmargencyNumber_1);
        Address=findViewById(R.id.editAddress);
        pateintId=findViewById(R.id.viewPID);
        phone=findViewById(R.id.viewMobile);



        viewdata();

    }
    public void onClick(View v){


        Intent intent;

        switch(v.getId()){

            case R.id.buttonSave:{

                updatedata();
                break;
            }

        }

    }


    public void updatedata(){
        String phonenumber=DataHolder.Phone;
        SQLiteDatabase sqLiteDB= dBconnection.getWritableDatabase();
        String pName=Name.getText().toString().trim();
        String EPhonenumber= Emergebcy1.getText().toString().trim();
        String anothernumber=Emargency2.getText().toString().trim();
        String address=Address.getText().toString().trim();

        if (Emergebcy1.equals("")||Emargency2.equals("")){


            Toast.makeText(this,"Must input number-1 and number-2",Toast.LENGTH_SHORT).show();

        }
        else {
            sqLiteDB.execSQL("UPDATE  patient SET EmargencyNumber_1='"+ EPhonenumber +"',EmargencyNumber_2='"+anothernumber+"',pname='"+ pName +"',paddress='"+address+"' WHERE pphone_number='"+phonenumber+"'");

            Toast.makeText(this,"UPDATE",Toast.LENGTH_SHORT).show();
            Intent intent;
            intent=new Intent(this,UserProfile.class);
            startActivity(intent);
        }
    }

public void viewdata(){
    dBconnection = new DBconnection(this);
    SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
    String phn=DataHolder.Phone;
    Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
    if (cursor.moveToNext())
    {

        Name.setText(cursor.getString(1)); ;
        pateintId.setText(cursor.getString(0)); ;
        phone.setText(cursor.getString(2)); ;
        Emergebcy1.setText(cursor.getString(13)); ;
        Emargency2.setText(cursor.getString(14)); ;
        Address.setText(cursor.getString(3)); ;


    }
    else {

        Toast.makeText(this,"No data is found",Toast.LENGTH_LONG);


    }


}


}

