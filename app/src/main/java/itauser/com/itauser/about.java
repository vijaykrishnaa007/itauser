package itauser.com.itauser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class about extends AppCompatActivity {
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        user=FirebaseAuth.getInstance().getCurrentUser();
    }
    public void onBackPressed()
    {
        if(user!=null)
        {
            startActivity(new Intent(about.this, MainActivity.class));
        }
        else
        {
            startActivity(new Intent(about.this, mainpage_start.class));
        }
    }
}
