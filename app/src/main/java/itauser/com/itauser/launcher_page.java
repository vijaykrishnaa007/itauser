package itauser.com.itauser;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class launcher_page extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_page);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (user != null)
                {
                    startActivity(new Intent(launcher_page.this, LoginActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(launcher_page.this, mainpage_start.class));
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}
