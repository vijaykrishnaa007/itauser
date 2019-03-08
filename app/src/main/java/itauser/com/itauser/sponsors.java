package itauser.com.itauser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sponsors extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Profile1> list;
    MyAdapter adapter;
    FirebaseAuth auth;
    FirebaseUser user;
    private ProgressDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
                recyclerView = findViewById(R.id.myRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                reference = FirebaseDatabase.getInstance().getReference().child("Sponsors");
                load = new ProgressDialog(this, R.style.loadstyle);
                load.setTitle("Loading");
                load.setMessage("Wait for few seconds.");
        load.setCanceledOnTouchOutside(false);
        load.setCancelable(false);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
                if(isOnline())
                {
                    load.show();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                list = new ArrayList<Profile1>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Profile1 p = dataSnapshot1.getValue(Profile1.class);
                                    list.add(p);
                                }
                                adapter = new MyAdapter(sponsors.this, list);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(sponsors.this, "No sponsors are present currently.", Toast.LENGTH_LONG).show();
                            }
                            load.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            load.dismiss();
                            Toast.makeText(sponsors.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(sponsors.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
                    if(user!=null) {
                        startActivity(new Intent(sponsors.this, MainActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(sponsors.this, mainpage_start.class));
                    }
                }




            }
            public boolean isOnline()
            {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
                else
                    return false;
            }

            @Override
            public void onBackPressed() {
        if(user!=null)
        {
            startActivity(new Intent(sponsors.this,MainActivity.class));
        }
        else
        {
            startActivity(new Intent(sponsors.this,mainpage_start.class));
        }

            }
        }
