package com.example.restaurant18.ui.signup;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.LoginSignupActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SignupFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButtonMr, radioButtonMrs;
    private EditText editTextEmailSignup, editTextPasswordSignup, editTextPasswordConfirmSignup, editTextFirstName, editTextLastName, editTextBirthDate;
    private Button buttonSignUp;

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.100.34:1521:xe";
    private static final String USERNAME = "raisa";
    private static final String PASSWORD = "Sasakisan";
    private Connection connection;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LoginSignupActivity loginSignupActivity = (LoginSignupActivity) getActivity();
        User user = loginSignupActivity.getUser();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup, container, false);

        radioGroup = root.findViewById(R.id.radio_group);
        radioButtonMr = root.findViewById(R.id.rb_mr);
        radioButtonMrs = root.findViewById(R.id.rb_mrs);
        editTextEmailSignup = root.findViewById(R.id.et_signupEmail);
        editTextFirstName = root.findViewById(R.id.et_signupFirstName);
        editTextLastName = root.findViewById(R.id.et_signupLastName);
        editTextBirthDate = root.findViewById(R.id.et_signupBirthDate);
        editTextPasswordSignup = root.findViewById(R.id.et_signupPassword);
        editTextPasswordConfirmSignup = root.findViewById(R.id.et_signupPasswordConfirm);
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
                            if(validateBirthDateFormat())
                            {
                                String firstName, lastName, appellative, email, password, birthDate;
                                if(radioButtonMr.isChecked())
                                    appellative = "Mr";
                                else
                                    appellative = "Mrs";
                                firstName = editTextFirstName.getText().toString().trim();
                                lastName = editTextLastName.getText().toString().trim();
                                email = editTextEmailSignup.getText().toString().trim();
                                birthDate = editTextBirthDate.getText().toString().trim();
                                password = editTextPasswordSignup.getText().toString().trim();
                                Signup(email,password,lastName,firstName,appellative,birthDate);
                            }
                            else
                                editTextBirthDate.setError("Please enter a valid birth date");
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
                    if(editTextBirthDate.getText().toString().isEmpty())
                        editTextBirthDate.setError("Please insert your birth date");
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

    private void Signup(String email, String password, String lastName, String firstName, String appellative, String birthDate)
    {
        try
        {
            User auxUser;
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UserDAO userDAO = new UserDAO(connection);

            auxUser = userDAO.getUserByEmail(email);

            if(auxUser != null)
            {
                Toast.makeText(getContext(), "Email address "+email+" already used", Toast.LENGTH_SHORT).show();
            }
            else
            {
                System.out.println("ADAUGAREEEEEEEEEEEEEE");
                auxUser = new User(firstName, lastName, email, password, appellative, birthDate);
                if(userDAO.createUser(auxUser))
                {
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
                    editTextBirthDate.setText(null);
                    editTextEmailSignup.setText(null);
                    editTextPasswordSignup.setText(null);
                    editTextPasswordConfirmSignup.setText(null);
                    Toast.makeText(getContext(), "Sign up completed", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Sign up completed NO", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            System.out.println("ERORRRREEEEEE: "+e.getMessage());
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

    public boolean validateBirthDateFormat() {
        String date = editTextBirthDate.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if(date.isEmpty())
            return true;
        else
        {
            format.setLenient(false);
            try {
                format.parse(date);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
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
        if(editTextBirthDate.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextPasswordSignup.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextPasswordConfirmSignup.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        return isAnyFieldEmpty;
    }
}
