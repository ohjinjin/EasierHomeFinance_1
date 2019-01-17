package com.example.easierhomefinance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    DBHelper mydb;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("ACTIVITY_LC","onCreate 호출됨");

        singleton = Singleton.getInstance(this);

        mydb = new DBHelper(this);

        //임의로 데이터 삽입
        mydb.deleteHistory(1);
        mydb.deleteHistory(2);
        mydb.deleteHistory(3);
        mydb.insertHistory("20190111", 1, 0, 0, 5000);


        Button button = (Button)findViewById(R.id.newActivity);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //Bundle bundle = new Bundle();
                //bundle.putInt("id",0);
                Intent intent = new Intent(getApplicationContext(),SubActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"내역조회 화면으로 이동합니다 :-)",Toast.LENGTH_SHORT).show();
            }
        });


        /*
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final DateFormat dateFormat_year = new SimpleDateFormat("yyyy");
        final DateFormat dateFormat_month = new SimpleDateFormat("MM");
        final DateFormat dateFormat_day = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt1"+dateFormat.format(cal.getTime())+" "+dateFormat_year.format(cal.getTime())+" "+dateFormat_month.format(cal.getTime())+" "+dateFormat_day.format(cal.getTime()));*/
    }
}
