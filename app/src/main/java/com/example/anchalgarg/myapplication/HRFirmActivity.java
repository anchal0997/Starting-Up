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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HRFirmActivity extends AppCompatActivity {
    private EditText mSkillLevel;
    private DatabaseReference mDatabaseTech;
    private Button mBtn;
    private Spinner mTechSpinner;
    private EditText mContact;
    int i=0;
    int pos=0;
    String[] tech_domain = null;
    private EditText mWebLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrfirm);
        mSkillLevel=(EditText)findViewById(R.id.skillLvl);
        mDatabaseTech= FirebaseDatabase.getInstance().getReference();
        mTechSpinner=(Spinner)findViewById(R.id.spinner);
        mContact=(EditText)findViewById(R.id.contact_no);
        mWebLink=(EditText)findViewById(R.id.webLink);
        final String user_id = getIntent().getStringExtra("tag");
        final String role=getIntent().getStringExtra("senddata");
        mBtn=(Button)findViewById(R.id.CompleteReg);

        mDatabaseTech.child("Tech_Area").addValueEventListener(new ValueEventListener() {
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

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(HRFirmActivity.this, android.R.layout.simple_spinner_item, tech_domain);
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
                final String skill=mSkillLevel.getText().toString().trim();
                final String webLink=mWebLink.getText().toString().trim();
                final String ContactNo=mContact.getText().toString().trim();
                if(!TextUtils.isEmpty(skill)&&!TextUtils.isEmpty(webLink)&&!TextUtils.isEmpty(ContactNo))
                {
                    DatabaseReference mDataEnter=mDatabaseTech.child("Domain").child(role).child(user_id);
                    mDataEnter.child("Skill_level").setValue(skill);
                    mDataEnter.child("tech_area").setValue(pos);
                    mDataEnter.child("contact").setValue(ContactNo);
                    mDataEnter.child("link").setValue(webLink);
                    Intent mainIntent=new Intent(HRFirmActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.putExtra("senddata","HR_firm");
                    mainIntent.putExtra("tag",user_id);
                    startActivity(mainIntent);
                }
                else
                {
                    Toast.makeText(HRFirmActivity.this,"FILL all the Fields",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
