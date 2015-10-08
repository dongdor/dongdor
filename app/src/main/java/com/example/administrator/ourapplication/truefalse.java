package com.example.administrator.ourapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class truefalse extends Activity {

    private Spinner cnt_excute;
    private Spinner cnt_end;
    private ArrayAdapter<CharSequence> counter_excute_adapter;
    private ArrayAdapter<CharSequence> counter_end_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truefalse);

        /*****
         * 체커 실행 시간
         *****/
        cnt_excute = (Spinner) this.findViewById(R.id.cnt_excute); // 스피너 연결
        counter_excute_adapter = ArrayAdapter.createFromResource(this, R.array.counter_excute_type, android.R.layout.simple_spinner_dropdown_item);
        counter_excute_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cnt_excute.setAdapter(counter_excute_adapter); // 스피너에 어댑터 연결
        cnt_excute.setOnItemSelectedListener(   // 스피너 아이템 선택시 동작
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        /*****
         * 체커 종료 설정
         *****/
        cnt_end = (Spinner) this.findViewById(R.id.cnt_end); // 스피너 연결
        counter_end_adapter = ArrayAdapter.createFromResource(this, R.array.checker_end_type, android.R.layout.simple_spinner_dropdown_item);
        counter_end_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cnt_end.setAdapter(counter_end_adapter); // 스피너에 어댑터 연결
        cnt_end.setOnItemSelectedListener(   // 스피너 아이템 선택시 동작
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

}
