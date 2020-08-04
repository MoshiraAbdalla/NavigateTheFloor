package com.example.naviloora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.*;
import java.lang.*;
import java.io.*;
import static android.graphics.Color.*;


public class login extends AppCompatActivity {
    private static final String TAG = "login";

    private ImageView image;
    private ImageView image2;
    private ImageView drawing;
    private ViewGroup mainlayout;
    private DatabaseReference myRef;
    private ListView list;
    final ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        image = (ImageView) findViewById(R.id.Man);
        image2 = (ImageView) findViewById(R.id.Man2);
        list = (ListView) findViewById(R.id.list);
        mainlayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
        drawing = (ImageView) findViewById(R.id.line);


        // Adding rooms names in the list

        arrayList.add("حمام رجال");
        arrayList.add("حمام سيدات");
        arrayList.add("ورشة");
        arrayList.add("مكتب معيدات");
        arrayList.add("فصل 1");
        arrayList.add("فصل 2");
        arrayList.add("ورشة مشاريع");
        arrayList.add("سكرتارية و رئيس قسم");
        arrayList.add("كافاتريا");
        arrayList.add("فصل 3");
        arrayList.add("فصل 4");
        arrayList.add("مكتب دكتور حنفي");
        arrayList.add("مكتب 412");
        arrayList.add("مكتب 428");
        arrayList.add("مكتب 429");
        arrayList.add("مكتب 430");
        arrayList.add("معمل شبكات الحاسب");
        arrayList.add("ورشة");
        arrayList.add("معمل الدوائر الرقمية");
        arrayList.add("معمل التحكم بالحاسبات");
        arrayList.add("معمل التحكم");
        arrayList.add("معمل تشخيص الاعطال");
        arrayList.add("سلم خلفي");
        arrayList.add("قاعة شبكة المعلومات");
        //  arrayList.add("esp32data");
        //   arrayList.add("مكتب دكتور عبدالله");

        // arrayList.add("مكتب دكتور عبدالله");
        //    arrayList.add("مكتب دكتور حسين");
        //    arrayList.add("مكتب دكتور أحمد محمود");
        //    arrayList.add("مكتب معيدين");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setVisibility(View.GONE); // remove the list if you are not searching
        list.setAdapter(arrayAdapter);
        // requesting  of the room position when click on it in the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String roomname=(String) list.getItemAtPosition(position);
                Toast.makeText(login.this,roomname,Toast.LENGTH_LONG).show();
                toastMessage("Getting " + roomname + " Position...");
                list.setVisibility(View.GONE); // after searching remove the list again
                // Read from the firebase database to get the room position
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String X =  dataSnapshot.child(roomname).child("x").getValue().toString(); // getting the x position of the requested room from the database
                        String Y =  dataSnapshot.child(roomname).child("y").getValue().toString(); // getting the y position of the requested room from the database
                        String  mobile_distance =  dataSnapshot.child("esp32data").child("data").getValue().toString(); // getting the y position of the requested room from the database
                        // changing the x and y from string to int
                        final float roomY = Float.parseFloat(Y);
                        final float roomX = Float.parseFloat(X);
                        float dis = Float.parseFloat(mobile_distance);
                        final float  distance= (float) (dis* 39.370);//convert the meters into inches


                        toastMessage("wait until getting ur position ");
                        //setting an initial position for the green dot ( which represents a person )
                        image.setX(distance);
                        image.setY(155);
                        toastMessage("u are here " + distance + " !...");
                      /*  new Handler().postDelayed(new Runnable() {
                            public void run() {
                                float newdistance=0;
                                for(int i=0;i<20;i++){
                                    newdistance += distance;
                                }
                                final float newdistance2=newdistance/20;

                                toastMessage("wait until getting ur position ");
                                //setting an initial position for the green dot ( which represents a person )
                                image.setX(newdistance2);
                                image.setY(155);
                                toastMessage("u are here " + newdistance2 + " !...");
                            }
                        }, 25000);*/


                        int geting_image_x = (int) image.getX();
                        int geting_image_y= (int) image.getY();
                        // Drawing the Line between the green dot and the room requested

                        final Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager().getDefaultDisplay().getWidth(), (int) getWindowManager().getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
                        final Canvas canvas = new Canvas(bitmap);
                        drawing.setImageBitmap(bitmap);
                        final Paint paint = new Paint();
                        paint.setColor(BLUE);
                        paint.setStrokeWidth(19);
                        final int Start_x = geting_image_x;
                        final int Start_y =geting_image_y;
                        final int End_x = (int) roomX;
                        final int End_y =  (int) roomY;
                        canvas.drawLine(Start_x, Start_y, End_x, End_y, paint); //drawing the line using canvas

                        //Delay function to move the green dot from it's current position to the requested position and remove the line
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                drawing.setImageDrawable(null);
                                // canvas.drawCircle(settingX,settingY,25,paint1);
                                image2.setX(roomX);
                                image2.setY(roomY);
                            }
                        }, 10000);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
    //toastMessage
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //Function to add the list to the search bar using a search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchitem=menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> TempList = new ArrayList<>();
                for (final String temp:arrayList){
                    if (temp.toLowerCase().contains(newText.toLowerCase())){
                        TempList.add(temp);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(login.this, android.R.layout.simple_list_item_1, TempList);
                list.setVisibility(View.VISIBLE); // make the list visible while searching
                list.setAdapter(arrayAdapter);
                return  true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}

//create a class to refer to the x and y
class point {
    public int x;
    public int y;

    public  point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
class mob_distance{
    public float data  ;


    public  mob_distance(float data) {
        this.data = data;
    }
}


