package itauser.com.itauser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class contact extends AppCompatActivity {
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        user= FirebaseAuth.getInstance().getCurrentUser();
    }

    public void onBackPressed()
    {
        if(user!=null)
        {
            startActivity(new Intent(contact.this, MainActivity.class));
        }
        else
        {
            startActivity(new Intent(contact.this, mainpage_start.class));
        }
    }
}
