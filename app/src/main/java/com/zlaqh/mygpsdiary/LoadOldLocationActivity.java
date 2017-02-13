package com.zlaqh.mygpsdiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    public final ArrayList<Location> locationList = new ArrayList<>();
    ;

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

                for (DataSnapshot child : children) {
                    Location location = child.getValue(Location.class);
                    if (!locationNames.contains(location.name)) {
                        locationList.add(location);
                        locationNames.add(location.name);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "failed to read value.", databaseError.toException());
            }
        });

        //putting the arraylist in the listview
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                locationNames);

        oldLocationList.setAdapter(arrayAdapter);
        oldLocationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(LoadOldLocationActivity.this).create();
                alertDialog.setTitle("Location Information");
                alertDialog.setMessage("Name: " + locationNames.get(position) +
                        "\nNote: " + locationList.get(position).note +
                        "\nDate and Time: " + locationList.get(position).date +
                        "\nCoordinates: " + locationList.get(position).lat + "," + locationList.get(position).lng);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                // to try and send coords to maps activity
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Show on Map",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(LoadOldLocationActivity.this, MapsActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putInt("method", 1);
                                bundle.putDouble("latitude", locationList.get(position).lat);
                                bundle.putDouble("longitude", locationList.get(position).lng);

                                i.putExtras(bundle);

                                startActivity(i);

                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void backToMap(View view) {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        finish();
    }
}
