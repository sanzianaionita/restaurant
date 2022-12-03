package com.example.restaurant18;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.restaurant18.entity.User;
import com.google.android.material.tabs.TabLayout;

public class LoginSignupActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

        TabLayout tabLayout;
        ViewPager viewPager;
        private Context context = getBaseContext();
        private User user = new User();

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_signup);

            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));


            tabLayout = findViewById(R.id.tab_layout);
            viewPager = findViewById(R.id.view_pager);

            tabLayout.addTab(tabLayout.newTab().setText("Login"));
            tabLayout.addTab(tabLayout.newTab().setText("Signup"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final LoginSignupAdapter loginSignupAdapter = new LoginSignupAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
            viewPager.setAdapter(loginSignupAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(this);

        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

        public Context getLoginActivityContext() {
            return context;
        }

        public User getUser() {
            return user;
        }
}
