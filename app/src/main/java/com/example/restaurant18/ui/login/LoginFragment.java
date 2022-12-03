package com.example.restaurant18.ui.login;

import static java.util.UUID.randomUUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.LoginSignupActivity;
import com.example.restaurant18.MainActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonLoginAsGuest;
    private float v=0;

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.178:1521:xe";
    private static final String USERNAME = "CTXSYS";
    private static final String PASSWORD = "CTXSYS";
    private Connection connection;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LoginSignupActivity loginSignupActivity = (LoginSignupActivity) getActivity();
        User user = loginSignupActivity.getUser();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail = root.findViewById(R.id.et_loginEmail);
        editTextPassword = root.findViewById(R.id.et_loginPassword);
        buttonLogin = root.findViewById(R.id.b_login);
        buttonLoginAsGuest = root.findViewById(R.id.b_loginAsGuest);

        editTextEmail.setTranslationX(800);
        editTextPassword.setTranslationX(800);
        buttonLogin.setTranslationX(800);
        buttonLoginAsGuest.setTranslationX(800);

        editTextEmail.setAlpha(v);
        editTextPassword.setAlpha(v);
        buttonLogin.setAlpha(v);
        buttonLoginAsGuest.setAlpha(v);

        editTextEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        editTextPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        buttonLoginAsGuest.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(550).start();
        buttonLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                Login(email, password, user);
                if (!email.isEmpty() && !password.isEmpty())
                    Login(email, password, user);
                else
                {
                    if (email.isEmpty())
                        editTextEmail.setError("Please insert your email address");
                    if (password.isEmpty())
                        editTextPassword.setError("Please insert your password");
                }
            }
        });

        buttonLoginAsGuest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginAsGuest();
            }
        });

        return root;
    }

    private void Login(final String email, final String password, User user)
    {
        try
        {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.getUserByCredentials(email,password);

            if(user == null)
            {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                editTextEmail.setText(null);
                editTextPassword.setText(null);
            }
            else
            {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                getActivity().finish();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Toast.makeText(getContext(), "Invalid credentials or user doesn't exits in database", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginAsGuest()
    {
        try
        {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            String uuid = String.valueOf(randomUUID());
            intent.putExtra("nume", uuid);
            intent.putExtra("email", "");
            intent.putExtra("prenume", "Guest");
            intent.putExtra("apelativ", "Mr");
            intent.putExtra("guest", true);
            startActivity(intent);
            getActivity().finish();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Couldn't logged you in as a guest", Toast.LENGTH_SHORT).show();
        }
    }
}
