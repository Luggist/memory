package com.example.android.roomwordssample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button500;
    private Button button600;
    private Button button700;
    private SharedPreferences sharedPreferencesMain;
    boolean check;

    LinearLayout linearLayoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button500 = (Button)findViewById(R.id.button500);
        button500.setOnClickListener(this);

        button600 = (Button)findViewById(R.id.button600);
        button600.setOnClickListener(this);

        button700 = (Button)findViewById(R.id.button700);
        button700.setOnClickListener(this);

        linearLayoutMain = (LinearLayout)findViewById(R.id.linearMain);

        sharedPreferencesMain = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


    }
    public void onResume(){
        super.onResume();

        check = sharedPreferencesMain.getBoolean("DARKMODE",false);

        if(!check){

            linearLayoutMain.setBackgroundResource(R.drawable.trinangle_white);

        }else{

            linearLayoutMain.setBackgroundResource(R.drawable.triangle_black);
        }
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == button500.getId()){
           Intent intent = new Intent(this,MemorieActivity.class);
            startActivity(intent);
        }

        if(v.getId() == button700.getId()){
           Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        if(v.getId() == button600.getId()){
            Intent intent = new Intent(this,ResultView.class);
            startActivity(intent);
        }

    }
}
