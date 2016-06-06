package com.example.teo.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Ieva on 12/05/2016.
 */
public class Help extends Activity{

    HelpListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_view);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableHelp);

        // preparing list data
        prepareListData();

        listAdapter = new HelpListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Help.this,MainActivity.class));
                finish();
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        this.listDataHeader.add("What is this?");
        this.listDataHeader.add("Where do I begin?");
        this.listDataHeader.add("How do I add a Geomon to my collection?");
        this.listDataHeader.add("How do I check which Geomon I have?");
        this.listDataHeader.add("How do I level up a Geomon?");
        this.listDataHeader.add("How do I earn points?");
        this.listDataHeader.add("Can I release a Geomon?");
        this.listDataHeader.add("Can I get two of the same Geomon?");
        this.listDataHeader.add("Help! I can't swim and there's a Geomon in the river!");
        this.listDataHeader.add("How do I reset the game?");
        this.listDataHeader.add("No marker appear. Why?");
        this.listDataHeader.add("How do I earn achievements?");

        // Adding child data
        List<String> answer1 = new ArrayList();
        answer1.add("This is a game where you can collect monsters by exploring the world.");
        List<String> answer2 = new ArrayList();
        answer2.add("You can start collecting by clicking the Map button.");
        List<String> answer3 = new ArrayList();
        answer3.add("When you see a marker on the map, you need to walk to it and click on it when you are right next to it.");
        List<String> answer4 = new ArrayList();
        answer4.add("You can view your Geomon by clicking Collection.");
        List<String> answer5 = new ArrayList();
        answer5.add("Not yet implemented.");
        List<String> answer6 = new ArrayList();
        answer6.add("Not yet implemented.");
        List<String> answer7 = new ArrayList();
        answer7.add("You can't.");
        List<String> answer8 = new ArrayList();
        answer8.add("No.");
        List<String> answer9 = new ArrayList();
        answer9.add("You can reset the map by going back and clicking the map again.");
        List<String> answer10 = new ArrayList();
        answer10.add("You can just register as a new user, your old account can still be accessed by you, however.");
        List<String> answer11 = new ArrayList();
        answer11.add("Check your gps and internet connections.");
        List<String> answer12 = new ArrayList();
        answer12.add("Not yet implemented.");

        this.listDataChild.put(this.listDataHeader.get(0), answer1);
        this.listDataChild.put(this.listDataHeader.get(1), answer2);
        this.listDataChild.put(this.listDataHeader.get(2), answer3);
        this.listDataChild.put(this.listDataHeader.get(3), answer4);
        this.listDataChild.put(this.listDataHeader.get(4), answer5);
        this.listDataChild.put(this.listDataHeader.get(5), answer6);
        this.listDataChild.put(this.listDataHeader.get(6), answer7);
        this.listDataChild.put(this.listDataHeader.get(7), answer8);
        this.listDataChild.put(this.listDataHeader.get(8), answer9);
        this.listDataChild.put(this.listDataHeader.get(9), answer10);
        this.listDataChild.put(this.listDataHeader.get(10), answer11);
        this.listDataChild.put(this.listDataHeader.get(11), answer12);
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}
