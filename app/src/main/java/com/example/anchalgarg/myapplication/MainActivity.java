package com.example.anchalgarg.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
   //i private RecyclerView mBlogList;
    private DatabaseReference mReference;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean mProcessLike=false;
    private TextView mHeader;
    private boolean flag = true;
    private FrameLayout fr;
    Front_Page mWelcome=new Front_Page();
    FragmentManager mananger;
    FragmentTransaction trans;
    private SharedPreferences mPrefs ;

    private String user_ID;
    private SharedPreferences mPrefs1;
    String name = "Choose the desired Option";
    String UserDomain=null;
    SharedPreferences sharedPreference;

    private ListView mListView;
    //private RecyclerView mRecycler;
    private ArrayList<String> lists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flag = false;/*
        fr = (FrameLayout)findViewById(R.id.fragment_container);
        fr.addView(mWelcome);
        mWelcome.show(mananger," hello");
        mananger=getSupportFragmentManager();
        trans = mananger.beginTransaction();
        trans.remove(mWelcome);
        trans.commit();
        final Timer timer1 = new Timer();
        final TimerTask task1 = new TimerTask() {
            @Override
            public void run() {

                fr.setVisibility(View.INVISIBLE);
            }
        };

        timer1.schedule(task1,2000);*/
        calling();
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Blog,RowHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, RowHolder>(

                Blog.class,
                R.layout.profile_row,
                RowHolder.class,
                mReference
        ) {
            @Override
            protected void populateViewHolder(RowHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
    }
    /* i
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Blog,BlogViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mReference
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                final String postKey=getRef(position).toString();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setLikeBtn(postKey);
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsername(model.getUsername());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(MainActivity.this,postKey,Toast.LENGTH_LONG).show();
                    }
                });
                viewHolder.mLikeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcessLike = true;

                        mDatabaseLike.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (mProcessLike) {
                                    if (dataSnapshot.child(postKey).hasChild(mAuth.getCurrentUser().getUid())) {
                                        mDatabaseLike.child(postKey).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        mProcessLike = false;
                                    } else {
                                        mDatabaseLike.child(postKey).child(mAuth.getCurrentUser().getUid()).setValue("IDK");
                                        mProcessLike = false;

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
*/

    private void checkUserExist() {
        if(mAuth.getCurrentUser()!=null)
        {
            final String UserId=mAuth.getCurrentUser().getUid();
            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(UserId))
                {
                    //Intent mainIntent=new Intent(MainActivity.this,SetupActivity.class);
                    //mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(mainIntent);
                }
                else
                {
                   name= dataSnapshot.child(UserId).child("name").getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"ERROR ERROR ERROR",Toast.LENGTH_LONG).show();
            }
            });
        }
    }
    public static class RowHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView PostTitle;

        public RowHolder(View itemView) {
            super(itemView);
            mView = itemView;
            PostTitle = (TextView) mView.findViewById(R.id.fieldName);

        }

        public void setTitle(String title) {
            //TextView PostTitle=(TextView) mView.findViewById(R.id.title);
            PostTitle.setText(title);
        }
        public void setImage(Context image, String ctx)
        {
            ImageView postImage=(ImageView)mView.findViewById(R.id.imageBtn);
            Picasso.with(image).load(ctx).into(postImage);
        }
    }

