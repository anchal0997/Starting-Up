package com.example.anchalgarg.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListOfSponsers extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;

    ArrayList<sponsor> list = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int tentative_Cost;
    private DatabaseReference mDatabase;
    private String email = new String("");
    private String name = new String("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_sponsers);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Domain").child("Sponser");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        final String bDomain = getIntent().getStringExtra("senddata");
        final String USERID = getIntent().getStringExtra("tag");
        //final String[] USERID = {""};
        final TableLayout tl=(TableLayout)findViewById(R.id.tableLayout);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
          //      USERID[0] =firebaseAuth.getCurrentUser().getUid().toString();
            }
        };
/*
        if(USERID!=null) {
            FirebaseDatabase.getInstance().getReference().child("Domain").child("Founder").child(USERID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tentative_Cost = Integer.parseInt(dataSnapshot.child("Tentative_Cost").getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            tentative_Cost=100;
        }
    */
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // HashMap<String,Integer> domainName=new HashMap<String, Integer>();
                //  domainName = (HashMap<String,Integer>)domainSnapshot.getValue(HashMap.class);

                /*ntain=new String[(int) dataSnapshot.getChildrenCount()];
                i=0;

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    tech_domain[i] =domainSnapshot.getKey();
                    i++;
                }

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(SMEActivity.this, android.R.layout.simple_spinner_item, tech_domain);
                domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTechSpinner.setAdapter(domainsAdapter);*/
                Log.d("Checkdata",dataSnapshot.toString());
                for (final DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    final String user_id = domainSnapshot.getKey().toString();
                    Log.d("userId",""+user_id+" "+ bDomain);
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            email=dataSnapshot.child(user_id).child("Email_id").getValue().toString();
                            name=dataSnapshot.child(user_id).child("name").getValue().toString();
                            int rangeFrom= Integer.parseInt( domainSnapshot.child("RangeFrom").getValue().toString());
                            int rangeTo= Integer.parseInt( domainSnapshot.child("RangeTo").getValue().toString());
                            sponsor sp = new sponsor(name , rangeFrom,rangeTo,email);
                            //list.add(sp);

                            TableRow row = (TableRow) LayoutInflater.from(ListOfSponsers.this).inflate(R.layout.table_row, null);

                            ((TextView)row.findViewById(R.id.name)).setText((sp.user_id)+"");

                            ((TextView)row.findViewById(R.id.email_id)).setText((sp.email_id)+"");
                            ((TextView)row.findViewById(R.id.from)).setText((sp.RangeFrom)+" ");
                            ((TextView)row.findViewById(R.id.to)).setText(" "+sp.RangeTo);
                            tl.addView(row);
                            Log.d("hello",""+email+"  "+ name);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //Log.d("hello1",""+findEmailVALUE(user_id)+"  "+findNameVALUE(user_id));




                   // Log.d("Range12",""+rangeFrom+" "+rangeTo);
//                   if((domainSnapshot.child(user_id).child("BusinessDomain").getValue().toString()).equals(bDomain)) {

                   // Log.d("ooooo",""+sp.user_id+"  "+sp.email_id);

  //                  }
                }

            /*for(int i = 0 ;i<list.size();i++){

                sponsor sp = list.get(i);
                Log.d("tent_cost",""+tentative_Cost);

               // if(sp.RangeFrom >= 0.8 * tentative_Cost && sp.RangeTo <= 1.2 * tentative_Cost) {

                    TableRow row = (TableRow) LayoutInflater.from(ListOfSponsers.this).inflate(R.layout.table_row, null);

                ((TextView)row.findViewById(R.id.name)).setText((sp.user_id)+"");

                ((TextView)row.findViewById(R.id.email_id)).setText((sp.email_id)+"");
                    ((TextView)row.findViewById(R.id.from)).setText((sp.RangeFrom)+" ");
                    ((TextView)row.findViewById(R.id.to)).setText(" "+sp.RangeTo);
                    tl.addView(row);
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

    private class sponsor{

        String user_id;
        int RangeFrom;
        int RangeTo;
        String email_id;

        sponsor(String user_id,int from,int to,String email){

            this.email_id = email;
            this.user_id = user_id;
            this.RangeFrom = from;
            this.RangeTo = to;
        }

        //public void display
    }

    public String findNameVALUE(final String UserId)
    {
        final String[] Name = {""};
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //email=dataSnapshot.child(UserId).child("Email_id").getValue().toString();
                Name[0] =dataSnapshot.child(UserId).child("name").getValue().toString();
                Log.d("hello",""+Name[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return Name[0];
    }
    public String findEmailVALUE(final String UserId)
    {
        final String[] Name = {""};
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name[0]=dataSnapshot.child(UserId).child("Email_id").getValue().toString();
                 //=dataSnapshot.child(UserId).child("name").getValue().toString();
                Log.d("hello",""+Name[0]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return Name[0];
    }
}
