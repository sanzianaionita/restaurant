package com.example.restaurant18.ui.login;

import static java.util.UUID.randomUUID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.restaurant18.LoginSignupActivity;
import com.example.restaurant18.MainActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.User;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonLoginAsGuest;
    private float v=0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LoginSignupActivity loginSignupActivity = (LoginSignupActivity) getActivity();
        User user = loginSignupActivity.getUser();

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail = root.findViewById(R.id.email_login);
        editTextPassword = root.findViewById(R.id.password_login);
        buttonLogin = root.findViewById(R.id.button_login);
        buttonLoginAsGuest = root.findViewById(R.id.button_login_guest);

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
            if(email.equals(user.getEmail()) && password.equals(user.getParola()))
            {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("nume", user.getNume());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("prenume", user.getPrenume());
                intent.putExtra("apelativ", user.getSex());
                intent.putExtra("guest", false);
                intent.putExtra("user",user);
                startActivity(intent);
                getActivity().finish();
            }
            else
            {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                editTextEmail.setText(null);
                editTextPassword.setText(null);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Toast.makeText(getContext(), "Couldn't logged you in", Toast.LENGTH_SHORT).show();
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
            System.out.println(e.getMessage());
            Toast.makeText(getContext(), "Couldn't logged you in as a guest", Toast.LENGTH_SHORT).show();
        }
    }
}
