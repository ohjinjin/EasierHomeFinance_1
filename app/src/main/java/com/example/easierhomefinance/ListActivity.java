package com.example.easierhomefinance;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private DBHelper mydb;
    private TextView date;
    private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mydb = new DBHelper(this);
        date = (TextView)findViewById(R.id.date);
        testText = (TextView)findViewById(R.id.testText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("date");
            date.setText((CharSequence) Value); // 해당일 display

            ArrayList<Cursor> cursors = mydb.getDatabydate(Value);
            //Log.e(this.getClass().getName(),cursors.size()+"dddddddddddddddddd");
            for (int i = 0; i<cursors.size();i++) {
                cursors.get(i).moveToPrevious();    ////////////////됐다! 왜 하나 전으로 가야하는거지 ㅠㅠ
                Log.e(this.getClass().getName(), cursors.get(i).getColumnCount()+"ssss"+cursors.get(i).getPosition()+"zzzzz"+i);

                String n = cursors.get(i).getString(cursors.get(i).getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
                //String n = rs.getString(rs.getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
                //Log.e(this.getClass().getName(),"ffff1");

                if (cursors.get(i).getInt(cursors.get(i).getColumnIndex(DBHelper.HF_COLUMN_INCOME)) == 1) {
                    //Log.e(this.getClass().getName(),"ffff2");
                    testText.setTextColor(Color.BLUE);
                    testText.setText("+" + (CharSequence) n);
                } else if (cursors.get(i).getInt(cursors.get(i).getColumnIndex(DBHelper.HF_COLUMN_EXPENSE)) == 1) {
                    //Log.e(this.getClass().getName(),"ffff3");
                    testText.setTextColor(Color.RED);
                    testText.setText("-" + (CharSequence) n);
                } else {
                    //Log.e(this.getClass().getName(),"ffff4");
                    testText.setTextColor(Color.GREEN);
                    testText.setText((CharSequence) n);
                }

                if (!cursors.get(i).isClosed()) {
                    //Log.e(this.getClass().getName(),"ffff5");
                    cursors.get(i).close();
                }
            }
            /*ArrayList <String> arrayList = mydb.getAllHistory();
            for (int i = 0; i<arrayList.size();i++) {
                Log.e(this.getClass().getName(), arrayList.get(i)+"dddd");
            }
            */
            /*
            String n = rs.getString(rs.getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
            Log.e(this.getClass().getName(),"checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            //String n = rs.getString(rs.getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));

            Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt"+n);

            if (rs.getInt(rs.getColumnIndex(DBHelper.HF_COLUMN_INCOME)) == 1) {
                Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt1");
                testText.setTextColor(Color.BLUE);
                testText.setText("+" + (CharSequence) n);
            } else if (rs.getInt(rs.getColumnIndex(DBHelper.HF_COLUMN_EXPENSE)) == 1) {
                Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt2");
                testText.setTextColor(Color.RED);
                testText.setText("-" + (CharSequence) n);
            } else {
                Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt3");
                testText.setTextColor(Color.GREEN);
                testText.setText((CharSequence) n);

            }

            if (!rs.isClosed()) {
                //Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt4");
                rs.close();
            }
*/

        }
    }
}
