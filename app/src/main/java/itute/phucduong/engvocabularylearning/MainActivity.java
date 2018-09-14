package itute.phucduong.engvocabularylearning;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Chọn từ điển EV-VE
    MenuItem menuSetting;

    DictFragment dictFragment;
    StarFragment starFragment;


    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        dictFragment = new DictFragment();
        starFragment = new StarFragment();

        detailFragment = new DetailFragment();

        goToFragment(dictFragment, true);


        dictFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(detailFragment, false);
            }
        });

        starFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Menu dictionary type (EV, VE)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);

        // Lưu lại trạng thái từ điển đã chọn
        String id = Global.getState(this, "dic_type");
        if (id != null)
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        else {

            // Apply SQLite

           /* ArrayList<String> source = dbHelper.getWord(R.id.action_ev);
            dictionaryFragment.resetDataSource(source);    chua ne*/
        }

        return true;
    }

    // Option menu dict type (EV, VE)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            int id = item.getItemId();

            if (R.id.action_settings == id) return true;

            Global.saveState(this, "dic_type", String.valueOf(id));
            /*ArrayList<String> source = dbHelper.getWord(id);     chua ne*/

            // Đổi icon và lấy DB tương ứng khi chọn từ điển E-V, V-E
            if (id == R.id.action_ev) {
                //dictionaryFragment.resetDataSource(source);
                menuSetting.setIcon(getDrawable(R.drawable.ev_white));  // Set icon EV
            } else if (id == R.id.action_ve) {
                //dictionaryFragment.resetDataSource(source);
                menuSetting.setIcon(getDrawable(R.drawable.ve_white));  // Set icon VE
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dict) {
            // Handle the dictionary action
            goToFragment(dictFragment, false);
        }

        else if (id == R.id.nav_star) {
            // Handle the star words action
            goToFragment(starFragment, false);

        }

        else if (id == R.id.nav_recent) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Replace fragment
    void goToFragment(android.support.v4.app.Fragment fragment, boolean isTop) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
