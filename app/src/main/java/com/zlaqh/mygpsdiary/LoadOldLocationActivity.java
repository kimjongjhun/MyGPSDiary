package com.zlaqh.mygpsdiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoadOldLocationActivity extends AppCompatActivity {

    //firebase user information
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    //trying something out
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userID = database.getReference("users");
    private String TAG;

    //declaring the xml things
    ListView oldLocationList;

    //declaring an arraylist to hold location objects
    public final ArrayList<Location> locationList = new ArrayList<>();;

    //trying something out
    public ArrayList<String> locationNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_old_location);

        oldLocationList = (ListView) findViewById(R.id.listview_oldLocationList);

        //trying something out
        locationNames = new ArrayList<>();

    }

    public void loadOldLocation(View view) {
        userID.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get children at this level
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    Location location = child.getValue(Location.class);
                    locationList.add(location);
                    locationNames.add(location.name);
                }
                //what i had
//                Location value = dataSnapshot.getValue(Location.class);
//                locationList.add(value);
//                locationNames.add(value.name);
//                Log.d(TAG, "value is: " + value.name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "failed to read value.", databaseError.toException());
            }
        });

        //putting the arraylist in the listview
        ArrayAdapter<Location> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationList);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationNames);

        oldLocationList.setAdapter(arrayAdapter1);
    }

    public void backToMap(View view) {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        finish();
    }
}
