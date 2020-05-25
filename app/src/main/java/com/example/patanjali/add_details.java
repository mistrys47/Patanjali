package com.example.patanjali;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class add_details extends Fragment {

    database db=new database(getContext());
    SQLiteDatabase db1;
    EditText name,price,quantity;
    Button add,sold;
    String count;
    String barcode="";
    public add_details() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sold, container, false);
        Toast.makeText(getContext(),"add_details",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db1 = db.getWritableDatabase();
        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        quantity = (EditText) view.findViewById(R.id.quantity);
        add = (Button) view.findViewById(R.id.add);
        sold = (Button) view.findViewById(R.id.sold);
        Toast.makeText(getContext(),"in add details",Toast.LENGTH_LONG).show();
        
//        Bundle bundle = getArguments();
//        count = "-1";
//        if (bundle != null)
//        {
//            barcode = bundle.getString("barcode");
//            Toast.makeText(getContext(),barcode+"in add details",Toast.LENGTH_LONG).show();
//            Cursor cursor = db.getitemdetails(db1,barcode);
//
//            if(cursor.getCount()>0)
//            {
//                while(cursor.moveToNext())
//                {
//                    name.setText(cursor.getString(0));
//                    price.setText(cursor.getString(1));
//                    count = cursor.getString(2);
//                }
//                name.setEnabled(false);
//                price.setEnabled(false);
//            }
//            else
//            {
//                sold.setEnabled(false);
//            }
//        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count.equals("-1"))
                {
                    Boolean b = db.insert1(db1,barcode,name.getText().toString(),price.getText().toString(),quantity.getText().toString());
                    if(b)
                    {
                        Toast.makeText(getContext(),"successfully added",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    int q = Integer.parseInt(count);
                    q+=Integer.parseInt(quantity.getText().toString());
                    Boolean b = db.update1(db1,barcode,String.valueOf(q));
                    if(b)
                    {
                        Toast.makeText(getContext(),"successfully added",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}