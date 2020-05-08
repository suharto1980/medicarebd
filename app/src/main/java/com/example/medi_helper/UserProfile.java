package com.example.medi_helper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity  implements View.OnClickListener {

    private ImageButton imageButton ;
    private TextView showName,showMobile,showEmargencyNumber1,showEmargencyNumber2,showPatientID,showAddress,showGender;
    Login login;

    DBconnection dBconnection;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("নিজের তথ্য");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageButton =findViewById(R.id.editButton);
        imageButton.setOnClickListener(this);
        dBconnection = new DBconnection(this);

        showName=findViewById(R.id.textName);
        showMobile=findViewById(R.id.textShowMobile);
        showEmargencyNumber1=findViewById(R.id.textEmargencyNumber_1);
        showEmargencyNumber2=findViewById(R.id.textEmargencyNumber_2);
        showPatientID=findViewById(R.id.textPID);
        showAddress=findViewById(R.id.textViewAddress);
        showGender=findViewById(R.id.textGender);



        Intent intent=getIntent();
        UserRegistrationDetails userRegistrationDetails=(UserRegistrationDetails)intent.getSerializableExtra("userRegistrationDetails");
        showMobile.setText(DataHolder.Phone);
        String phn=DataHolder.Phone;


      // data view for profile
        SQLiteDatabase sqLiteDatabase= dBconnection.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("Select * from patient WHERE pphone_number='"+phn+"' ",null);
        if (cursor.moveToNext())
        {

            showName.setText(cursor.getString(1)); ;
            showPatientID.setText(cursor.getString(0)); ;
            showGender.setText(cursor.getString(6)); ;
            showEmargencyNumber1.setText(cursor.getString(13)); ;
            showEmargencyNumber2.setText(cursor.getString(14)); ;
            showAddress.setText(cursor.getString(3)); ;


        }
        else {

                Toast.makeText(this,"No data is found",Toast.LENGTH_LONG);


            }

        getData();
        }


    public void onClick(View v){


        Intent intent;

        switch(v.getId()){

            case R.id.editButton:{

                intent=new Intent(this,EditUserProfile.class);
                startActivity(intent);
                break;
            }

        }

    }

   //for data read from API
    private void getData(){
        String URL="http://suharto1980-001-site1.ftempurl.com/api/PatientsApi/{id}"+DataHolder.pid;


        RequestQueue requestQueue = Volley.newRequestQueue(this);



        //method
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String Name=response.getString("pname");
                    System.out.println("Data "+Name);
                    showName.setText(Name);
                }catch (Exception ex){
                    System.out.println("Data not send"+ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println( "ERROR"+error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}

