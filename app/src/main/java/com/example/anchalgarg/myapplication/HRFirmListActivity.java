package com.example.anchalgarg.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public class HRFirmListActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;

    //private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int tentative_Cost;
    private DatabaseReference mDatabase;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrfirm_list);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Domain").child("HR_firm");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        final TableLayout tl=(TableLayout)findViewById(R.id.table_layout);
       // m.inst = this;

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    final String user_id = domainSnapshot.getKey();
                    Log.d("NAMESS--",""+user_id);
                   // final String[] name = {""};
                    name  = "";



                    mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name =dataSnapshot.child("name").getValue().toString();
                            int skillLvl= Integer.parseInt( domainSnapshot.child("Skill_level").getValue().toString());
                            String link=domainSnapshot.child("link").getValue().toString();
                            String Contact_no = domainSnapshot.child("contact").getValue().toString();
                            TableRow row = (TableRow) LayoutInflater.from(HRFirmListActivity.this).inflate(R.layout.table_row, null);

                            ((TextView)row.findViewById(R.id.name)).setText(Contact_no);
                            ((TextView)row.findViewById(R.id.from)).setText((skillLvl)+" ");
                            ((TextView)row.findViewById(R.id.email_id)).setText(name);
                            ((TextView)row.findViewById(R.id.to)).setText(link);
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
  /*  @Override
    protected void onPause() {
        super.onPause();


            if(isApplicationSentToBackground(getApplicationContext()))
            {

            }


    }
    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }*/
}
