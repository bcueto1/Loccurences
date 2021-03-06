package com.uima.event_app;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by sidneyjackson on 4/21/17.
 */

public class TopEventPageFragment extends Fragment {

    protected View rootView;
    private String eventID;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    TextView title;
    TextView date;
    TextView time;
    ImageView headerImage;

    public TopEventPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference();
        this.eventID = getArguments().getString("eventID");
    }

    @Override
    public void onStart() {
        super.onStart();
        this.populateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.populateData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_top_event_page, container, false);
        //this.title = (TextView) rootView.findViewById(R.id.top_event_title);
        this.date = (TextView) rootView.findViewById(R.id.top_event_date);
        this.time = (TextView) rootView.findViewById(R.id.top_event_time);
        this.headerImage = (ImageView) rootView.findViewById(R.id.top_event_board_header);

        return rootView;
    }

    private void populateData() {
        myRef.child("events").child(eventID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event thisEvent = dataSnapshot.getValue(Event.class);
                //title.setText(thisEvent.getName());
                date.setText("Date: " + thisEvent.grabDateString());
                time.setText("Time: " + thisEvent.grabStartTimeString() + " - " + thisEvent.grabEndTimeString());
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sRef = storage.getReference();
                sRef.child(thisEvent.getImgId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).fit().centerCrop().into(headerImage);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
