package com.example.pikachu_game;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> titles;
    private final ArrayList<String> scores;
    private final ArrayList<String> numbers;

    public MyListAdapter(Activity context, ArrayList<String> titles, ArrayList<String> scores, ArrayList<String> numbers) {
        super(context, R.layout.my_list, titles);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.titles=titles;
        this.scores=scores;
        this.numbers=numbers;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.my_list_LBL_title);
        TextView numberText = (TextView) rowView.findViewById(R.id.my_list_LBL_number);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.my_list_LBL_subtitle);

        titleText.setText(titles.get(position));
        numberText.setText(numbers.get(position));
        subtitleText.setText(scores.get(position));

        return rowView;

    };
}