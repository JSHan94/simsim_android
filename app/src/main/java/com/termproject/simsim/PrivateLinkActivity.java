package com.termproject.simsim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrivateLinkActivity extends AppCompatActivity implements View.OnClickListener {

    private Button enterBtn,leaveBtn;
    private TextView roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_link);

        enterBtn = (Button) findViewById(R.id.enterBtn);
        leaveBtn = (Button) findViewById(R.id.leaveBtn);

        enterBtn.setOnClickListener(this);
        leaveBtn.setOnClickListener(this);

        getSupportActionBar().setTitle("채팅 입장");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterBtn:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.leaveBtn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}