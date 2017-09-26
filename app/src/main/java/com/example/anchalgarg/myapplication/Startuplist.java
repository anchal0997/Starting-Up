package com.example.anchalgarg.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Startuplist extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    ArrayList<startup> list = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int tentative_Cost;
    private DatabaseReference mDatabase;
    private String email , tc , idea;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startuplist);

        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Domain").child("Founder");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        final String bDomain = getIntent().getStringExtra("senddata");
        final String USERID = getIntent().getStringExtra("tag");
        //final String[] USERID = {""};
        final TableLayout tl=(TableLayout)findViewById(R.id.tableLayout);

        mAuth=FirebaseAuth.getInstance();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (final DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    final String user_id = domainSnapshot.getKey().toString();
                    Log.d("userId",""+user_id+" "+bDomain);

                   // String rangeFrom= Integer.parseInt( domainSnapshot.child("RangeFrom").getValue().toString());
                    // rangeTo= Integer.parseInt( domainSnapshot.child("RangeTo").getValue().toString());

                    mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            Log.d("DATA!!",""+dataSnapshot1);

                            email=dataSnapshot1.child("Email_id").getValue().toString();
                            name=dataSnapshot1.child("name").getValue().toString();
                            idea = domainSnapshot.child("Idea").getValue().toString();
                            tc = domainSnapshot.child("Tentative_Cost").getValue().toString();
                            TableRow row = (TableRow) LayoutInflater.from(Startuplist.this).inflate(R.layout.table_row, null);
                            ((TextView)row.findViewById(R.id.name)).setText(email);

                            ((TextView)row.findViewById(R.id.email_id)).setText(name);
                            ((TextView)row.findViewById(R.id.from)).setText(idea);
                            ((TextView)row.findViewById(R.id.to)).setText(tc);
                            tl.addView(row);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


//                   if((domainSnapshot.child(user_id).child("BusinessDomain").getValue().toString()).equals(bDomain)) {
                    //startup sp = new startup(name , idea,tc,email);
                    //list.add(sp);
                    //                  }
                }
/*
                for(int i = 0 ;i<list.size();i++){

                    startup sp = list.get(i);
                    Log.d("tent_cost",""+tentative_Cost);

                    // if(sp.RangeFrom >= 0.8 * tentative_Cost && sp.RangeTo <= 1.2 * tentative_Cost) {


                    //}
                }*/



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Checkdata","------------------------------------------------------");

            }
        });
        Log.d("YES",""+USERID);


    }




    private class startup{

        String name;
        String idea;
        String tentative_cost;
        String email_id;

        startup(String name,String idea,String tt,String email){
            this.email_id = email;
            this.name = name;
            this.tentative_cost = tt;
            this.idea = idea;
        }

        //public void display
    }
}
