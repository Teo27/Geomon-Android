package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teo.myapplication.Models.GeomonModel;

import java.util.List;

/**
 * Created by Ieva on 11/05/2016.
 */
public class CollectionAdapter extends ArrayAdapter {
    private Context context;
    private List<GeomonModel> geoList;
    private int resource;

    class ViewHolder {
        protected TextView GeoName;

        ViewHolder() {
        }
    }

    public CollectionAdapter(Context context, int resource, List<GeomonModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.geoList = objects;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.collection_row, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.GeoName = (TextView) view.findViewById(R.id.textViewCollection);
        ImageView image = (ImageView) view.findViewById(R.id.imageViewCollection);
        view.setTag(viewHolder);
        ((ViewHolder) view.getTag()).GeoName.setText((geoList.get(position)).getName());
        image.setImageResource(this.context.getResources().getIdentifier((geoList.get(position)).getName().toLowerCase(), "drawable", this.context.getPackageName()));
        return view;
    }
}