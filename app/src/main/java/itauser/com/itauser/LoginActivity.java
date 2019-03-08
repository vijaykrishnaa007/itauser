package itauser.com.itauser;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import itauser.com.itauser.MainActivity;
import itauser.com.itauser.R;
import itauser.com.itauser.forgotpassword;
import itauser.com.itauser.signuppage;

public class LoginActivity extends AppCompatActivity
{
    AutoCompleteTextView email;
    EditText password;
    Button signin;
    TextView signup,forgot;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.login_progress);
        forgot=findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,forgotpassword.class));
            }
        });
        signin=findViewById(R.id.email_sign_in_button);
        signup=findViewById(R.id.signup);
       if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String emailid = "", passcode = "";
                emailid = email.getText().toString();
                passcode = password.getText().toString();
                if (emailid.isEmpty() || passcode.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Enter both the email and password.", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        if(isOnline()) {
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailid, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && !FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(LoginActivity.this, "Verify your EMAIL first", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Enter correct EMAIL and PASSWORD", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Check your Internet Connection",Toast.LENGTH_LONG).show();
                        }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,signuppage.class));
            }
        });
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

    public void onBackPressed()
    {
        finish();
startActivity(new Intent(LoginActivity.this,mainpage_start.class));

    }
}
