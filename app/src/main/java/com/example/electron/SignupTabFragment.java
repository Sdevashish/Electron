package com.example.electron;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTabFragment extends Fragment {

    EditText sign_username,mobile,sign_password,confirm_password;
    Button sign_button;
    FirebaseAuth fAuth;
    float v=0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

        sign_username = root.findViewById(R.id.sign_username);
        mobile = root.findViewById(R.id.mobile);
        sign_password = root.findViewById(R.id.sign_password);
        confirm_password = root.findViewById(R.id.re_enter_password);
        sign_button = root.findViewById(R.id.sign_button);
        fAuth = FirebaseAuth.getInstance();

        sign_username.setTranslationX(800);
        mobile.setTranslationX(800);
        sign_password.setTranslationX(800);
        confirm_password.setTranslationX(800);
        sign_button.setTranslationX(1000);

        sign_username.setAlpha(v);
        mobile.setAlpha(v);
        sign_password.setAlpha(v);
        confirm_password.setAlpha(v);
        sign_button.setAlpha(v);

        sign_username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        sign_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirm_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        sign_button.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sign_username.getText().toString().trim();
                String password = sign_password.getText().toString().trim();
                String confirm_passw = confirm_password.getText().toString().trim();
                String mobile_no =mobile.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    sign_username.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    sign_password.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(confirm_passw)){
                    confirm_password.setError("Re-Enter password");
                    return;
                }
                if(TextUtils.isEmpty(mobile_no)){
                    sign_password.setError("Mobile number is required");
                    return;
                }
                if(!(isValid(email))){
                    sign_username.setError("Enter valid Email Id");
                    return;
                }
                if(password.length() < 6){
                    sign_password.setError("Too Short");
                    return;
                }
                if(!(password.equals(confirm_passw))){
                    confirm_password.setError("Password not match!");
                    return;
                }
                if (mobile_no.length() < 10 || mobile_no.length() > 10){
                    mobile.setError("Enter Valid Phone Number");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }else {
                            Toast.makeText(getActivity(), "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            private boolean isValid(String email) {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(emailRegex);
                if (email == null)
                    return false;
                return pat.matcher(email).matches();
            }
        });
        return root;
    }
}
