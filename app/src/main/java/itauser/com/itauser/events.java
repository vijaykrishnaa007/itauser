package itauser.com.itauser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class events extends AppCompatActivity
{
    GridLayout gridLayout;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog load;
    protected  void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.events);
        gridLayout = findViewById(R.id.gridlayout);
        auth=FirebaseAuth.getInstance();
        load=new ProgressDialog(this,R.style.loadstyle);
        user=auth.getCurrentUser();
        load.setMessage("Wait until the events are loaded.");
        load.setTitle("Loading");
        if (isOnline()) {
            load.show();
            FirebaseDatabase.getInstance().getReference().child("EVENTS").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    gridLayout.removeAllViews();
                    for (final DataSnapshot child : dataSnapshot.getChildren()) {
                        View add = LayoutInflater.from(events.this).inflate(R.layout.card, null);
                        ImageView imageView = add.findViewById(R.id.imageview);
                        TextView textView = add.findViewById(R.id.text);
                        LinearLayout button = add.findViewById(R.id.button);
                        Glide.with(events.this).load(child.child("DETAILS").child("imagelink").getValue().toString()).into(imageView);
                        textView.setText(child.getKey());
                        load.dismiss();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isOnline()) {
                                    transfer.description = child.child("DETAILS").child("description").getValue().toString();
                                    transfer.format = child.child("DETAILS").child("format").getValue().toString();
                                    transfer.contact = child.child("DETAILS").child("contact").getValue().toString();
                                    transfer.rules = child.child("DETAILS").child("rules").getValue().toString();
                                    transfer.faq = child.child("DETAILS").child("faq").getValue().toString();
                                    transfer.datevenue = child.child("DETAILS").child("datevenue").getValue().toString();
                                    transfer.name = child.getKey();
                                    transfer.imagelink = child.child("DETAILS").child("imagelink").getValue().toString();
                                    startActivity(new Intent(events.this, editor.class));
                                }
                                else
                                {
                                    Toast.makeText(events.this,"Check your Internet Connection",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        gridLayout.addView(add);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
load.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(events.this,"Check your Internet Connection",Toast.LENGTH_LONG).show();
            if(user!=null) {
                startActivity(new Intent(events.this, MainActivity.class));
            }
            else
            {
                startActivity(new Intent(events.this, mainpage_start.class));
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
    public void onBackPressed()
    {
        if(user!=null)
        {
            startActivity(new Intent(events.this, MainActivity.class));
        }
        else
        {
            startActivity(new Intent(events.this, mainpage_start.class));
        }
    }
}
