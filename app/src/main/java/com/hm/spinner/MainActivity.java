package com.hm.spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.hm.samsungspinner.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomSpinner spinner = findViewById(R.id.spinner);
        List<String> datas = new ArrayList<>();
        datas.add("Hanoi");
        datas.add("Haiphong");
        datas.add("Danang");
        datas.add("HCM");
        spinner.setData(datas);
    }
}
