package com.example.anchalgarg.domain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseDomain;
    private Button mAddBtn;
    private EditText mAddText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseDomain= FirebaseDatabase.getInstance().getReference().child("Domain");
        mAddText=(EditText)findViewById(R.id.domain_feild);
        mAddBtn=(Button)findViewById(R.id.click);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDomain=mAddText.getText().toString().trim();
                DatabaseReference current_user_db = mDatabaseDomain;
                Toast.makeText(MainActivity.this,"HELLO",Toast.LENGTH_LONG).show();
                if(!TextUtils.isEmpty(newDomain))
                {
                    current_user_db.child(newDomain).setValue(0);
                }
            }
        });
    }
}
