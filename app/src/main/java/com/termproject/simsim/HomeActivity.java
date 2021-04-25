package com.termproject.simsim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.termproject.simsim.adapter.MatchAdapter;
import com.termproject.simsim.dataclass.G;
import com.termproject.simsim.dataclass.LogData;
import com.termproject.simsim.dataclass.MatchData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LocationManager lm;
    Location location;
    double userLongitude,userLatitude;

    private FirebaseUser user;
    private DatabaseReference reference, logReference;
    private String userID;

    private Button tagBtn, simsimBtn, nosimsimBtn;
    private EditText tagEditText;
    private ImageView chatIcon,settingIcon;

    ArrayList<MatchData> matchDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //location
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d("Permission", "permission requested!");
            return;
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // xml views
        tagBtn = (Button) findViewById(R.id.tagBtn);
        tagEditText = (EditText) findViewById(R.id.tagEditText);
        simsimBtn = (Button) findViewById(R.id.simsimBtn);
        nosimsimBtn = (Button) findViewById(R.id.noSimsimBtn);
        chatIcon = (ImageView) findViewById(R.id.chatIcon);
        settingIcon = (ImageView) findViewById(R.id.settingIcon);

        //listener
        tagBtn.setOnClickListener(this);
        simsimBtn.setOnClickListener(this);
        nosimsimBtn.setOnClickListener(this);
        chatIcon.setOnClickListener(this);

        //firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            logReference = FirebaseDatabase.getInstance().getReference("Logs");
            // MatchListView
            this.getMatchData();

            //userName은 로그인한 계정의 이름으로로
            reference.child(user.getUid()).child("nickname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        G.nickName=String.valueOf(task.getResult().getValue());
                    }
                }
            });
        }


    }

    private void getMatchData() {


        matchDataList = new ArrayList<MatchData>();
        List<Integer> distanceList = new ArrayList<>();
        //get Matchdata , can change to addListenerForSingleValueEvent
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    try {
                        if(item.child("match_data").child("status").getValue(String.class).equals("simsim")) {
                            String name = item.child("match_data").child("userName").getValue(String.class);
                            String tag = item.child("match_data").child("tag").getValue(String.class);
                            double latitude = item.child("match_data").child("latitude").getValue(Double.class);
                            double longitude = item.child("match_data").child("longitude").getValue(Double.class);
                            String time =  item.child("match_data").child("time").getValue(String.class);


                            matchDataList.add(new MatchData(name, tag, latitude, longitude,time));
                            Location friendLocation = new Location("friend");
                            friendLocation.setLatitude(latitude);
                            friendLocation.setLongitude(longitude);

                            distanceList.add((int) location.distanceTo(friendLocation));
                        }
                    } catch (Exception e) {
                        Log.e("Error  in HomeActivity", e.toString());
                    }
                }
                ListView matchListView = (ListView) findViewById(R.id.matchListView);
                final MatchAdapter matchAdapter = new MatchAdapter(getApplicationContext(), matchDataList, distanceList);

                matchListView.setAdapter(matchAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", String.valueOf(error.getCode()));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tagBtn:
                tagUserInterest();
                getMatchData();
                break;
            case R.id.simsimBtn:
                updateUserStatus("simsim");
                getMatchData();
                break;
            case R.id.noSimsimBtn:
                updateUserStatus("nosimsim");
                getMatchData();
                break;
            case R.id.chatIcon:
                goChattingRoom();
                break;
        }
    }

    private void goChattingRoom(){
        Intent intent = new Intent(this, ChatEnterActivity.class);
        startActivity(intent);
    }
    private void updateUserStatus(String status){
        reference.child(user.getUid()).child("match_data").child("status").setValue(status);
    }

    private void tagUserInterest() {
        String tag = tagEditText.getText().toString();

        String time = getFormatDate();
        String dayOfweek = getDayOfWeek();

        userLatitude = location.getLatitude();
        userLongitude = location.getLongitude();
        location.setLatitude(userLatitude);
        location.setLongitude(userLongitude);



        MatchData matchdata = new MatchData(G.nickName,tag,userLatitude,userLongitude,time,"simsim");
        reference.child(user.getUid()).child("match_data").setValue(matchdata);

        LogData logdata = new LogData(user.getUid(),time,dayOfweek,tag,userLatitude,userLongitude);
        logReference.push().setValue(logdata);


        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    private String getFormatDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();

        Date date = calendar.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//        Log.d("getDate()",String.valueOf(date.getTime()));
        String dateResult = sdf.format(date);
        return dateResult;
    }

    private String getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.KOREA).format(date.getTime());
        //Log.d("getDayOfWeek", dayOfWeek);
        return dayOfWeek;
    }



}