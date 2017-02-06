package com.zlaqh.mygpsdiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveNewLocationActivity extends AppCompatActivity {

    //declaring the xml things
    EditText locationName, locationNote;

    //date and time things
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;

    // trying something out
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userID = database.getReference("users");

    //declaring the name and note in strings
    String saveName, saveNote;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_new_location);

        //instantiating the xml things
        locationName = (EditText) findViewById(R.id.editText_saveName);
        locationNote = (EditText) findViewById(R.id.editText_saveNote);

        //instantiating the date and time
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = simpleDateFormat.format(calendar.getTime());

    }

    public void saveLocation(View view) {
        //instantiating the name and note in strings
        saveName = locationName.getText().toString();
        saveNote = locationNote.getText().toString();

        uid = user.getUid();

        Location location = new Location(saveName, saveNote, date);

        DatabaseReference newRef = userID.child(uid).push();
        newRef.setValue(location);

    }

}
