package com.example.pikachu_game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;


public class Fragment_List extends Fragment {

    private final int SIZE = 10;
    private ListView fragment_list_LST_topTen;
    private TopTen topTen;
    private Gson gason;
    private ArrayList<String> titles;
    private ArrayList<String> numbers;
    private ArrayList<String> scores;
    private MyListAdapter adapter;
    private CallBack callBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {

        gason = new Gson();
        titles = new ArrayList<>();
        numbers = new ArrayList<>();
        scores = new ArrayList<>();
        fragment_list_LST_topTen = view.findViewById(R.id.fragment_list_LST_topTen);

    }

    private void initViews() {

        initAdapter();

        fragment_list_LST_topTen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(callBack != null)
                    callBack.updateMapLocation(topTen.getPlayers().get(position).getLatitude(),topTen.getPlayers().get(position).getLongitude());
            }
        });
    }

    private void initAdapter() {
        for(int i =1; i<= SIZE; i++)
            numbers.add(i+"");

        String gson_topTen = MySP.getInstance().getString(Activity_GameOver.TOP_TEN, Activity_GameOver.NOT_EXIST);

        if(!gson_topTen.equals(Activity_GameOver.NOT_EXIST)){
            topTen = gason.fromJson(gson_topTen, TopTen.class);
            int size = topTen.getPlayers().size();
            ArrayList<Player> players = topTen.getPlayers();

            for (Player p : players) {
                titles.add(p.getName());
                scores.add("score: "+p.getScore());
            }

            adapter=new MyListAdapter(getActivity(), titles, scores,numbers);
            fragment_list_LST_topTen.setAdapter(adapter);
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
