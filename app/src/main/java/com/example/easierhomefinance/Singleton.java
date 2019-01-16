package com.example.easierhomefinance;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*public class Singleton {
    private Singleton() {   // 싱글톤 인스턴스가 생성될때 JVM의 클래스 초기화 과정에서 보장되는 원자적 특성을 이용하여 싱글턴의 초기화 문제에 대한 책임을 JVM에 떠넘긴다. holder안에 선언된 instance가 static이기 때문에 클래스 로딩시점에 한번만 호출될 것이며 final을 사용해 다시 값이 할당되지않도록 만든 방법
    }

    private static class LazyHolder {
        public static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}*/
public class Singleton {
    private static Singleton instance;// = new Singleton();
    private static Map<String,List<Data>> datas;

    // constructor_2
    public static Singleton getInstance(Context context) {
        if (instance == null){
            datas = new HashMap();
            instance = new Singleton();
            DBHelper mydb = new DBHelper(context);

            int income = 0;
            int expense = 0;
            int save = 0;
            String date = "";

            // 싱글톤 인스턴스가 생성될 때 일별로 DB상의 모든 데이터를 메모리로 담는다_via depth가 2인 해시맵
            for (int i=0; i < mydb.numberOfRows(); i++) {
                Cursor rs = mydb.getDatabyid(i+1);    // 모든 데이터 row에 대하여 체크
                //rs.moveToFirst();
                rs.moveToPrevious();
                date = rs.getString(rs.getColumnIndex(DBHelper.HF_COLUMN_DATE));

                if (!(datas.containsKey(date))) {   // 데이터 해시맵 상에 중복된 키(date)가 없다면
                    ArrayList<Data> dailyDatas = new ArrayList();
                    ArrayList<Cursor> cursors = mydb.getDatabydate(date);

                    for (int j = 0; j < cursors.size(); j++) {
                        if (cursors.get(j).getInt(rs.getColumnIndex(DBHelper.HF_COLUMN_INCOME)) == 1) {
                            income = cursors.get(j).getInt(rs.getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
                        } else if (cursors.get(j).getInt(cursors.get(j).getColumnIndex(DBHelper.HF_COLUMN_EXPENSE)) == 1) {
                            expense = cursors.get(j).getInt(cursors.get(j).getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
                        } else {
                            save = cursors.get(j).getInt(cursors.get(j).getColumnIndex(DBHelper.HF_COLUMN_AMOUNT));
                        }
                        if (!cursors.get(i).isClosed()) {
                            cursors.get(i).close();
                        }
                        dailyDatas.add(new Data(income, expense, save, date));  // 데이터 객체를 생성하여 list에 append
                    }
                    datas.put(date,dailyDatas); // key: date, value: 데이터 객체들의 list 해시맵 구성
                }

                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }
        return instance;
    }

    // constructor_1
    private Singleton() {
        //System.out.println("Singleton Instance Created..");
        Log.e(this.getClass().getName(),"Singleton Instance Created.");
    }

    // getting total balance
    public int getTotalBalance(){
        int totalBalance = 0;
        for (Map.Entry<String,List<Data>> each:datas.entrySet()){
            for (int j = 0; j<each.getValue().size();j++){
                totalBalance += each.getValue().get(j).total;
            }
        }
        return totalBalance;
    }

    // getting total income
    public int getTotalIncome(){
        int totalIncome = 0;
        for (Map.Entry<String,List<Data>> each:datas.entrySet()){
            for (int j = 0; j<each.getValue().size();j++){
                totalIncome += each.getValue().get(j).income;
            }
        }
        return totalIncome;
    }

    // getting total expense
    public int getTotalExpense(){
        int totalExpense = 0;
        for (Map.Entry<String,List<Data>> each:datas.entrySet()){
            for (int j = 0; j<each.getValue().size();j++){
                totalExpense += each.getValue().get(j).expense;
            }
        }
        return totalExpense;
    }

    // getting total save
    public int getTotalSave(){
        int totalSave = 0;
        for (Map.Entry<String,List<Data>> each:datas.entrySet()){
            for (int j = 0; j<each.getValue().size();j++){
                totalSave += each.getValue().get(j).save;
            }
        }
        return totalSave;
    }

    // getting monthly balance--------------------->이전달까지의 누적금액을 알고있어야함. 이전달.....을 어떻게알고 거기서 계산을 멈추지?ㅠㅠ 수정예정
    public int getMonthlyBalance(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth());
        Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalBalance = 0;

        for (Map.Entry<String,List<Data>> each:datas.entrySet()){   // 해시맵 내의 모든 데이터 중
            if (each.getKey().contains(date)) { // 파라미터 날짜와 같은 달의 데이터들은
                for (int j = 0; j < each.getValue().size(); j++) {  // 그 날의 일별 내역중 total을 모두 가져와 누적덧셈해준다
                    totalBalance += each.getValue().get(j).total;
                }
            }
        }
        return totalBalance;
    }

    // getting monthly income
    public int getMonthlyIncome(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalIncome = 0;

        for (Map.Entry<String,List<Data>> each:datas.entrySet()){   // 해시맵 내의 모든 데이터 중
            if (each.getKey().contains(date)) { // 파라미터 날짜와 같은 달의 데이터들은
                for (int j = 0; j < each.getValue().size(); j++) {  // 그 날의 일별 내역중 income을 모두 가져와 누적덧셈해준다
                    totalIncome += each.getValue().get(j).income;
                }
            }
        }
        return totalIncome;
    }

    // getting monthly expense
    public int getMonthlyExpense(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalExpense = 0;

        for (Map.Entry<String,List<Data>> each:datas.entrySet()){   // 해시맵 내의 모든 데이터 중
            if (each.getKey().contains(date)) { // 파라미터 날짜와 같은 달의 데이터들은
                for (int j = 0; j < each.getValue().size(); j++) {  // 그 날의 일별 내역중 expense를 모두 가져와 누적덧셈해준다,음수면 누적될수록 더 큰 음수가 될것이다
                    totalExpense += each.getValue().get(j).expense;
                }
            }
        }
        return totalExpense;
    }

    // getting monthly save
    public int getMonthlySave(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalSave = 0;

        for (Map.Entry<String,List<Data>> each:datas.entrySet()){   // 해시맵 내의 모든 데이터 중
            if (each.getKey().contains(date)) { // 파라미터 날짜와 같은 달의 데이터들은
                for (int j = 0; j < each.getValue().size(); j++) {  // 그 날의 일별 내역중 save를 모두 가져와 누적덧셈해준다
                    totalSave += each.getValue().get(j).save;
                }
            }
        }
        return totalSave;
    }

    // getting daily balance--------------------->이전일까지의 누적금액을 알고있어야함. 이전일.....을 어떻게알고 거기서 계산을 멈추지?ㅠㅠ 수정예정,str to int해서 대소비교?
    public int getDailyBalance(Date curDate){
        int totalBalance = 0;
        for (Map.Entry<String,List<Data>> each:datas.entrySet()){
            for (int j = 0; j<each.getValue().size();j++){
                totalBalance += each.getValue().get(j).total;
            }
        }
        return totalBalance;
    }

    // getting daily income
    public int getDailyIncome(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth())+String.format("%02d",curDate.getDay());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalIncome = 0;
        for (int j = 0; j < datas.get(date).size(); j++) {  // 그 날의 일별 내역중 income을 모두 가져와 누적덧셈해준다
            totalIncome += datas.get(date).get(j).income;
        }

        return totalIncome;
    }

    // getting daily expense
    public int getDailyExpense(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth())+String.format("%02d",curDate.getDay());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalExpense = 0;
        for (int j = 0; j < datas.get(date).size(); j++) {  // 그 날의 일별 내역중 income을 모두 가져와 누적덧셈해준다
            totalExpense += datas.get(date).get(j).expense;
        }

        return totalExpense;
    }

    // getting daily save
    public int getDailySave(Date curDate){
        String date = String.format("%04d",curDate.getYear())+String.format("%02d",curDate.getMonth())+String.format("%02d",curDate.getDay());
        //Log.e(this.getClass().getName(),"checkcheckcheckcheckcheckcheck"+date);
        int totalSave = 0;
        for (int j = 0; j < datas.get(date).size(); j++) {  // 그 날의 일별 내역중 income을 모두 가져와 누적덧셈해준다
            totalSave += datas.get(date).get(j).save;
        }

        return totalSave;
    }
}