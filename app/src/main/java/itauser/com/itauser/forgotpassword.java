package itauser.com.itauser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity
{
    AutoCompleteTextView email;
    Button reset;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        email=findViewById(R.id.email);
        reset=findViewById(R.id.email_sign_in_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length()>0)
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    Toast.makeText(forgotpassword.this,"EMAIL SENT",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(forgotpassword.this,"SOME ERROR OCCURED",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
