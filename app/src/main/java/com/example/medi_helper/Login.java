package com.example.medi_helper;



import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity implements View.OnClickListener {

    DBconnection dbconnection;

    public static final String DATA_PASS="com.example.application.example.DATA_PASS";
    public static final String DATA_PASSN="com.example.application.example.DATA_PASSN";
    private AlertDialog.Builder alertDialogBuilder;

    private TextView newaccount,forgetpassword;
    private ImageButton sign_in;
    private EditText umobile,upassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dbconnection=new DBconnection(this);



        umobile=findViewById(R.id.inputMobile2);
        upassword=findViewById(R.id.inputPassword2);
        newaccount=findViewById(R.id.textNewAccount);
        forgetpassword=findViewById(R.id.textForgetpassword);
        sign_in =findViewById(R.id.buttonSignIn);

        newaccount.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
        sign_in.setOnClickListener(this);
        SQLiteDatabase sqLiteDatabase=dbconnection.getWritableDatabase();



    }


    @Override

    public void onClick(View v){
        String usermobile =umobile.getText().toString();
        String userpassword =upassword.getText().toString();
        Intent intent;


        switch(v.getId()){

            case R.id.textNewAccount:{

                intent=new Intent(this,Registration_form.class);
                startActivity(intent);
                break;
            }
            case R.id.textForgetpassword:{

                onBackPressed();
                break;
            }
            case R.id.buttonSignIn:{

                Boolean result = dbconnection.findPassword(usermobile,userpassword);

                if(result==true){

                    DataHolder.Phone=usermobile;
                    intent=new Intent(this,Home_Page.class);
                    startActivity(intent);

                } else{

                    //AlertDialog.Builder builder =new AlertDialog.Builder(view.getContet());
                    Toast.makeText(this,"User Name OR Password Wrong",Toast.LENGTH_SHORT).show();

                }


            }


        }


    }

    public void forViewPassdata(){
        String usermobile =umobile.getText().toString();
        Intent intent2 = new Intent(this,UserProfile.class);
        intent2.putExtra(DATA_PASS,usermobile);
        startActivity(intent2);

   //return usermobile;


    }


    public void onBackPressed() {

        alertDialogBuilder = new AlertDialog.Builder(Login.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
        alertDialogBuilder.setMessage("দয়া করে মাঠকর্মী সাথে কথা বলুন ");

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


    public void finger(){


    }

}
