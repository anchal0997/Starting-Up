package com.example.anchalgarg.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SMEListActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;

    private DatabaseReference mDatabaseSME;
    private String email = new String("");
    private String name = new String("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smelist);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseSME=FirebaseDatabase.getInstance().getReference().child("Domain").child("Subject-matter");
        final TableLayout tl=(TableLayout)findViewById(R.id.table_layout);
        mDatabaseSME.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    final String user_id = domainSnapshot.getKey();
                    Log.d("NAMESS--",""+user_id);
                    // final String[] name = {""};
                    name  = "";



                    mDatabaseRef.child(user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name =dataSnapshot.child("name").getValue().toString();

                            String skillLvl=  domainSnapshot.child("interestedIn").getValue().toString();
                            String link=domainSnapshot.child("Payout").getValue().toString();
                            String Contact_no = dataSnapshot.child("Email_id").getValue().toString();
                            TableRow row = (TableRow) LayoutInflater.from(SMEListActivity.this).inflate(R.layout.table_row, null);

                            ((TextView)row.findViewById(R.id.name)).setText(link);
                            ((TextView)row.findViewById(R.id.from)).setText(name);
                            ((TextView)row.findViewById(R.id.email_id)).setText(skillLvl);
                            ((TextView)row.findViewById(R.id.to)).setText(Contact_no);
                            tl.addView(row);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
