package com.zlaqh.mygpsdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadOldLocationActivity extends AppCompatActivity {

    //trying something out
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userID = database.getReference("users");
    private String TAG;

    //declaring the xml things
    ListView oldLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_old_location);

        oldLocationList = (ListView) findViewById(R.id.listview_oldLocationList);


    }

    public void loadOldLocation(View view) {
        userID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "failed to read value.", databaseError.toException());
            }
        });
    }

    public void backToMap(View view) {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        finish();
    }
}
