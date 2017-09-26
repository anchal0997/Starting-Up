package com.example.anchalgarg.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SMEActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private Spinner mTechSpinner;
    private EditText mSpecialization;
    private EditText mLevel;
    private EditText mInterested;
    private EditText mPayout;
    private Button mBtn;

    int pos=0;
    String[] tech_domain = null;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sme);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference();
        mTechSpinner=(Spinner)findViewById(R.id.spinner_tech);
        mSpecialization=(EditText)findViewById(R.id.specialization);
        mLevel=(EditText)findViewById(R.id.level);
        final String user_id = getIntent().getStringExtra("tag");
        final String role=getIntent().getStringExtra("senddata");
        mInterested=(EditText)findViewById(R.id.intersted_in);
        mPayout=(EditText)findViewById(R.id.exp_Pay);
        mBtn=(Button)findViewById(R.id.complete);

        mDatabaseRef.child("Tech_Area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // HashMap<String,Integer> domainName=new HashMap<String, Integer>();
                //  domainName = (HashMap<String,Integer>)domainSnapshot.getValue(HashMap.class);

                tech_domain=new String[(int) dataSnapshot.getChildrenCount()];
                i=0;

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    tech_domain[i] =domainSnapshot.getKey();
                    i++;
                }

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(SMEActivity.this, android.R.layout.simple_spinner_item, tech_domain);
                domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTechSpinner.setAdapter(domainsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTechSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                pos=i;
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String spec=mSpecialization.getText().toString().trim();

                final String lev= mLevel.getText().toString().trim();
                final String interested= mInterested.getText().toString().trim();
                final String payout=mPayout.getText().toString().trim();

                if(!TextUtils.isEmpty(spec)&&!TextUtils.isEmpty(lev)&&!TextUtils.isEmpty(interested)&&!TextUtils.isEmpty(payout))
                {
                    DatabaseReference mDataEnter=mDatabaseRef.child("Domain").child(role).child(user_id);
                    mDataEnter.child("Tech_Area").setValue(pos);
                    mDataEnter.child("Specification").setValue(spec);
                    mDataEnter.child("level").setValue(lev);
                    mDataEnter.child("interestedIn").setValue(interested);
                    mDataEnter.child("Payout").setValue(payout);
                    Intent mainIntent=new Intent(SMEActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("senddata",role);
                    mainIntent.putExtra("tag",user_id);
                    startActivity(mainIntent);

                }
                else
                {
                    Toast.makeText(SMEActivity.this,"FILL all the Fields",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}
