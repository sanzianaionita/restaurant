package com.example.restaurant18.ui.profile_settings;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.restaurant18.MainActivity;
import com.example.restaurant18.R;
import com.example.restaurant18.User;
import com.example.restaurant18.databinding.FragmentHomeBinding;
import com.example.restaurant18.databinding.FragmentProfileSettingsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProfileSettingsFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButtonMr, radioButtonMrs;
    private EditText editTextEmail, editTextPhone, editTextAddress, editTextFirstName, editTextLastName, editTextBirthDate;
    private Button buttonSaveChanges;
    private User user;

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
        editTextPhone = root.findViewById(R.id.et_profileSettingsPhone);
        editTextAddress = root.findViewById(R.id.et_profileSettingsAddress);
        editTextBirthDate = root.findViewById(R.id.et_profileSettingsBirthDate);
        buttonSaveChanges = root.findViewById(R.id.b_profileSettingsSave);
        user = ((MainActivity)getActivity()).getUser();

        if(user.getSex().equals("Mr"))
            radioButtonMr.setChecked(true);
        else
            radioButtonMrs.setChecked(true);
        editTextFirstName.setText(user.getPrenume());
        editTextLastName.setText(user.getNume());
        editTextEmail.setText(user.getEmail());
        editTextPhone.setText(user.getPhoneNumber());
        editTextBirthDate.setText(user.getBirthDate());
        editTextAddress.setText(user.getAddress());


        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v)
            {
                if(!emptyFields())
                {
                    if(checkIfBirthDateIsValid())
                    {
                        if(validatePhoneNumber())
                        {
                            String firstName, lastName, appellative, email, phoneNumber, birthDate, address;
                            if(radioButtonMr.isChecked())
                                appellative = "Mr";
                            else
                                appellative = "Mrs";
                            firstName = editTextFirstName.getText().toString().trim();
                            lastName = editTextLastName.getText().toString().trim();
                            email = editTextEmail.getText().toString().trim();
                            phoneNumber = editTextPhone.getText().toString().trim();
                            birthDate = editTextBirthDate.getText().toString().trim();
                            address = editTextAddress.getText().toString().trim();
                            SaveChanges(lastName,firstName,appellative,
                                    address,phoneNumber,birthDate,user);
                        }
                        else
                            editTextPhone.setError("Please enter a valid phone number");
                    }
                    else
                        editTextBirthDate.setError("Please enter a valid date");
                }
                else
                {
                    if(editTextEmail.getText().toString().isEmpty())
                        editTextEmail.setError("Please insert your email address");
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
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
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

    public boolean validatePhoneNumber()
    {
        if(editTextPhone.getText().toString().length()>0 && editTextPhone.getText().toString().length()<10)
            return false;
        else
            return true;
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

    private void SaveChanges(String lastName, String firstName, String sex, String address,
                             String phoneNumber, String birthDate, User user)
    {
        try
        {
            user.setNume(lastName);
            user.setPrenume(firstName);
            user.setPhoneNumber(phoneNumber);
            user.setSex(sex);
            user.setAddress(address);
            user.setBirthDate(birthDate);
            Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getActivity(), R.id.nav_profile).navigate(R.id.action_nav_profile_to_nav_home);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Couldn't save your changes", Toast.LENGTH_SHORT).show();
        }
    }
}