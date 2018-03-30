package com.app.androidkt.mqtt;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        EditText edBroker = (EditText) findViewById(R.id.ed_broker);
        EditText edClientid = (EditText) findViewById(R.id.ed_clientid);
        EditText edPubtopic = (EditText) findViewById(R.id.ed_pubtopic);
        EditText edSubtopic = (EditText) findViewById(R.id.ed_subtopic);

        SharedPreferences setting =
                getSharedPreferences("skyeyes", MODE_PRIVATE);
        edBroker.setText(setting.getString("PREF_BROKER", ""));
        edClientid.setText(setting.getString("PREF_CLIENT", ""));
        edPubtopic.setText(setting.getString("PREF_PUB", ""));
        edSubtopic.setText(setting.getString("PREF_SUB", ""));
    }

    public void update(View view){
        EditText edBroker = (EditText) findViewById(R.id.ed_broker);
        EditText edClientid = (EditText) findViewById(R.id.ed_clientid);
        EditText edPubtopic = (EditText) findViewById(R.id.ed_pubtopic);
        EditText edSubtopic = (EditText) findViewById(R.id.ed_subtopic);

        String broker = edBroker.getText().toString();
        String clientid = edClientid.getText().toString();
        String pub = edPubtopic.getText().toString();
        String sub = edSubtopic.getText().toString();

        SharedPreferences setting =
                getSharedPreferences("skyeyes", MODE_PRIVATE);
        setting.edit()
                .putString("PREF_BROKER", broker)
                .putString("PREF_CLIENT", clientid)
                .putString("PREF_PUB", pub)
                .putString("PREF_SUB", sub)
                .apply();
        Toast.makeText(this, "Update Successfully", Toast.LENGTH_LONG).show();
        getIntent().putExtra("LOGIN_BROKER", broker);
        getIntent().putExtra("LOGIN_CLIENT", clientid);
        getIntent().putExtra("LOGIN_PUB", pub);
        getIntent().putExtra("LOGIN_SUB", sub);
        setResult(RESULT_OK, getIntent());
        finish();
    }

    public void cancel(View v){
//        Toast.makeText(this, "Cancal", Toast.LENGTH_LONG).show();
        SharedPreferences setting =
                getSharedPreferences("skyeyes", MODE_PRIVATE);
        getIntent().putExtra("LOGIN_BROKER", setting.getString("PREF_BROKER", ""));
        getIntent().putExtra("LOGIN_CLIENT", setting.getString("PREF_CLIENT", ""));
        getIntent().putExtra("LOGIN_PUB", setting.getString("PREF_PUB", ""));
        getIntent().putExtra("LOGIN_SUB", setting.getString("PREF_SUB", ""));
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
