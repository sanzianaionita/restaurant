package com.example.restaurant18.ui.profile_settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.MainActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.databinding.FragmentProfileSettingsBinding;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.utils.DatabaseHandler;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProfileSettingsFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButtonMr, radioButtonMrs;
    private EditText editTextFirstName, editTextLastName, editTextBirthDate;
    private TextView editTextEmail;
    private Button buttonSaveChanges;
    private User user;
    Connection connection;

    private FragmentProfileSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileSettingsViewModel homeViewModel =
                new ViewModelProvider(this).get(ProfileSettingsViewModel.class);

        binding = FragmentProfileSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        radioGroup = root.findViewById(R.id.rg_profileSettings);
        radioButtonMr = root.findViewById(R.id.rb_profileSettingsMr);
        radioButtonMrs = root.findViewById(R.id.rb_profileSettingsMrs);
        editTextEmail = root.findViewById(R.id.et_profileSettingsEmail);
        editTextFirstName = root.findViewById(R.id.et_profileSettingsFirstName);
        editTextLastName = root.findViewById(R.id.et_profileSettingsLastName);
        editTextBirthDate = root.findViewById(R.id.et_profileSettingsBirthDate);
        buttonSaveChanges = root.findViewById(R.id.b_profileSettingsSave);
        user = ((MainActivity)getActivity()).getUser();



        if(user.getAppellative().equalsIgnoreCase("mr"))
            radioButtonMr.setChecked(true);
        else
            radioButtonMrs.setChecked(true);
        editTextFirstName.setText(user.getFirstname());
        editTextLastName.setText(user.getLastname());
        editTextEmail.setText(user.getEmail());
        editTextBirthDate.setText(user.getBirthDate());


        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {
                if(!emptyFields())
                {
                    if(checkIfBirthDateIsValid())
                    {
                        String firstName, lastName, appellative, birthDate;
                        if(radioButtonMr.isChecked())
                            appellative = "Mr";
                        else
                            appellative = "Mrs";
                        firstName = editTextFirstName.getText().toString().trim();
                        lastName = editTextLastName.getText().toString().trim();
                        birthDate = editTextBirthDate.getText().toString().trim();
                        SaveChanges(lastName,firstName,appellative,birthDate,user);
                    }
                    else
                        editTextBirthDate.setError("Please enter a valid date");
                }
                else
                {
                    if(editTextFirstName.getText().toString().isEmpty())
                        editTextFirstName.setError("Please insert your first name");
                    if(editTextLastName.getText().toString().isEmpty())
                        editTextLastName.setError("Please insert your last name");
                }
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean checkIfBirthDateIsValid() {
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
        if(editTextFirstName.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        if(editTextLastName.getText().toString().trim().isEmpty())
            isAnyFieldEmpty = true;
        return isAnyFieldEmpty;
    }

    private void SaveChanges(String lastName, String firstName, String appellative, String birthDate, User user)
    {
        try
        {

            String[] fieldsToUpdate = new String[]{"firstname", "lastname", "appellative", "date_of_birth"};
            String[] values = new String[]{firstName, lastName, appellative, birthDate};

            connection = DatabaseHandler.createDbConn();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.editUser(user.getId(), fieldsToUpdate, values);


            user.setLastname(lastName);
            user.setFirstname(firstName);
            user.setAppellative(appellative);
            user.setBirthDate(birthDate);
            Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_SHORT).show();

            ImageView imageViewUser;
            TextView textViewUserName, textViewEmail;
            MainActivity main = (MainActivity) getActivity();
            View viewHeader = main.returnView();
            imageViewUser = viewHeader.findViewById(R.id.imageViewUserLogo);
            textViewUserName = viewHeader.findViewById(R.id.textViewUserName);
            textViewEmail = viewHeader.findViewById(R.id.textViewEmail);
            if (user.getAppellative().equalsIgnoreCase("Mr"))
                imageViewUser.setImageResource(R.mipmap.png_man);
            else
                imageViewUser.setImageResource(R.mipmap.png_woman);
            textViewUserName.setText("Hello, "+user.getFirstname()+" "+user.getLastname());
            textViewEmail.setText(user.getEmail());
            //MainActivity main = (MainActivity) getActivity();

            //Navigation.findNavController(main,R.id.nav_profile).navigate(R.id.action_nav_profile_to_nav_home);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Couldn't save your changes", Toast.LENGTH_SHORT).show();
        }
    }
}