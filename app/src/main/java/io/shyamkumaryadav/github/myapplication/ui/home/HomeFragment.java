package io.shyamkumaryadav.github.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import io.shyamkumaryadav.github.myapplication.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        final EditText email = root.findViewById(R.id.editTextTextEmailAddress);
        final EditText password = root.findViewById(R.id.editTextTextPassword);
        final EditText number = root.findViewById(R.id.editTextPhone);
        final Button button = root.findViewById(R.id.button);
        final Button create = root.findViewById(R.id.button2);
        final Button sendotp = root.findViewById(R.id.button3);
        final TextView textView = root.findViewById(R.id.text_home);
        final String[] msg = {""};
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! email.getText().toString().isEmpty() && ! password.getText().toString().isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                msg[0] = "Auth created 200" + email.getText().toString() + " / " + password.getText().toString();
                                Toast.makeText(getContext(), msg[0],Toast.LENGTH_SHORT).show();
                                textView.setText(msg[0]);
                            }
                            else{
                                msg[0] = "Auth created 404" + email.getText().toString() + " / " + password.getText().toString();
                                Toast.makeText(getContext(), msg[0],Toast.LENGTH_SHORT).show();
                                textView.setText(msg[0]);
                            }
                        }
                    });
                }else Toast.makeText(getContext(),"Enter Email and password!", Toast.LENGTH_LONG).show();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! email.getText().toString().isEmpty() && ! password.getText().toString().isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                msg[0] = "Auth 200" + email.getText().toString() + " / " + password.getText().toString();
                                Toast.makeText(getContext(), msg[0],Toast.LENGTH_SHORT).show();
                                textView.setText(msg[0]);
                            }
                            else{
                                msg[0] = "Auth 404" + email.getText().toString() + " / " + password.getText().toString();
                                Toast.makeText(getContext(), msg[0],Toast.LENGTH_SHORT).show();
                                textView.setText(msg[0]);
                            }
                        }
                    });
                }else Toast.makeText(getContext(),"Enter Email and password!", Toast.LENGTH_LONG).show();

            }
        });

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! number.getText().toString().isEmpty()){
                    PhoneAuthProvider.verifyPhoneNumber(new PhoneAuthOptions().newBuilder(firebaseAuth)
                            .setPhoneNumber(number.getText().toString())
                            .setTimeout(5L, TimeUnit.MINUTES)
                            .setActivity(this)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                }
                            }));

                }else Toast.makeText(getContext(),"Enter Your Number!", Toast.LENGTH_LONG).show();
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("lol xd now working...");
            }
        });
        return root;
    }
}