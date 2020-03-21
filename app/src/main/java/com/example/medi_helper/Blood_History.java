package com.example.medi_helper;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Blood_History extends AppCompatActivity {

    DBconnection dBconnection;
    TextView bloodGrp,Uname,Uage;
    Button checkeButton,donetiontDate;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;
    int colorA,normal,Months;
    String currentDate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__history);
        dBconnection = new DBconnection(this);

        //action bar name and back button(also change to ("AndoidManifest.xml")
        getSupportActionBar().setTitle("রক্তে তথ্য");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        bloodGrp=findViewById(R.id.textViewBloodGroup);
        donetiontDate=findViewById(R.id.buttonLastDateOfDonation);
        checkeButton=findViewById(R.id.textViewBloodStatus);
        Uname=findViewById(R.id.textViewName);
        Uage=findViewById(R.id.textViewAge);


        //current date view
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
         SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
         currentDate= simpleDateFormat.format(Calendar.getInstance().getTime());

         donetiontDate.setText("22/12/2018");//current ar palce a datavase theke asebe





        donationdateUpdate();
         viewdata(); //for view data
        CheckeBloodDonationAvailability();







    }

    public boolean onOptionItemSelected(MenuItem item){

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
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
            /*Bp.setText(cursor.getString(12));
            Weight.setText(cursor.getString(7));
            Hight.setText(cursor.getString(8));
            Bmi.setText(cursor.getString(10));
            */
            bloodGrp.setText(cursor.getString(5));

        } else {

            Toast.makeText(this, "No data is found", Toast.LENGTH_LONG);

        }
    }


public void CheckeBloodDonationAvailability(){

    String dDate=donetiontDate.getText().toString();
    String cDate=currentDate.toString();
    SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");

    try {
        Date date1 =simpleDateFormat1.parse(dDate);
        Date date2 = simpleDateFormat1.parse(cDate);

        long donationD=date1.getTime();
        long currentD=date2.getTime();

        if (donationD<=currentD){

            org.joda.time.Period period =new Period(donationD,currentD, PeriodType.yearMonthDay());
            Months=period.getMonths();
        }



    } catch (ParseException e) {
        e.printStackTrace();
    }


    int c3= Color.rgb(231, 76, 60 );
    int  c4=Color.rgb(19, 141, 117 );

    if(Months>=3){

        checkeButton.setBackgroundColor(c4);
        checkeButton.setText("রক্ত দিতে পারবে");
        checkeButton.setTextColor(Color.WHITE);

    }
    else{

        checkeButton.setBackgroundColor(c3);
        checkeButton.setText("রক্ত দিতে পারবে না ");
        checkeButton.setTextColor(Color.WHITE);
    }

}

public void donationdateUpdate(){
    Calendar calendar=Calendar.getInstance();
    final int year=calendar.get(Calendar.YEAR);
    final int month=calendar.get(Calendar.MONTH);
    final int day=calendar.get(Calendar.DAY_OF_MONTH);
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
    donetiontDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    Blood_History.this
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
            donetiontDate.setText(date);
            CheckeBloodDonationAvailability();
        }
    };


}

}
