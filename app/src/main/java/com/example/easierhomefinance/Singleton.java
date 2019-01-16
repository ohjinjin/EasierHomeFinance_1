package com.example.easierhomefinance;

import android.util.Log;

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

    public static Singleton getInstance() {
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    private Singleton() {
        //System.out.println("Singleton Instance Created..");
        Log.e(this.getClass().getName(),"Singleton Instance Created.");
    }
}