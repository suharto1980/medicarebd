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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Registration_form extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText userName,userPhoneNumber,userAge,userAddress,userBloodGroup,userPassword;
    private RadioGroup radioGroup;
    private RadioButton userGender;
    private Button registration,dateOfBrith;
    UserRegistrationDetails userRegistrationDetails;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;
    int age;




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

            if (dateOfbrith<=currentD){

                org.joda.time.Period period =new Period(dateOfbrith,currentD, PeriodType.yearMonthDay());
                age=period.getYears();
            }



        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public void onClick(View view) {

        //radio button ar valu chack
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
        long rowid = dBconnection.inserdata(userRegistrationDetails);

        if (rowid>0){

           // Toast.makeText(getApplicationContext(),"Row"+rowid+" is insert",Toast.LENGTH_SHORT).show();

            popup();//popup window
        }
        else {
            Toast.makeText(getApplicationContext(),"NO DATA INSERT",Toast.LENGTH_SHORT).show();

        }


    }

    public void popup() {

        alertDialogBuilder = new AlertDialog.Builder(Registration_form.this);

        //for setting title
        alertDialogBuilder.setTitle("বার্তা");

        //for setting the message
        String pName= userName.getText().toString();
        alertDialogBuilder.setMessage("Hi "+pName+",আপনার রেজিস্ট্রেশন সম্পূর্ণ হয়েছে");

        //for setting the icon
        //alertDialogBuilder.setIcon(R.drawable.question);

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                /*Intent intent;
                intent=new Intent(String.valueOf(this .onClick(Login.class));
                startActivity(intent);
                //break;  */
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




