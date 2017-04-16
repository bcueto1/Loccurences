package com.uima.event_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyLiveEventsFragment extends Fragment {
    private static List<Event> eventItems;
    private static EventAdapter adapter;

    private ListView eventListView;
    private View rootView;

    public MyLiveEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_live_events, container, false);
        eventListView = (ListView) rootView.findViewById(R.id.event_list_view);
        updateArray();
        return rootView;
    }

    public void updateArray() {
        // get dummy or actual list of events
        eventItems = new ArrayList<>();
        List<String> dummyTypes = new ArrayList<>();
        dummyTypes.add("Local Culture");
        dummyTypes.add("Social Activism");
        List<String> dummyTags = new ArrayList<>();
        dummyTags.add("free");
        dummyTags.add("parking");
        Event temp = new Event("An Event!", "An Org", "JHU", "oh look details", false, "", dummyTypes, dummyTags);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);
        eventItems.add(temp);

        adapter = new EventAdapter(getActivity(), R.layout.event_item, eventItems);
        eventListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