/*
    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView PostTitle;
        ImageButton mLikeBtn;
        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mDatabaseLike=FirebaseDatabase.getInstance().getReference().child("Likes");
            mAuth=FirebaseAuth.getInstance();
            mDatabaseLike.keepSynced(true);
            PostTitle=(TextView) mView.findViewById(R.id.title);
            PostTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("MainActivity","hello brother");
                    mLikeBtn=(ImageButton)mView.findViewById(R.id.likeBtn);
                }
            });
        }
        public void setLikeBtn(final String postkey)
        {
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(postkey).hasChild(mAuth.getCurrentUser().getUid())) {
                        mLikeBtn.setImageResource(R.mipmap.like);

                    } else {
                        mLikeBtn.setImageResource(R.mipmap.ic_thumb_up_black_24dp);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setTitle(String title)
        {
            //TextView PostTitle=(TextView) mView.findViewById(R.id.title);
            PostTitle.setText(title);
        }
        public void setDesc(String desc)
        {
            TextView PostDesc=(TextView) mView.findViewById(R.id.description);
            PostDesc.setText(desc);
        }
        public void setImage(Context image, String ctx)
        {
            ImageView postImage=(ImageView)mView.findViewById(R.id.imageButton);
            Picasso.with(image).load(ctx).into(postImage);
        }
        public void setUsername(String username)
        {

            TextView PostUsername=(TextView) mView.findViewById(R.id.username);
            PostUsername.setText(username);
        }

    }
*/

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





    public static class SharedPreferencesManager {

        private static final String APP_SETTINGS = "APP_SETTINGS";


        // properties
        private static final String SOME_STRING_VALUE = "SOME_STRING_VALUE";
        private static final String SOME_STRING_VALUE1 = "SOME_STRING_VALUE1";
        // other properties...


        private SharedPreferencesManager() {}

        private static SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
        }

        public static String getSomeStringValue(Context context) {
            return getSharedPreferences(context).getString(SOME_STRING_VALUE , null);
        }
        public static String getSomeStringValue1(Context context) {
            return getSharedPreferences(context).getString(SOME_STRING_VALUE1 , null);
        }

        public static void setSomeStringValue(Context context, String newValue , String newValue1) {
            final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putString(SOME_STRING_VALUE , newValue);
            editor.putString(SOME_STRING_VALUE1 , newValue1);
            editor.commit();
        }

        // other getters/setters
    }

    private void calling(){
        UserDomain=getIntent().getStringExtra("senddata");
        user_ID=getIntent().getStringExtra("tag");

        if(UserDomain!=null) {
            sharedPreference = getSharedPreferences("Impdata", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor.putString("USerId", user_ID);
            editor.putString("UserDomain", UserDomain);
            editor.commit();
        }
        else
        {
            sharedPreference=getSharedPreferences("Impdata",Context.MODE_PRIVATE);
            user_ID=sharedPreference.getString("USerId","Hey");
            UserDomain=sharedPreference.getString("UserDomain","Hey");

        }

        mAuth=FirebaseAuth.getInstance();

        mListView=(ListView)findViewById(R.id.DomainList);



        Log.d("YES","MainActivity-- "+ user_ID+"  "+UserDomain);

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent LoginIntent=new Intent(MainActivity.this,LoginActivity.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginIntent.putExtra("senddata",UserDomain);
                    LoginIntent.putExtra("tag",user_ID);
                    startActivity(LoginIntent);
                }
                else
                {
                   /* if(user_ID==null&&mPrefs!=null)
                    {
                        Map<String, ?> allEntries = mPrefs.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            user_ID = entry.getKey();
                            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                        }


                    }
                    if(UserDomain==null&&mPrefs1!=null)
                    {
                        Map<String, ?> allEntries = mPrefs1.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            UserDomain = entry.getKey();
                            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                        }
                    }*/

                    if(user_ID==null){
                        user_ID = SharedPreferencesManager.getSomeStringValue(getApplicationContext());
                        UserDomain = SharedPreferencesManager.getSomeStringValue1(getApplicationContext());

                    }

                }
            }
        };
        mReference= FirebaseDatabase.getInstance().getReference().child("Domain");
        mDatabaseUsers=FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);
        mHeader=(TextView)findViewById(R.id.header1);
        mHeader.setText(name);
        // mRecycler=(RecyclerView)findViewById(R.id.recylerView);
        //mRecycler.setHasFixedSize(true);
        //mRecycler.setLayoutManager(new LinearLayoutManager(this));
        //i  mBlogList=(RecyclerView)findViewById(R.id.list_blog);
        //i mBlogList.setHasFixedSize(true);
        //i mBlogList.setLayoutManager(new LinearLayoutManager(this));
        checkUserExist();

        if(UserDomain!=null) {

            if (UserDomain.equals("Founder") || UserDomain.equals("Co-Founder")) {

                lists.add("Sponsors");
                lists.add("HR firms");
                lists.add("Subject matter experts");
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);
            } else if (UserDomain.equals("Sponser")) {
                lists.add("HR firms");
                lists.add("Startups");
                lists.add("Subject matter experts");
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);

            } else if (UserDomain.equals("HR_firm")) {

                lists.add("Startups");
                lists.add("Subject matter experts");
                //lists.add("Subject matter experts");
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);
            } else if (UserDomain.equals("Subject-matter")) {

                lists.add("Startups");
                lists.add("HR_firm");
                //   lists.add("Hr firms");
                //  lists.add("Subject matter experts");
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);
            } else {

                lists.add("Startups");
                //    lists.add("Hr firms");
                //   lists.add("Subject matter experts");
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);
            }
        }

        /*
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot domainSnapshot: dataSnapshot.getChildren()){
                    if(!domainSnapshot.getKey().equals(UserDomain)){

                        lists.add(domainSnapshot.getKey().toString());
                    }

                }
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lists);
                mListView.setAdapter(itemsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lists.get(position);
                if(s.equals("Sponsors")){
                    Intent LoginIntent=new Intent(MainActivity.this,ListOfSponsers.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginIntent.putExtra("senddata",UserDomain);
                    LoginIntent.putExtra("tag",user_ID);
                    startActivity(LoginIntent);

                }else if(s.equals("HR firms")){

                    Intent LoginIntent=new Intent(MainActivity.this,HRFirmListActivity.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginIntent.putExtra("senddata",UserDomain);
                    LoginIntent.putExtra("tag",user_ID);

                    startActivity(LoginIntent);


                }else if(s.equals("Startups")){

                    Intent LoginIntent = new Intent(MainActivity.this, Startuplist.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginIntent.putExtra("senddata", UserDomain);
                    LoginIntent.putExtra("tag", user_ID);
                    startActivity(LoginIntent);

                }else if(s.equals("Subject matter experts")){
                    Intent LoginIntent = new Intent(MainActivity.this,SMEListActivity.class);
                    LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginIntent.putExtra("senddata", UserDomain);
                    LoginIntent.putExtra("tag", user_ID);
                    startActivity(LoginIntent);

                }
            }
        });




    }
}
