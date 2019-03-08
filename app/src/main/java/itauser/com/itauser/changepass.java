package itauser.com.itauser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changepass extends AppCompatActivity {
    private Button change1;
    private EditText newpass11,newpass12;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressDialog load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
                change1=findViewById(R.id.changeid1);
                newpass11=findViewById(R.id.changepassid11);
                newpass12=findViewById(R.id.changepassid12);
                user=auth.getInstance().getCurrentUser();
                load=new ProgressDialog(this,R.style.loadstyle);
                change1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        load.setMessage("Please wait for few seconds.");
                        load.show();
                        String pass1=newpass11.getText().toString();
                        String pass2=newpass12.getText().toString();
                        if(pass1.isEmpty()&&pass2.isEmpty()) {
                            load.dismiss();
                            Toast.makeText(changepass.this,"Enter both the fields",Toast.LENGTH_SHORT).show();
                        }

                        else if(pass1.equals(pass2)) {
                            user.updatePassword(pass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(isOnline()) {
                                        if (task.isSuccessful()) {

                                            load.dismiss();
                                            Toast.makeText(changepass.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(changepass.this, MainActivity.class));
                                        } else {
                                            load.dismiss();
                                            Toast.makeText(changepass.this, "Password update not successfull. Please Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        load.dismiss();
                                        Toast.makeText(changepass.this,"Check your internet connection",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else if(pass1.isEmpty()||pass2.isEmpty())
                        {
                            load.dismiss();
                            Toast.makeText(changepass.this,"Enter all the fields given.",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            load.dismiss();
                            Toast.makeText(changepass.this,"Enter the same password in both the fields.",Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(changepass.this,MainActivity.class));
    }
}



