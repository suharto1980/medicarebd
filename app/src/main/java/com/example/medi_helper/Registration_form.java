package com.example.medi_helper;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Registration_form extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText userName,userPhoneNumber,userAge,userAddress,userBloodGroup,userPassword;
    private RadioGroup radioGroup;
    private RadioButton userGender;
    private Button registration,dateOfBrith;
    UserRegistrationDetails userRegistrationDetails;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;
    int age;
    long ageFromdate;


    DBconnection dBconnection;
    private AlertDialog.Builder alertDialogBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        radioGroup = findViewById(R.id.radioGroupGrender);

        userName = findViewById(R.id.inputName2);
        userPhoneNumber = findViewById(R.id.inputMobile2);
        userAge = findViewById(R.id.inputAge2);
        userBloodGroup = findViewById(R.id.inputBloodGroup2);
        userAddress = findViewById(R.id.inputAddress2);
        userPassword = findViewById(R.id.inputPassword2);
        dateOfBrith=findViewById(R.id.buttonDateOfBrith);
        registration = findViewById(R.id.buttonSignUp);

        userRegistrationDetails = new UserRegistrationDetails();
        dBconnection = new DBconnection(this);

        registration.setOnClickListener(this);

        ageinput();

    }



    public void ageinput(){

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        dateOfBrith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Registration_form.this
                        ,dateSetListener1,year,month,day

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });

        dateSetListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {

                Month = Month+1;
               String date= Day+"/"+ Month +"/"+Year;
                dateOfBrith.setText(date);
                //CheckeBloodDonationAvailability();
            }
        };

           String Bdate=dateOfBrith.getText().toString();

          // String currentDate= DateFormat.getDateInstance().format("dd/MM/yyyy");
        String currentDate="02/05/2020";

        try {
            Date date1 =simpleDateFormat.parse(Bdate);
            Date date2 = simpleDateFormat.parse(currentDate);

            long dateOfbrith=date1.getTime();
            long currentD=date2.getTime();
            ageFromdate=date1.getTime();
            if (dateOfbrith<=currentD){

                org.joda.time.Period period =new Period(dateOfbrith,currentD, PeriodType.yearMonthDay());
                age=period.getYears();
            }



        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void onClick(View view) {

        Intent intent;

        localDataSave();
        ApiDataSend();

        long rowid = dBconnection.inserdata(userRegistrationDetails);

        if (rowid>0){
           // Toast.makeText(getApplicationContext(),"Row"+rowid+" is insert",Toast.LENGTH_SHORT).show();
            popup();//popup window

            intent=new Intent(this,Register_Activity.class);
            startActivity(intent);


        }
        else {
            Toast.makeText(getApplicationContext(),"NO DATA INSERT",Toast.LENGTH_SHORT).show();

        }
    }


    private  void localDataSave(){


        //radio button ar value chack
        int selectedID= radioGroup.getCheckedRadioButtonId();
        userGender=(RadioButton) findViewById(selectedID);
        String Gender= userGender.getText().toString();


        //converting all value to string
        String Name= userName.getText().toString();
        String MobileNumber= userPhoneNumber.getText().toString();
        String Age= userAge.getText().toString();
        String Address= userAddress.getText().toString();
        String BloodGroup= userBloodGroup.getText().toString();
        String Password= userPassword.getText().toString();
        String Weight="0",Hight="0",Bmr="0",Bmi="0",BP="0",Emargency_Number1="0",Emargency_number2="0";

        //pass to data UserRagistrationDetails cls , Because Setter and getter is there
        userRegistrationDetails.setPname(Name);
        userRegistrationDetails.setpMobileNumber(MobileNumber);
        userRegistrationDetails.setpAge(Age);
        userRegistrationDetails.setPgender(Gender);
        userRegistrationDetails.setpAddress(Address);
        userRegistrationDetails.setpBloodGroup(BloodGroup);
        userRegistrationDetails.setpPassword(Password);
        userRegistrationDetails.setpWeight(Weight);
        userRegistrationDetails.setpHight(Hight);
        userRegistrationDetails.setpBmi(Bmi);
        userRegistrationDetails.setpBmr(Bmr);
        userRegistrationDetails.setpBP(BP);
        userRegistrationDetails.setpEmargencyNumber1(Emargency_Number1);
        userRegistrationDetails.setpEmargencyNumber2(Emargency_number2);
    }

     //this function for API data send
    private void ApiDataSend() {

        //radio button ar value chack
        int selectedID= radioGroup.getCheckedRadioButtonId();
        userGender=(RadioButton) findViewById(selectedID);
        String Gender= userGender.getText().toString();


        //converting all value to string
        int id=1;
        String Name= userName.getText().toString();
        String MobileNumber= userPhoneNumber.getText().toString();
        String Age= userAge.getText().toString();
        String Address= userAddress.getText().toString();
        String BloodGroup= userBloodGroup.getText().toString();
        String Password= userPassword.getText().toString();
        String Weight="0",Hight="0",Bmr="0",Bmi="0",BP="0",Emargency_Number1="0",Emargency_number2="0";

       //String dateOfBrith=ageFromdate.toString();



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://suharto1980-001-site1.ftempurl.com/api/PatientsApi";

        JSONObject prams = new JSONObject();
        try {
            prams.put("pname",Name);
            prams.put("pphone",MobileNumber);
            prams.put("paddress",Address);
            prams.put("pgender", Gender);
            prams.put("pweight",Weight);
            prams.put("phight",Hight);
            prams.put("pbmi",Bmi);
            prams.put("pbmr",Bmr);
            prams.put("pbp",BP);
            prams.put("pdate_of_brith",Age);
            prams.put("ppassword",Password);
            prams.put("pdisease","");
            prams.put("pdiseaseSlove", "");
            prams.put("mid",id);


        } catch (Exception e) {

        }

        //method
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, prams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Done"+response);
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










    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(Registration_form.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
        String pName= userName.getText().toString();
        alertDialogBuilder.setMessage("Hi "+pName+",আপনার রেজিস্ট্রেশন সম্পূর্ণ হয়েছে,আপনাকে যাচাই করার জন্য একটি OTP পাঠানো হচ্ছে । ধন্যবাদ  ");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exit from popup
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}




