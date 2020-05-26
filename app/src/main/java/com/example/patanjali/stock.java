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

public class stock extends Fragment {

    public stock() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sold, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            database db=new database(getActivity());
            SQLiteDatabase db1;
            db1 = db.getWritableDatabase();
            Cursor c = db.getitemdetails(db1, "799418201542");
            while (c.moveToNext()) {
                Toast.makeText(getContext(), c.getString(0) + c.getString(1) + c.getString(2), Toast.LENGTH_LONG).show();
            }
        }
        catch ( Exception e)
        {
            Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
}