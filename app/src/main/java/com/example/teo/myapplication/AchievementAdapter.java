package com.example.teo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teo.myapplication.Models.AchievementModel;

import java.util.List;

/**
 * Created by Teo on 2016-06-05.
 */
public class AchievementAdapter extends ArrayAdapter {

    private List<AchievementModel> AchList;
    private Context context;
    private int resource;

    class ViewHolder {
        protected TextView AchDesc;
        protected TextView AchName;

        ViewHolder() {
        }
    }

    public AchievementAdapter(Context context, int resource, List<AchievementModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.AchList = objects;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.achievement_row, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.AchDesc = (TextView) view.findViewById(R.id.AchDesc);
        viewHolder.AchName = (TextView) view.findViewById(R.id.AchName);
        ImageView image = (ImageView) view.findViewById(R.id.AchImage);
        view.setTag(viewHolder);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.AchName.setText((AchList.get(position)).getName());
        holder.AchDesc.setText((AchList.get(position)).getDescription());
        image.setImageResource(R.drawable.trophy);
        return view;
    }
}