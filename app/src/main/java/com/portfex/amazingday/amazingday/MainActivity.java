package com.portfex.amazingday.amazingday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void test(){
        for (int i=0;i<10;i++){
            int a=5;
            int b=10;
            int c=0;
            c=a+b;

        }
    }

}
