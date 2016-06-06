package com.example.teo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.teo.myapplication.Models.ScoreModel;

import java.util.List;

/**
 * Created by Teo on 2016-06-05.
 */
public class HighscoreAdapter extends ArrayAdapter {
    private List<ScoreModel> HighScList;
    private Context context;
    private int resource;

    class ViewHolder {
        protected TextView TextNumber;
        protected TextView TextScore;
        protected TextView TextScreenName;

        ViewHolder() {
        }
    }

    public HighscoreAdapter(Context context, int resource, List<ScoreModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.HighScList = objects;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tablerow, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.TextNumber = (TextView) view.findViewById(R.id.TextNumber);
        viewHolder.TextScreenName = (TextView) view.findViewById(R.id.TextScreenName);
        viewHolder.TextScore = (TextView) view.findViewById(R.id.TextScore);
        view.setTag(viewHolder);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.TextNumber.setText(String.valueOf(getItemId(position) + 1));
        holder.TextScreenName.setText((HighScList.get(position)).getScrName());
        holder.TextScore.setText(String.valueOf((HighScList.get(position)).getScore()));
        return view;
    }

    public long getItemId(int position) {
        return super.getItemId(position);
    }
}