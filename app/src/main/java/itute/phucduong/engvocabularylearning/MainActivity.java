package itute.phucduong.engvocabularylearning;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.database.ChildEventListener;
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

    Toolbar toolbar;

    DictFragment dictFragment;
    BookmarkFragment bookmarkFragment;
    DetailFragment detailFragment;
    EmptyFragment emptyFragment;
    NoInternetFragment noInternetFragment;
    TopicFragment topicFragment;

    ImageButton btnHear;
    TextView textWord;
    TextToSpeech toSpeech;

    EditText txtSpeechInput;

    private static final int REQUEST_CODE = 1234;


    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /**
         * Push database
        Dictionary dictionary = new Dictionary("assurance","sự chắc chắn","sự chắc chắn","",false,false,false);
        mData.child("Dictionary").push().setValue(dictionary);*/


        // Add the button that opens the navigation drawer
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        dictFragment = new DictFragment();
        bookmarkFragment = new BookmarkFragment();
        detailFragment = new DetailFragment();
        emptyFragment = new EmptyFragment();
        topicFragment = new TopicFragment();
        noInternetFragment = new NoInternetFragment();



        // Fragment mặc định mở mở app
        goToFragment(bookmarkFragment, true);


        dictFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
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

        final EditText edit_search = findViewById(R.id.edit_search);
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

        final ImageButton speak = findViewById(R.id.btnSearch);
        txtSpeechInput = (EditText) this.findViewById(R.id.edit_search);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            speak.setEnabled(false);
            //speak.setText("Recognizer not present");
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        switch (item.getItemId()) {
            case R.id.nav_dict:
                goToFragment(dictFragment, false);
                break;
            case R.id.nav_topic:
                i = new Intent(MainActivity.this, TopicActivity.class);
                startActivity(i);
                break;
            case R.id.nav_star:
                goToFragment(bookmarkFragment, false);
                break;
            case R.id.nav_recent:
                goToFragment(emptyFragment, true);
                break;
            case R.id.nav_person:
                goToFragment(emptyFragment, true);
                break;
            case R.id.nav_lang:
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_language);
                dialog.show();
                break;
            case R.id.nav_rate:
               
                break;
            case R.id.nav_help:

                break;
            case R.id.nav_about:
                i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Replace fragment
    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  //chuyển giữa các fragment đẹp hơn
        fragmentTransaction.commit();
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
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }
}
