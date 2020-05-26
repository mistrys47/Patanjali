package com.example.patanjali;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class stock extends Fragment {

    private List<stats> statsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private stock_adapter stock_adapter;
    public stock() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_stock, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_stock);
            stock_adapter = new stock_adapter(statsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(stock_adapter);
            database db = new database(getActivity());
            SQLiteDatabase db1;
            db1 = db.getWritableDatabase();
            Cursor c = db.getstock(db1);
            while (c.moveToNext()) {
                //Toast.makeText(getContext(),c.getString(0)+c.getString(1)+ c.getString(2),Toast.LENGTH_LONG).show();
                statsList.add(new stats(c.getString(2), c.getString(3), c.getString(4)));
            }
            stock_adapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
}