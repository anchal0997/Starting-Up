package com.example.anchalgarg.businessdomain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button mAdd;
    private ListView mList;
    private EditText mBusinssDomain;
    private DatabaseReference mDatabaseRef;
    private ImageButton mAddSubDomain;
    private EditText mSubDomain;
    private int i=0;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private static final String   ITEM_KEY   = "key";
   // ArrayList<HashMap<String, String>>   list= new ArrayList<HashMap<String, String>>();
    //private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdd=(Button)findViewById(R.id.button2);
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Business Domain");
        mBusinssDomain=(EditText)findViewById(R.id.editText2);
        mAddSubDomain=(ImageButton)findViewById(R.id.AddBtn);
        mSubDomain=(EditText)findViewById(R.id.subDomain);
      //  mList=(ListView)findViewById(R.id.SUbDomainList);
       // mList.setHasFixedSize(true);
        //mList.setLayoutManager(new LinearLayoutManager(this));
       /* adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        mList.setAdapter(adapter);

*/
       // ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.expandable_list_content);
     //   mList.setAdapter(domainsAdapter);

       /* mAddSubDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mList.addView(vi.inflate(R.layout.plain_text_subdomain, null));


            }
        });*/

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String domain=mBusinssDomain.getText().toString().trim();
                String sub=mSubDomain.getText().toString().trim();
                if(TextUtils.isEmpty(domain))
                {
                    Toast.makeText(MainActivity.this,"Fill all the fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mDatabaseRef.child(domain).setValue(0);
                    mDatabaseRef.child(domain).child(sub).setValue(0);
                    Toast.makeText(MainActivity.this,""+mDatabaseRef,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}
