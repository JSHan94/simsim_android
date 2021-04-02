package com.example.simsim;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simsim.adapter.MatchAdapter;
import com.example.simsim.dataclass.MatchData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LocationManager lm;
    Location location;
    double userLongitude,userLatitude;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button tagBtn;
    private EditText tagEditText;

    ArrayList<MatchData> matchDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //location
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d("Permission", "permission requested!");
            return;
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // xml views
        tagBtn = (Button) findViewById(R.id.tagBtn);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        //listener
        tagBtn.setOnClickListener(this);

        //firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users");

            // MatchListView
            this.getMatchData();
        }






    }

    private void getMatchData() {
        matchDataList = new ArrayList<MatchData>();
        List<Integer> distanceList = new ArrayList<>();
        //get Matchdata , can change to addListenerForSingleValueEvent
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    try {
                        //getKey != uid
                        String name = item.child("match_data").child("userName").getValue(String.class);
                        String tag = item.child("match_data").child("tag").getValue(String.class);
                        double latitude = item.child("match_data").child("latitude").getValue(Double.class);
                        double longitude = item.child("match_data").child("longitude").getValue(Double.class);
                        matchDataList.add(new MatchData(name, tag, latitude, longitude));
                        Location friendLocation = new Location("friend");
                        friendLocation.setLatitude(latitude);
                        friendLocation.setLongitude(longitude);

                        distanceList.add((int) location.distanceTo(friendLocation));
                    } catch (Exception e) {
                        Log.e("Error", e.toString());
                    }
                }
                ListView matchListView = (ListView) findViewById(R.id.matchListView);
                final MatchAdapter matchAdapter = new MatchAdapter(getApplicationContext(), matchDataList,distanceList);

                matchListView.setAdapter(matchAdapter);
                matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        Toast.makeText(HomeActivity.this,
                                "Clicked!!",
                                Toast.LENGTH_LONG).show();
                    }
                });

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
        }
    }

    private void tagUserInterest() {
        String tag = tagEditText.getText().toString();

        userLatitude = location.getLatitude();
        userLongitude = location.getLongitude();
        location.setLatitude(userLatitude);
        location.setLongitude(userLongitude);

        MatchData matchdata = new MatchData("준수",tag,userLatitude,userLongitude);
        reference.child(user.getUid()).child("match_data").setValue(matchdata);
    }
}