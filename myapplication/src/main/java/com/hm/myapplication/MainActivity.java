package com.hm.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.hm.samsungspinner.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomSpinner spinner;
    private boolean isDark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        List<String> datas = new ArrayList<>();
        datas.add("Hanoi");
        datas.add("Haiphong");
        datas.add("Danang");
        datas.add("HCM");
        spinner.setData(datas);
    }

    public void changeTheme(View view) {
        isDark = !isDark;
        spinner.setDarkTheme(isDark);
    }
}
