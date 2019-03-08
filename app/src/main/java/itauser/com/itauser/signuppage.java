package itauser.com.itauser;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signuppage extends AppCompatActivity
{
    AutoCompleteTextView email,name,rollno,phoneno;
    EditText password;
    EditText confirmpassword;
    SwipeButton signup;
    ProgressDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        rollno=findViewById(R.id.rollno);
        phoneno=findViewById(R.id.phoneno);
        confirmpassword=findViewById(R.id.confirmpassword);
        load=new ProgressDialog(this,R.style.loadstyle);
        signup=findViewById(R.id.email_sign_in_button);
        load.setMessage("Wait until the registration process is completed.");
        load.setCanceledOnTouchOutside(false);
        load.setCancelable(false);
        signup.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {

                load.show();
                String name1="",rollno1="",phoneno1="",emailid="",p1,p2;
                name1=name.getText().toString();
                rollno1=rollno.getText().toString();
                phoneno1=phoneno.getText().toString();
                emailid=email.getText().toString();
                p1= password.getText().toString();
                p2=confirmpassword.getText().toString();
                if(name1.isEmpty()  ||  rollno1.isEmpty() || phoneno1.isEmpty() ||  emailid.isEmpty() || p1.isEmpty() || p2.isEmpty())
                {
                    load.dismiss();
                    Toast.makeText(signuppage.this,"Enter all the fields.",Toast.LENGTH_LONG).show();

                    return;
                }
                else if(phoneno1.length() != 10)
                {
                    load.dismiss();
                    Toast.makeText(signuppage.this, "Mobile No. must have 10 digits", Toast.LENGTH_LONG).show();
                    return;
                }
                String passcode="",confirmpasscode="",emailid1="";
                passcode=password.getText().toString();
                confirmpasscode=confirmpassword.getText().toString();
                emailid1=email.getText().toString();
                if(!passcode.equals(confirmpasscode))
                {
                    load.dismiss();
                    Toast.makeText(signuppage.this,"PASSWORDS do not match",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(passcode.length()<8)
                {
                    load.dismiss();
                    Toast.makeText(signuppage.this,"Password should contain atleast 8 characters.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(isOnline())
                {
                        final String finalName = name1;
                        final String finalRollno = rollno1;
                        final String finalEmailid = emailid;
                        final String finalPhoneno = phoneno1;
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailid1, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    FirebaseDatabase.getInstance().getReference().child("USERS").child(user.getUid()).setValue(finalName + "-" + finalRollno + "-" + finalEmailid + "-" + finalPhoneno);
                                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                                    FirebaseAuth.getInstance().signOut();
                                    load.dismiss();
                                    Toast.makeText(signuppage.this, "ACCOUNT CREATION SUCCESSFULL. Verify your email address.", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    load.dismiss();
                                    Toast.makeText(signuppage.this, "Email already registered.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                }
                else
                {
                    load.dismiss();
                    Toast.makeText(signuppage.this,"Check your Internet Connection",Toast.LENGTH_LONG).show();
                }
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
}
