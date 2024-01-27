package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class CustomerLogin extends AppCompatActivity {

    private Button customer_login ;
    private Button register_login ;

    private Button customer_create_account ;
    private TextView create_link;
    private TextView customer_status;

    private EditText email_customer;
    private EditText password_customer;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        customer_login = (Button) findViewById(R.id.loginbtn);
        create_link = (TextView) findViewById(R.id.customer_create);
        customer_status = (TextView) findViewById(R.id.customer_status);
        register_login = (Button) findViewById(R.id.registerbtn);

        email_customer = (EditText) findViewById(R.id.login_email);
        password_customer = (EditText) findViewById(R.id.login_Password);
        loadingbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        register_login.setVisibility(View.INVISIBLE);
        register_login.setEnabled(false);

        create_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_login.setVisibility(View.INVISIBLE);
                create_link.setVisibility(View.INVISIBLE);
                customer_status.setText("REGISTER");

                register_login.setVisibility(View.VISIBLE);
                register_login.setEnabled(true);

            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = email_customer.getText().toString();
                String password  = password_customer.getText().toString();

                RegisterCustomer(email,password);

            }
        });

        customer_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = email_customer.getText().toString();
                String password  = password_customer.getText().toString();

                SignInCustomer(email,password);
            }
        });
    }

    private void SignInCustomer(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(CustomerLogin.this, "please write email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(CustomerLogin.this, "please write password", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingbar.setTitle("Customer Login");
            loadingbar.setMessage("please wait , while are checking your credentials");
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CustomerLogin.this, "customer Login Successfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                            }
                            else{
                                Toast.makeText(CustomerLogin.this, "Customer Login unsuccessfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }

    private void RegisterCustomer(String email, String password) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(CustomerLogin.this, "please write email", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(CustomerLogin.this, "please write password", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingbar.setTitle("Customer Registration");
            loadingbar.setMessage("please wait , while registering your data");
            loadingbar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CustomerLogin.this, "customer Register Successfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                            else{
                                Toast.makeText(CustomerLogin.this, "Customer Register unsuccessfull", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });
        }
    }
}