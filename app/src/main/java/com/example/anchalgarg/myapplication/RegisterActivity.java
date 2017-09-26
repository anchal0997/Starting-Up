package com.example.anchalgarg.myapplication;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText mNameFeild;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mRepassword;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseDomain;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    Spinner spinner;
    int position;
    int i=0;
    String[] domain = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNameFeild=(EditText) findViewById(R.id.NameFeild);
        mEmail=(EditText)findViewById(R.id.EmailFeild);
        mPassword=(EditText)findViewById(R.id.PasswordFeild);
        mRepassword=(EditText)findViewById(R.id.RepasswordFeild);
        mRegister=(Button)findViewById(R.id.register);
        mProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        spinner=(Spinner)findViewById(R.id.stream_spinner);
        //YO spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseDomain=FirebaseDatabase.getInstance().getReference().child("Domain");
       // Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //YO final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, streams);


       //YO adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
      //YO  spinner.setAdapter(adapter);

      /*YO  class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                position=pos;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        }*/
        //domain=new String[7];
        Log.d("yeah",""+mDatabaseDomain);
        mDatabaseDomain.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                domain=new String[(int) dataSnapshot.getChildrenCount()];
                   // HashMap<String,Integer> domainName=new HashMap<String, Integer>();
                     //  domainName = (HashMap<String,Integer>)domainSnapshot.getValue(HashMap.class);


                i=0;

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    domain[i] =domainSnapshot.getKey();
                    i++;
                }
                 //   int i=0;

                 /*   for(Map.Entry<String,Integer> entry:messages.entrySet()){
                        domain[i]=entry.getKey();
                        i++;
                       // Log.d("Transition",""+entry.getKey()+"-"+entry.getValue());

                    }*/

                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, domain);
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
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }
//    String selected = spinner.getSelectedItem().toString();

    private void startRegister() {
        final String name=mNameFeild.getText().toString().trim();
        final String Email=mEmail.getText().toString().trim();
        final String Password=mPassword.getText().toString().trim();
        String Repassword=mRepassword.getText().toString().trim();
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(Password)&&!TextUtils.isEmpty(Repassword))
        {
            if(!Password.equals(Repassword))
            {
                //Log.d("%s ",Password);
                //Log.d("%s ",Repassword);
                Toast.makeText(RegisterActivity.this,"Both the passwords don't match",Toast.LENGTH_LONG).show();
            }
            else if(Password.length()<8)
            {
                Toast.makeText(RegisterActivity.this,"Password strength must be more than 8 characters",Toast.LENGTH_LONG).show();
            }
            else
            {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(name))
                        {
                            Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            //mProgress.setMessage("Signing up");

                           // mProgress.show();
                            // mAuth.cre

                            if (mAuth == null) {
                                Log.d("Hey", "--------------");
                                return;
                            }
                            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Log.d("Check","------------");
                                        String user_id = mAuth.getCurrentUser().getUid();
                                        DatabaseReference current_user_db = mDatabase.child(user_id);
                                        current_user_db.child("name").setValue(name);
                                        current_user_db.child("Email_id").setValue(Email);
                                        current_user_db.child("image").setValue("default");
                                        if(position==0)
                                        {
                                            position=1;
                                        }
                                        current_user_db.child("Domain").setValue(position);

                                        Log.d("domainx",""+position);
                                        mDatabaseDomain.child(domain[position]).child(user_id).setValue(0);

                                      //  mProgress.dismiss();
                                        Intent mainIntent;
                                        switch(position)
                                        {
                                            case 0:
                                                mainIntent = new Intent(RegisterActivity.this, Register2Activity.class);
                                                Log.d("CHECK__","_____");
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;
                                            case 1:
                                                mainIntent = new Intent(RegisterActivity.this, Register2Activity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;
                                            case 2:
                                                mainIntent = new Intent(RegisterActivity.this, HRFirmActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;
                                            case 3:
                                                mainIntent = new Intent(RegisterActivity.this, HRFirmActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;
                                            case 4:
                                                mainIntent = new Intent(RegisterActivity.this,SponserRegisterActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;
                                            case 5:
                                                mainIntent = new Intent(RegisterActivity.this, SMEActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                mainIntent.putExtra("senddata",domain[position]);
                                                mainIntent.putExtra("tag",user_id);
                                                startActivity(mainIntent);
                                                break;

                                        }


                                    }
                                    else {
                                        mProgress.dismiss();
                                        Log.d("Check","Unsuccesful");
                                    }
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
        else
        {
            Toast.makeText(RegisterActivity.this,"None of the streams should remain empty",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
