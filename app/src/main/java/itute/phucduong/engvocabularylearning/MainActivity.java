package itute.phucduong.engvocabularylearning;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Chọn từ điển EV-VE
    MenuItem menuSetting;

    MenuItem menuSearchVoice;
    Toolbar toolbar;


    DictFragment dictFragment;
    BookmarkFragment bookmarkFragment;


    DetailFragment detailFragment;


    EditText txtSpeechInput;
    Button btnSpeak;
    private static final int REQUEST_CODE = 1234;


    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the button that opens the navigation drawer
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        dictFragment = new DictFragment();
        bookmarkFragment = new BookmarkFragment();

        detailFragment = new DetailFragment();

        goToFragment(dictFragment, true);


        dictFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(detailFragment, false);
            }
        });

        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
            }
        });


        /**
        ** Search: Filter text
        **/

        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dictFragment.filterValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /**
        ** Search: Speech to text
        **/

        final Button speak = findViewById(R.id.btnSearch);
        txtSpeechInput = (EditText) this.findViewById(R.id.edit_search);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speak.setEnabled(false);
            speak.setText("Recognizer not present");
        }
        txtSpeechInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                speak.setEnabled(false);
            }
        });







        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, Qorld!");





        // Get a DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("Name").setValue("Duong Hong Phuc");

        Student s = new Student("Tran Kim Hoang", 1997, "Q9");
        mDatabase.child("Student").setValue(s);
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


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
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
            goToFragment(bookmarkFragment, false);

        }

        else if (id == R.id.nav_lang) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_language);
            dialog.show();
        }

        else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
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

    // Prepare menu dictionary type
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (activeFragment.equals(BookmarkFragment.class.getSimpleName())) {
            menuSetting.setVisible(false);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Favorite words");
        } else {
            menuSetting.setVisible(true);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }
        return true;
    }






    /** The function was performed on 16/9/2018 by Phuc Duong */

    // Handle the action of the button being clicked
    public void speakButtonClicked(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    // Handle the results from the voice recognition activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String Query = matches.get(0);
                txtSpeechInput.setText(Query);
                btnSpeak.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
