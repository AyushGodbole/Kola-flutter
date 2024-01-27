package com.example.app;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogin extends AppCompatActivity {

    private Button driver_login ;
    private Button register_login ;

    private Button driver_create_account ;
    private TextView create_link;
    private TextView driver_status;

    private EditText email_driver;
    private EditText password_driver;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        driver_login = (Button) findViewById(R.id.driverLogin_button);
        create_link = (TextView) findViewById(R.id.register_link);
        driver_status = (TextView) findViewById(R.id.driver_staus);
        register_login = (Button) findViewById(R.id.driverregister_button);
        loadingbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        email_driver = (EditText) findViewById(R.id.driver_email);
        password_driver = (EditText) findViewById(R.id.driver_Password);


        register_login.setVisibility(View.INVISIBLE);
        register_login.setEnabled(false);

        create_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driver_login.setVisibility(View.INVISIBLE);
                create_link.setVisibility(View.INVISIBLE);
                driver_status.setText("REGISTER");

                register_login.setVisibility(View.VISIBLE);
                register_login.setEnabled(true);

            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = email_driver.getText().toString();
                String password  = password_driver.getText().toString();

                RegisterDriver(email,password);

            }
        });

        driver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = email_driver.getText().toString();
                String password  = password_driver.getText().toString();

                LoginDriver(email,password);
            }
        });

    }

    private void LoginDriver(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLogin.this, "please write email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLogin.this, "please write password", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingbar.setTitle("Driver Login");
            loadingbar.setMessage("please wait , while we are checking your credentials");
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(DriverLogin.this, "Driver Logged in Successfully", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                                Intent driverintent = new Intent(DriverLogin.this,DriverMap.class);
                                startActivity(driverintent);
                            }
                            else{
                                Toast.makeText(DriverLogin.this, "Driver Login unsuccessfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }

    private void RegisterDriver(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(DriverLogin.this, "please write email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(DriverLogin.this, "please write password", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingbar.setTitle("Driver Registration");
            loadingbar.setMessage("please wait , while registering your data");
            loadingbar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(DriverLogin.this, "Driver Register Successfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent driverintent = new Intent(DriverLogin.this,DriverMap.class);
                                startActivity(driverintent);
                            }
                            else{
                                Toast.makeText(DriverLogin.this, "Driver Register unsuccessfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }
}
