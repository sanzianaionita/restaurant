package com.example.restaurant18.ui.signup;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.restaurant18.LoginSignupActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButtonMr, radioButtonMrs;
    private EditText editTextEmailSignup, editTextPasswordSignup, editTextPasswordConfirmSignup, editTextFirstName, editTextLastName;
    private Button buttonSignUp;
    float v=0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LoginSignupActivity loginSignupActivity = (LoginSignupActivity) getActivity();
        User user = loginSignupActivity.getUser();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup, container, false);

        radioGroup = root.findViewById(R.id.radio_group);
        radioButtonMr = root.findViewById(R.id.mr);
        radioButtonMrs = root.findViewById(R.id.mrs);
        editTextEmailSignup = root.findViewById(R.id.email_signup);
        editTextFirstName = root.findViewById(R.id.first_name);
        editTextLastName = root.findViewById(R.id.last_name);
        editTextPasswordSignup = root.findViewById(R.id.password_signup);
        editTextPasswordConfirmSignup = root.findViewById(R.id.password_confirm_signup);
        buttonSignUp = root.findViewById(R.id.button_signup);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {
                if(!emptyFields())
                {
                    if(validateEmail())
                    {
                        if(validatePassword())
                        {
                            String firstName, lastName, appellative, email, password;
                            if(radioButtonMr.isChecked())
                                appellative = "Mr";
                            else
                                appellative = "Mrs";
                            firstName = editTextFirstName.getText().toString().trim();
                            lastName = editTextLastName.getText().toString().trim();
                            email = editTextEmailSignup.getText().toString().trim();
                            password = editTextPasswordSignup.getText().toString().trim();
                            Signup(email,password,lastName,firstName,appellative, user);
                        }
                        else
                            editTextPasswordConfirmSignup.setError("Passwords doesn't match");
                    }
                    else
                        editTextEmailSignup.setError("Please enter a valid email address");
                }
                else
                {
                    if(editTextEmailSignup.getText().toString().isEmpty())
                        editTextEmailSignup.setError("Please insert your email address");
                    if(editTextFirstName.getText().toString().isEmpty())
                        editTextFirstName.setError("Please insert your first name");
                    if(editTextLastName.getText().toString().isEmpty())
                        editTextLastName.setError("Please insert your last name");
                    if(editTextPasswordSignup.getText().toString().isEmpty())
                        editTextPasswordSignup.setError("Please insert your password");
                    if(editTextPasswordConfirmSignup.getText().toString().isEmpty())
                        editTextPasswordConfirmSignup.setError("Please insert your password");
                    if(radioGroup.getCheckedRadioButtonId() == -1)
                        Toast.makeText(getContext(), "Please select the appropriate appellative", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }

    private void Signup(final String email, final String password, String nume, String prenume, String apelativ, User user)
    {
        try
        {

            if(email.equals(user.getEmail()))
            {
                Toast.makeText(getContext(), "Email address "+email+" already used", Toast.LENGTH_SHORT).show();
            }
            else
            {
                user.setEmail(email);
                user.setNume(nume);
                user.setPrenume(prenume);
                user.setParola(password);
                user.setSex(apelativ);
                radioButtonMr.post(new Runnable() {
                    @Override
                    public void run() {
                                        radioButtonMr.setChecked(false);
                                    }
                });
                radioButtonMrs.post(new Runnable() {
                    @Override
                    public void run() {
                                        radioButtonMrs.setChecked(false);
                                    }
                });
                editTextFirstName.setText(null);
                editTextLastName.setText(null);
                editTextEmailSignup.setText(null);
                editTextPasswordSignup.setText(null);
                editTextPasswordConfirmSignup.setText(null);
                Toast.makeText(getContext(), "Sign up completed", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Couldn't sign you up", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean validateEmail()
    {
        return Patterns.EMAIL_ADDRESS.matcher(editTextEmailSignup.getText().toString().trim()).matches();
    }

    public boolean validatePassword()
    {
        return editTextPasswordSignup.getText().toString().equals(editTextPasswordConfirmSignup.getText().toString());
    }

    public boolean emptyFields()
    {
        boolean isAnyFieldEmpty = false;
        if(radioGroup.getCheckedRadioButtonId() == -1)
            isAnyFieldEmpty = true;
        if(editTextEmailSignup.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextFirstName.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextLastName.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextPasswordSignup.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextPasswordConfirmSignup.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        return isAnyFieldEmpty;
    }
}
