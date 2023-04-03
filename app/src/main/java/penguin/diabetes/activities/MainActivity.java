package penguin.diabetes.activities;

import android.Manifest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import penguin.diabetes.R;
import penguin.diabetes.db.DataBaseManager;

import penguin.diabetes.fragments.HistoryFragment;
import penguin.diabetes.ui.contact.EmergencyContactDialog;

import penguin.diabetes.ui.main.SectionsPagerAdapter;
import penguin.diabetes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // -------------------- SMS Permission request ---------------------------

        ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.SEND_SMS},1);

        // -------------------- Data Base Initialization ------------------------

        SQLiteDatabase appDatabase = openOrCreateDatabase("app", MODE_PRIVATE,null);
        DataBaseManager db = new DataBaseManager(appDatabase);



//        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition() == 1){
////                    PlaceholderFragment.HistoryFragment.loadHistoryList();
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 1) {
////                    PlaceholderFragment.HistoryFragment.loadHistoryList();
//                }
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.three_dots_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tdm_reload:
                try {
                    HistoryFragment.refresh();
                    Snackbar.make(binding.getRoot(), R.string.tdm_reloading, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Snackbar.make(binding.getRoot(), R.string.tdm_reloading_fail, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                return true;
            case R.id.tdm_add_contact:
                EmergencyContactDialog dialog = new EmergencyContactDialog(MainActivity.this);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}