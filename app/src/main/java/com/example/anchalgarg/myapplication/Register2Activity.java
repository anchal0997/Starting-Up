package com.example.anchalgarg.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Register2Activity extends AppCompatActivity {

    private Button mCompleteRegister;
    private DatabaseReference mDatabaseRef;
    Spinner spinner;
    private DatabaseReference mDatabaseBusiness;
    private EditText mIdea;
    private EditText mDisc;
    private EditText mTentCost;
    private FirebaseAuth mAuth;
    private EditText mPerProfit;
    int position;
    int i=0;
    String subDomain="";
    private EditText mRevenue;
    String[] busi_domain = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mAuth=FirebaseAuth.getInstance();
        final String s = getIntent().getStringExtra("tag");
        final String doMain= getIntent().getStringExtra("senddata");
        mIdea=(EditText)findViewById(R.id.idea_feild);

        mTentCost=(EditText)findViewById(R.id.tentCost_field);
        mDisc=(EditText)findViewById(R.id.desc_field);
        mRevenue=(EditText)findViewById(R.id.rev_field);
        spinner=(Spinner)findViewById(R.id.business_spinner);
        mPerProfit=(EditText)findViewById(R.id.per_profit);
        mCompleteRegister=(Button)findViewById(R.id.button3);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Domain").child("Founder").child(s);
        mDatabaseBusiness=FirebaseDatabase.getInstance().getReference().child("Business Domain");
       // busi_domain=new String[7];
        Log.d("hello",s);
        Toast.makeText(Register2Activity.this,"Dont leave any field empty",Toast.LENGTH_LONG).show();
        mDatabaseBusiness.addValueEventListener(new ValueEventListener() {
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
                //   int i=0;

                 /*   for(Map.Entry<String,Integer> entry:messages.entrySet()){
                        domain[i]=entry.getKey();
                        i++;
                       // Log.d("Transition",""+entry.getKey()+"-"+entry.getValue());

                    }*/

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(Register2Activity.this, android.R.layout.simple_spinner_item, busi_domain);
                domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(domainsAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                position=i;
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        mCompleteRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child("Idea").setValue(mIdea.getText().toString().trim());
                mDatabaseRef.child("desc").setValue(mDisc.getText().toString().trim());
                mDatabaseRef.child("Revenue").setValue(mRevenue.getText().toString().trim());
                mDatabaseRef.child("Tentative_Cost").setValue(mTentCost.getText().toString().trim());
                mDatabaseRef.child("per_profit").setValue(mPerProfit.getText().toString().trim());
                mDatabaseRef.child("BusinessDomain").setValue(position);
                mDatabaseBusiness.child(busi_domain[position]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                            subDomain=domainSnapshot.getKey().toString();
                            Log.d("SSSSS",""+subDomain);
                            mDatabaseBusiness.child(busi_domain[position]).child(subDomain).child(s).setValue(0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                Log.d("SUBDOMAIN",""+subDomain);
                Intent mainIntent = new Intent(Register2Activity.this,startup_activity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("senddata",doMain);
                mainIntent.putExtra("tag",s);
                startActivity(mainIntent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if(item.getItemId()==R.id.action_add)
        {
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }*/
        if(item.getItemId()==R.id.action_logout)
        {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }
}
