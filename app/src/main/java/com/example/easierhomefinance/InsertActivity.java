package com.example.easierhomefinance;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private long bln = 0;
    private LinearLayout container;

    private DBHelper mydb;
    EditText date;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date dateObject;
    RadioButton income;
    RadioButton expense;
    RadioButton save;
    TextView amount;
    int id = 0;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        singleton = Singleton.getInstance(this);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.classification,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        mydb = new DBHelper(this);
        date = (EditText)findViewById(R.id.date);
        amount = (TextView)findViewById(R.id.amount);
        income = (RadioButton) findViewById(R.id.income);
        expense = (RadioButton) findViewById(R.id.expense);
        save = (RadioButton) findViewById(R.id.save);

        /*TextView view1 = new TextView(this);
        view1.setText("나는 텍스트뷰");
        view1.setTextSize(15);
        view1.setTextColor(Color.BLACK);

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view1.setLayoutParams(lp);

        //부모 뷰에 추가
        container.addView(view1);*/

    }
    @Override
    public void onClick(final View v) {
        new AlertDialog.Builder(this)
                .setTitle("저장 확인 창")
                .setMessage("정말로 저장하시겠습니까?")
                .setIcon(android.R.drawable.ic_menu_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 확인시 처리 로직
                        insert(v);///////////////////////////////////////////////////////////////////////////////////////////////
                        //Toast.makeText(InsertActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 취소시 처리 로직
                        Toast.makeText(InsertActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        Toast.makeText(parent.getContext(),"check "+parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0){
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void insert(View view){
        /*
        Bundle extras = getIntent().getExtras();
        String dobvar=date.getText().toString();
        dateObject = sdf.parse(dobvar);
        DateFormat dateFormatISO8601 = new SimpleDateFormat("dd/MM/yyyy");
        String strDob = dateFormatISO8601.format(dateObject);
        if (extras!=null){
            int Value = extras.getInt("id");
            if (Value>0){   //해당일자도 체크해줘야함 수정수정수정수정수정
                if (mydb.updateHistory(id, date.getText().toString(),income.ge))
            }
        }
        else{*/
        int tmp1 = income.isChecked()?1:0;
        int tmp2 = expense.isChecked()?1:0;
        int tmp3 = save.isChecked()?1:0;

        //singleton.insertData(date.getText().toString(),tmp1,tmp2,tmp3,Integer.parseInt(amount.getText().toString()));

        //Log.e(this.getClass().getName(),"testttttttttttttttttttttttttttttttttttttt44"+s1+s2);//+(date.getText().toString())+" bools : "+tmp1+" "+tmp2+" "+ tmp3+" "+Integer.parseInt(amount.getText().toString()));
        if (singleton.insertData(date.getText().toString(),tmp1,tmp2,tmp3,Integer.parseInt(amount.getText().toString()))){
            Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"저장되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    /*public void delete(View view){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int Value = extras.getInt("id");
            if (Value>0){
                mydb.deleteHistory(id);
                Toast.makeText(getApplicationContext(),"삭제되었음",Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(),"삭제되지 않았음",Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
