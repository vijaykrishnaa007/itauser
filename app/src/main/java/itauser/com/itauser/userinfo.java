package itauser.com.itauser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class userinfo extends AppCompatActivity
{
    TextView name,rollno,email;
    EditText phoneno;Button save;
    FirebaseDatabase data;
    DatabaseReference ref;
    ProgressDialog load;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.userinfo);
        name = findViewById(R.id.name);
        rollno = findViewById(R.id.rollno);
        save = findViewById(R.id.save);
        email = findViewById(R.id.email);
        data = FirebaseDatabase.getInstance();
        load = new ProgressDialog(this, R.style.loadstyle);
        phoneno = findViewById(R.id.phoneno);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        load.setMessage("Wait until your profile is loaded");
        load.show();

        FirebaseDatabase.getInstance().getReference().child("USERS").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String array[] = dataSnapshot.getValue().toString().split("-");
                    name.setText(array[0]);
                    rollno.setText(array[1]);
                    email.setText(array[2]);
                    phoneno.setText(array[3]);
                    load.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        phoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno.setCursorVisible(true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneno1, name1, rollno1, email1;
                name1 = name.getText().toString();
                rollno1 = rollno.getText().toString();
                email1 = email.getText().toString();
                phoneno1 = phoneno.getText().toString();
                if (phoneno1.isEmpty()) {
                    Toast.makeText(userinfo.this, "Enter your phone no.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (phoneno1.length() != 10) {
                    Toast.makeText(userinfo.this, "Mobile No. must have 10 digits", Toast.LENGTH_LONG).show();
                    return;
                }
                ref = data.getReference().child("USERS").child(user.getUid());
                ref.setValue(name1 + "-" + rollno1 + "-" + email1 + "-" + phoneno1);
                load.setMessage("Wait until we update the details");
                startActivity(new Intent(userinfo.this, MainActivity.class));
            }
                });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(userinfo.this,MainActivity.class));
    }
}
