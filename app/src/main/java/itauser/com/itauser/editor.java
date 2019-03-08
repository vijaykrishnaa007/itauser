package itauser.com.itauser;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import jp.wasabeef.richeditor.RichEditor;

public class editor extends AppCompatActivity
{
    RichEditor richEditor;
    Button enroll;
    FirebaseAuth auth;
    FirebaseUser user;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.editor);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        enroll = findViewById(R.id.enroll);
        if(user!=null)
        {
        FirebaseDatabase.getInstance().getReference().child("EVENTS").child(transfer.name).child("ROUNDS").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // if(user!=null)
                //{
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    enroll.setText("ENROLLED ALREADY");
                    enroll.setEnabled(false);
                }
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(user!=null)
                {
                    FirebaseDatabase.getInstance().getReference().child("EVENTS").child(transfer.name).child("ROUNDS").child("1").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
                }
               else
                {
                    Toast.makeText(editor.this,"You have to sign in for enrolling into events",Toast.LENGTH_LONG).show();
                }
            }
        });
        richEditor=findViewById(R.id.editor);
        richEditor.setEditorFontColor(Color.RED);
        richEditor.setInputEnabled(false);
        richEditor.setHtml("<b><u>DESCRIPTION</u></b><br /><br />"+transfer.description+"<br /><br /><b><u>FORMAT</u></b><br /><br />"+transfer.format+"<br /><br /><b><u>RULES</u></b><br /><br />"+transfer.rules+"<br /><br /><b><u>FAQ</u></b><br /><br />"+transfer.faq+"<br /><br /><b><u>DATE AND VENUE</u></b><br /><br />"+transfer.datevenue+"<br /><br /><b><u>CONTACT</u></b><br /><br />"+transfer.contact);
    }
}
