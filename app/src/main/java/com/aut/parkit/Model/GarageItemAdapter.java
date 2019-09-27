package com.aut.parkit.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;
import com.aut.parkit.R;

import java.net.ContentHandler;
import java.util.LinkedList;

public class GarageItemAdapter extends BaseAdapter {

    LayoutInflater mInflator;
    String[] lPlates, name;
    String defaultCar;
    User user;
    LinkedList<Vehicle> garage;

    public GarageItemAdapter(Context c) {

        user = new User();
        garage = user.getGarage();

        lPlates = new String[garage.size()];
        name = new String[garage.size()];

        for (int i = 0; i < garage.size(); i++){
            lPlates[i] = garage.get(i).getNumberPlate();
            name[i] = garage.get(i).getVehicleName();
        }

        defaultCar = user.getDefaultVehicle().getNumberPlate();
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflator.inflate(R.layout.garage_car_view, null);

        TextView licence, name;
        ImageView star;

        name = v.findViewById(R.id.carName);
        licence = v.findViewById(R.id.lPlateView);

        star = v.findViewById(R.id.carStar);

        name.setText(this.name[i]);
        licence.setText(this.lPlates[i]);

        if (this.lPlates[i].contentEquals(this.defaultCar)){
            star.setVisibility(View.VISIBLE);
        }
        else {
            star.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}
