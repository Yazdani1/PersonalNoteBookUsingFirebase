package yazdaniscodelab.personalnotebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPass;

    private Button mButtn;

    private TextView mSignin;

    //Firebase

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth=FirebaseAuth.getInstance();



        mEmail=findViewById(R.id.edittext_email);
        mPass=findViewById(R.id.edittext_password);
        mButtn=findViewById(R.id.registerbutton_xml);

        mSignin=findViewById(R.id.signinhere_xml);


        mDialog=new ProgressDialog(this);
        mButtn.setOnClickListener(this);
        mSignin.setOnClickListener(this);


    }


    private void registrTiondetails(){

        String email=mEmail.getText().toString().trim();
        String pass=mPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            mEmail.setError("Required Field..");
            return;
        }

        if (TextUtils.isEmpty(pass)){
            mPass.setError("Required Field..");
            return;
        }

        mDialog.setTitle("Please Wait.");
        mDialog.setMessage("Registration is on processing..");
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registration Successfil",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
            }
        });

    }



    @Override
    public void onClick(View view) {

        if (view==mButtn){
            registrTiondetails();
        }

        if (view==mSignin){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

}
