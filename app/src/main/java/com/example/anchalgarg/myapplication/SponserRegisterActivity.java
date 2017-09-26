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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SponserRegisterActivity extends AppCompatActivity {

    private Spinner mDomainSpinner;
    private Spinner mSubDomainSpinner;
    private DatabaseReference mDatabaseRef;
    private Button mComplete;
    String[] busi_domain = null;
    String[] subBusi_domain=null;
    int DomainPos;
    int SubDomainPos;
    int i;
    private EditText mFromFund;
    private EditText mToFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_register);
        mDatabaseRef=FirebaseDatabase.getInstance().getReference();
        mDomainSpinner=(Spinner)findViewById(R.id.DomainSpinner);
        final String User_id = getIntent().getStringExtra("tag");
        final String domain=getIntent().getStringExtra("senddata");
        mSubDomainSpinner=(Spinner)findViewById(R.id.SubDomainSpinner);
        mComplete=(Button)findViewById(R.id.complete_Btn);
        mFromFund=(EditText)findViewById(R.id.from_fund);
        mToFund=(EditText)findViewById(R.id.to_fund);


        mDatabaseRef.child("Business Domain").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // HashMap<String,Integer> domainName=new HashMap<String, Integer>();
                //  domainName = (HashMap<String,Integer>)domainSnapshot.getValue(HashMap.class);

                busi_domain=new String[(int) dataSnapshot.getChildrenCount()];
                i=0;

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    busi_domain[i] =domainSnapshot.getKey();
                    i++;
                }

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(SponserRegisterActivity.this, android.R.layout.simple_spinner_item, busi_domain);
                domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mDomainSpinner.setAdapter(domainsAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mDomainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int ia, long l) {
                DomainPos=ia;
                mDatabaseRef.child("Business Domain").child(busi_domain[DomainPos]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        subBusi_domain=new String[(int) dataSnapshot.getChildrenCount()];
                        i=0;

                        for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                            subBusi_domain[i] =domainSnapshot.getKey();
                            i++;
                        }
                        ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(SponserRegisterActivity.this, android.R.layout.simple_spinner_item, subBusi_domain);
                        domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSubDomainSpinner.setAdapter(domainsAdapter);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        mSubDomainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                SubDomainPos=i;

            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });


        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String From=mFromFund.getText().toString().trim();
                final String To=mToFund.getText().toString().trim();
                if(!TextUtils.isEmpty(From)&&!TextUtils.isEmpty(To))
                {
                    mDatabaseRef.child("Users").child(User_id).child("Busi_Domain").setValue(DomainPos);
                    mDatabaseRef.child("Users").child(User_id).child("SubDomain").setValue(SubDomainPos);
                    mDatabaseRef.child("Domain").child("Sponser").child(User_id).child("BusinessDomain").setValue(busi_domain[DomainPos]);
                    mDatabaseRef.child("Domain").child("Sponser").child(User_id).child("SubDomain").setValue(subBusi_domain[SubDomainPos]);
                    mDatabaseRef.child("Domain").child("Sponser").child(User_id).child("RangeFrom").setValue(From);
                    mDatabaseRef.child("Domain").child("Sponser").child(User_id).child("RangeTo").setValue(To);
                    Intent mainIntent=new Intent(SponserRegisterActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("senddata","Sponser");
                    mainIntent.putExtra("tag",User_id);
                    startActivity(mainIntent);
                }

            }
        });

    }
}
