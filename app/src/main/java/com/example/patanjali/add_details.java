package com.example.patanjali;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.math.BigDecimal;

public class add_details extends Fragment {


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
        View view= inflater.inflate(R.layout.fragment_add_details, container, false);
        //Toast.makeText(getContext(),"add_details",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final database db=new database(getActivity());
        final SQLiteDatabase db1 = db.getWritableDatabase();
        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        quantity = (EditText) view.findViewById(R.id.quantity);
        add = (Button) view.findViewById(R.id.add);
        sold = (Button) view.findViewById(R.id.sold);
        
        Bundle bundle = getArguments();
        count = "-1";
        if (bundle != null)
        {
            barcode = bundle.getString("barcode");
            Toast.makeText(getContext(),barcode+"in add details",Toast.LENGTH_LONG).show();
            Cursor cursor = db.getitemdetails(db1,barcode);

            if(cursor.getCount()>0)
            {
                while(cursor.moveToNext())
                {
                    name.setText(cursor.getString(0));
                    price.setText(cursor.getString(1));
                    count = cursor.getString(2);
                }
                name.setEnabled(false);
                price.setEnabled(false);
            }
            else
            {
                sold.setEnabled(false);
            }
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(checkValid(price.getText().toString()) && checkValid(quantity.getText().toString())){
                        if (count.equals("-1")) {
                            Boolean b = db.insert1(db1, barcode, name.getText().toString(), price.getText().toString(), quantity.getText().toString());
                            if (b) {
                                Toast.makeText(getContext(), "successfully added", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            int q = Integer.parseInt(count);
                            q += Integer.parseInt(quantity.getText().toString());
                            Boolean b = db.update1(db1, barcode, String.valueOf(q));
                            if (b) {
                                Toast.makeText(getContext(), "successfully added", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"Enter valid number",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(),e+"",Toast.LENGTH_LONG).show();
                }
                FragmentManager fragmentManager1=getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.fl1,new stock()).addToBackStack(null).commit();
            }
        });
        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p,q;
                p = price.getText().toString();
                q = quantity.getText().toString();
                if(checkValid(p) && checkValid(q))
                {
                    if(Integer.parseInt(q)>Integer.parseInt(count)){
                        Toast.makeText(getContext(),"stock is getting negative",Toast.LENGTH_LONG).show();
                    }
                    else {
                        int q1 = Integer.parseInt(count);
                        q1 -= Integer.parseInt(q);
                        Boolean b = db.update1(db1, barcode, String.valueOf(q1));
                        if (b) {
                            Toast.makeText(getContext(), "successfully sold", Toast.LENGTH_LONG).show();
                        }
                        Cursor x = db.getsolddetails(db1,barcode);
                        if(x.getCount()>0)
                        {
                            while (x.moveToNext()){
                                q1 = Integer.parseInt(x.getString(0));
                            }
                            q1+=Integer.parseInt(q);
                            b = db.update2(db1,barcode,String.valueOf(q1));
                            if (b) {
                                Toast.makeText(getContext(), "updated sold", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            b = db.insert2(db1, barcode, name.getText().toString(), price.getText().toString(), quantity.getText().toString());
                            if (b) {
                                Toast.makeText(getContext(), "inserted sold", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(),"Enter valid number",Toast.LENGTH_LONG).show();
                }
                FragmentManager fragmentManager1=getFragmentManager();
                fragmentManager1.beginTransaction().replace(R.id.fl1,new stock()).addToBackStack(null).commit();
            }
        });

    }
    private static boolean checkValid(String value) {
        try {
            BigDecimal decimal = new BigDecimal(value);
            return decimal.signum() > 0 ;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

}