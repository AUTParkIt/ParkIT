package com.aut.parkit.View;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.CarPark;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarparkManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.DocumentConverter;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSpace;
import com.aut.parkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class ViewEmptySpace extends AppCompatActivity {

    private TextView car1, space1, car2, space2;
    private LinkedList<CarPark> cars;
    public static final String CampusKey = "Campus_Key";
    private String campusID;
    private LinkedList<Button> spaces = new LinkedList<>();
    private static FirebaseFirestore mFStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_empty_space);

        car1 = findViewById(R.id.carPark1View);
        space1 = findViewById(R.id.carPark1Space);
        car2 = findViewById(R.id.carPark2View);
        space2 = findViewById(R.id.carPark2Space);

        campusID = this.getIntent().getExtras().getString(CampusKey);

        Log.e("Test", "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                cars = CarparkManager.getAllCarparksFromDB(campusID);
                final CarPark car11 = cars.get(0);
                CarPark car23 = null;
                if (cars.size() > 1){
                    car23 = cars.get(1);
                }

                final CarPark car22 = car23;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        car1.setText(car11.getCarParkID());
                        space1.setText(car11.getFreeSpaces() + "/" + car11.getTotalSpaces());

                        if (cars.size() > 1){
                            car2.setText(car22.getCarParkID());
                            space2.setText(car22.getFreeSpaces() + "/" + car22.getTotalSpaces());
                        }
                        else {
                            car2.setText("");
                            space2.setText("");
                        }
                    }
                });
            }
        }).start();


            spaces.add((Button) findViewById(R.id.button30));
            spaces.add((Button) findViewById(R.id.button31));
            spaces.add((Button) findViewById(R.id.button32));
            spaces.add((Button) findViewById(R.id.button33));
            spaces.add((Button) findViewById(R.id.button34));
            spaces.add((Button) findViewById(R.id.button35));
            spaces.add((Button) findViewById(R.id.button36));
            spaces.add((Button) findViewById(R.id.button37));
            spaces.add((Button) findViewById(R.id.button38));
            spaces.add((Button) findViewById(R.id.button39));
            spaces.add((Button) findViewById(R.id.button40));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < spaces.size(); i++){
                        final DocumentReference docRef = mFStore.collection("Campus/Manukau/Carparks/Manukau-A/ParkingSpaces").document("Manukau-A-" + (i + 1));

                        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                if (!documentSnapshot.exists()){
                                    return;
                                }

                                ParkingSpace sp = DocumentConverter.toParkingSpace(documentSnapshot.getData());
                                StringTokenizer token = new StringTokenizer(docRef.getId(), "-");

                                token.nextToken();
                                token.nextToken();
                                String snumb = token.nextToken();

                                try {
                                    int numb = Integer.parseInt(snumb);
                                    numb--;

                                    Button space = spaces.get(numb);

                                    if (sp.isBooked()){
                                        space.setBackgroundColor(Color.RED);
                                    }
                                    else {
                                        space.setBackgroundColor(Color.WHITE);
                                    }
                                }
                                catch(Exception v){
                                    Log.e("Map", e.toString());
                                }

                            }
                        });

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){

                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    if (!documentSnapshot.exists()){
                                        return;
                                    }

                                    ParkingSpace sp = DocumentConverter.toParkingSpace(documentSnapshot.getData());
                                    StringTokenizer token = new StringTokenizer(docRef.getId(), "-");

                                    token.nextToken();
                                    token.nextToken();
                                    String snumb = token.nextToken();

                                    try {
                                        int numb = Integer.parseInt(snumb);
                                        numb--;

                                        Button space = spaces.get(numb);

                                        if (sp.isBooked()){
                                            space.setBackgroundColor(Color.RED);
                                        }
                                        else {
                                            space.setBackgroundColor(Color.WHITE);
                                        }
                                    }
                                    catch(Exception v){
                                        Log.e("Map", v.toString());
                                    }
                                }
                            }
                        });


                    }



                }
            }).start();
    }
}
