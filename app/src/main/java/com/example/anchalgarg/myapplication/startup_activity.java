package com.example.anchalgarg.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class startup_activity extends AppCompatActivity {

    private DatabaseReference mDatabaseSubDomain;
    private DatabaseReference mDatabaseManpwr;
    private Button mCompleteBtn;
    ArrayList<String> selectedItems=new ArrayList<>();
    private Spinner mSpinner;
    int i=0;
    int position;

    int len=0;
    String[] manpwr = null;
    String[] tech_area=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_activity);
        mSpinner=(Spinner)findViewById(R.id.sub_domain_spinner);
        final String s = getIntent().getStringExtra("tag");
        final String doMain= getIntent().getStringExtra("senddata");
        Log.d("YES!!","Startup activity--"+s);
        mCompleteBtn=(Button)findViewById(R.id.reg_complete_btn);
        final ListView lv1=(ListView)findViewById(R.id.list_view);
        lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mDatabaseManpwr=FirebaseDatabase.getInstance().getReference().child("Manpwr");
        mDatabaseManpwr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                manpwr=new String[(int) dataSnapshot.getChildrenCount()];
                len= (int) dataSnapshot.getChildrenCount();
                i=0;
                Log.d("inhere","--"+len);

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    manpwr[i] =domainSnapshot.getKey();
                    i++;
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(startup_activity.this,R.layout.checkboxlayout,R.id.txt_lan,manpwr);
                lv1.setAdapter(adapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem=((TextView)view).getText().toString();
                        if(selectedItems.contains(selectedItem))
                        {
                            selectedItems.remove(selectedItem);
                        }
                        else
                        {
                            selectedItems.add(selectedItem);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseSubDomain= FirebaseDatabase.getInstance().getReference().child("Tech_Area");
        mDatabaseSubDomain.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // HashMap<String,Integer> domainName=new HashMap<String, Integer>();
                //  domainName = (HashMap<String,Integer>)domainSnapshot.getValue(HashMap.class);

                tech_area=new String[(int) dataSnapshot.getChildrenCount()];
                len= (int) dataSnapshot.getChildrenCount();
                i=0;
                Log.d("inhere","--"+len);

                for (DataSnapshot domainSnapshot: dataSnapshot.getChildren()) {
                    tech_area[i] =domainSnapshot.getKey();
                    i++;
                }
                ArrayAdapter<String> domainsAdapter = new ArrayAdapter<String>(startup_activity.this, android.R.layout.simple_spinner_item,tech_area);
                domainsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(domainsAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                position=i;
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent mainIntent=new Intent(startup_activity.this,MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("senddata",doMain);
                mainIntent.putExtra("tag",s);
                startActivity(mainIntent);

            }
        });
        //Log.d("Manpower",""+manpwr.length);
       /* ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.checkboxlayout,manpwr);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem))
                {
                    selectedItems.remove(selectedItem);
                }
                else
                {
                    selectedItems.add(selectedItem);
                }
            }
        });*/
        /*ArrayAdapter<String> adapter=new ArrayAdapter<String>(startup_activity.this,android.R.layout.simple_list_item_single_choice, manpwr);
        setListAdapter(adapter);

        ListView lv=getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=(String) parent.getItemAtPosition(position);
            }
        });*/
        /*LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.linear_layout);

            // create radio button
            final RadioButton[] rb = new RadioButton[len];
            //RadioGroup rg = new RadioGroup(this);
            //rg.setOrientation(RadioGroup.VERTICAL);
            for (int i = 0; i < len; i++) {
                rb[i] = new RadioButton(this);
              //  rg.addView(rb[i]);
                rb[i].setText(manpwr[i]);
                mLinearLayout.addView(rb[i]);
            }

*/

    }

}
