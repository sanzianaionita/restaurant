package com.example.restaurant18;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restaurant18.ui.login.LoginFragment;
import com.example.restaurant18.ui.signup.SignupFragment;

public class LoginSignupAdapter extends FragmentPagerAdapter {

    private Context context;
    int numberOfTabs;

    public LoginSignupAdapter(FragmentManager fragmentManager, Context context, int numberOfTabs)
    {
        super(fragmentManager);
        this.context = context;
        this.numberOfTabs = numberOfTabs;
    }

    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
