package penguin.diabetes.activities;

import android.Manifest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import penguin.diabetes.R;
import penguin.diabetes.db.DataBaseManager;

import penguin.diabetes.fragments.HistoryFragment;
import penguin.diabetes.ui.contact.EmergencyContactDialog;

import penguin.diabetes.ui.main.SectionsPagerAdapter;
import penguin.diabetes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static Context mainContext;

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

        // -------------------- Data Base Initialization ------------------------

        SQLiteDatabase appDatabase = openOrCreateDatabase("Diabetes Monitor", MODE_PRIVATE,null);
        DataBaseManager db = new DataBaseManager(appDatabase);

        //--------------------- Setting a static variable to acquire the main Context ------
        mainContext = this;
    }

    public static Context getMainContext(){
        return mainContext;
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
                    Toast.makeText(MainActivity.getMainContext(), R.string.tdm_reloading,
                            Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.getMainContext(), R.string.tdm_reloading_fail,
                            Toast.LENGTH_LONG).show();
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