package com.example.anchalgarg.tech_area;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText mTechArea;
    private Button mAdd;
    private Button mAdd2;
    EditText mManpwr;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTechArea=(EditText)findViewById(R.id.tech_area);
        mAdd=(Button)findViewById(R.id.add);
        mManpwr=(EditText)findViewById(R.id.manpwr_feild);
        mAdd2=(Button)findViewById(R.id.add_field);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTech = mTechArea.getText().toString().trim();
                if(!TextUtils.isEmpty(newTech))
                {
                    mDatabaseRef.child("Tech_Area").child(newTech).setValue(0);
                }

            }
        });
        mAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newManpwr=mManpwr.getText().toString().trim();
                if(!TextUtils.isEmpty(newManpwr))
                {
                    mDatabaseRef.child("Manpwr").child(newManpwr).setValue(0);
                }
            }
        });

    }
}
