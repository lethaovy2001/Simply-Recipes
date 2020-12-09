package com.example.simplyrecipes.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simplyrecipes.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Stack;

/**
 * Main Activity for project Simply Recipes
 * Implements fragment manager to control switching from and to different fragments: Home Page,
 * Pantry, Favorite, Shopping List
 *
 * @author Dung V Vo
 * @version 1.0 - Initial version for the Main Activity
 */

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG = "MainActivity";       // Tag to check in log statements
    FragmentManager mFragment;
    FragmentTransaction mFragmentTransaction;
    private Stack<Fragment> fragmentStack;
    /**
     * Create a Bottom Navigation listener to move between fragments
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String menuTitle = "";
                    switch (item.getItemId()) {
                        case R.id.navHome:
                            selectedFragment = new HomePageFragment();
                            menuTitle = "Home";
                            break;
                        case R.id.navPantry:
                            selectedFragment = new PantryFragment();
                            menuTitle = "Pantry";
                            break;
                        case R.id.navFavorite:
                            selectedFragment = new FavoriteFragment();
                            menuTitle = "Favorite";
                            break;
                        case R.id.navShoppingList:
                            selectedFragment = new ShoppingListFragment();
                            menuTitle = "Shopping List";
                            break;
                    }
                    replaceFragment(selectedFragment);

                    TextView toolbarTitle = findViewById(R.id.toolbar_title_text);
                    toolbarTitle.setText(menuTitle);

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate fragment manager for the main screen
        this.mFragment = getSupportFragmentManager();

        // Bottom Navigation View to switch back and forth from the main screens (fragments switch)
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Toolbar to replace action bar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = findViewById(R.id.toolbar_title_text);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_logout);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.logout_action) {
                    logoutApp();
                }
                return false;
            }
        });
    }

//    /**
//     * Pushes a Fragment onto the Fragment stack.
//     *
//     * @param fragment - Fragment to be added
//     */
//
//    private void addFragment(Fragment fragment) {
//        this.mFragmentTransaction = mFragment.beginTransaction();
//        mFragmentTransaction.add(R.id.fragment_container, fragment).addToBackStack("" + fragment.getTag());
//        mFragmentTransaction.commit();
//    }

    private void replaceFragment(Fragment fragment) {
        this.mFragmentTransaction = mFragment.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack("" + fragment.getTag());
        mFragmentTransaction.commit();
    }

//    /**
//     * Pushes a Fragment onto the Fragment stack.
//     *
//     * @param fragment - Fragment to be pushed onto Fragment stack
//     * @param add      - true if adding fragment to stack
//     */
//    public void pushFragment(Fragment fragment, boolean add) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//        if (add) {
//            this.fragmentStack.push(fragment);
//        }
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
//    }
//
//
//    /**
//     * Pops top Fragment from the stack
//     */
//    public void popFragment() {
//        if (!this.fragmentStack.isEmpty()) {
//            Fragment fragment = this.fragmentStack.elementAt(this.fragmentStack.size() - 2);
//            this.fragmentStack.pop();
//            pushFragment(fragment, false);
//        } else {
//            super.onBackPressed();
//        }
//    }

    /**
     * When user clicks on back button pressed on phone
     */
    @Override
    public void onBackPressed() {
        // Fragment fragment = mFragment.findFragmentById(R.id.fragment_container);
        super.onBackPressed();
    }

    public void logoutApp() {
        Intent intent = new Intent(this, SignInActivity.class);
        finish();
        startActivity(intent);
    }
}
