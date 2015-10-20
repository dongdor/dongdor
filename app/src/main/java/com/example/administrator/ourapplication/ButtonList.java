package com.example.administrator.ourapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by 영수 on 2015-09-04.
 */
public class ButtonList extends ActionBarActivity {

    private static final String TAG = "ButtonList Activity";


    private ListView m_ListView;    // 버튼 리스트 띄우기 위한 뷰
    private ButtonAdapter m_ListAdapter;    // 버튼 리스트 가져올 어댑터



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         *  로그인 상태 확인
         */
        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        boolean is_login = prefs.getBoolean("is_login", true);

        Log.d(TAG, "로그인 상태 : "+ is_login);

        // 로그인이 되어있지 않다면 로그인 창으로 이동
        if (!is_login) {
            Intent intent = new Intent(ButtonList.this, Entrance.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        setContentView(R.layout.button_list);

        // 클릭키를 추가할 버튼정의
        ImageButton addBtn = (ImageButton) findViewById(R.id.add_button);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭키 추가버튼");
                Intent intent = new Intent(ButtonList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // ListView 생성 및 어댑터 연결
        m_ListAdapter = new ButtonAdapter();

        m_ListView = (ListView) findViewById(R.id.list_view);
        m_ListView.setAdapter(m_ListAdapter);
        m_ListView.setOnItemClickListener(onClickListItem);

        // ListView에 아이템 추가
        m_ListAdapter.add("First Button");
        m_ListAdapter.add("Second Button");
        m_ListAdapter.add("Third Button");
        m_ListAdapter.add("Forth Button");
        m_ListAdapter.add("Fifth Button");
        m_ListAdapter.add("Sixth Button");
        m_ListAdapter.add("Seventh Button");
        m_ListAdapter.add("afsdfa Button");
        m_ListAdapter.add("asdfsdafe Button");
        m_ListAdapter.add("retert Button");
        m_ListAdapter.add("Fqer Button");
        m_ListAdapter.add("agsga Button");
        m_ListAdapter.add("qrqerqetsa Button");


    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getApplicationContext(), m_ListAdapter.getItem(position) + "\n 여기서 버튼 상세화면으로 넘어갑니다", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // 로그아웃 프로세스
        if (id == R.id.action_settings) {
            // 로그인 데이터 삭제
            SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.remove("id").remove("password");
            editor.putBoolean("is_login", false);
            editor.commit();

            // 로그인 창으로 이동
            Intent intent = new Intent(ButtonList.this, Entrance.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
