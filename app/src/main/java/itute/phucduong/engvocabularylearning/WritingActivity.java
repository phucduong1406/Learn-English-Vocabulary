package itute.phucduong.engvocabularylearning;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WritingActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    List<Writing> mSource = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    RecyclerView mRecyclerView;
    WritingAdapter mAdapter;

    ImageView btnHear;
    TextToSpeech toSpeech;
    ImageView btnCheck, btnVolWT;
    public EditText txtWord;
    TextView txtRight;
     String name;
    int countRight = 0, countWrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        txtWord = (EditText) findViewById(R.id.txtNameWT);
        btnCheck = (ImageView) findViewById(R.id.btnCheckWT);
        btnVolWT = (ImageView) findViewById(R.id.btnVolWT);


//        txtWord.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                btnVolWT.setVisibility(View.INVISIBLE);
//                imgWT.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                btnVolWT.setVisibility(View.VISIBLE);
//                imgWT.setVisibility(View.VISIBLE);
//                if(txtWord.getText().toString() == name) {
//                    countRight++;
//                    Toast.makeText(WritingActivity.this,countRight,Toast.LENGTH_SHORT).show();
//                }
//                else countWrong++;
//            }
//        });




//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(txtWordU.getText().toString() == name) {
//                    countRight++;
//                    Toast.makeText(WritingActivity.this,countRight,Toast.LENGTH_SHORT).show();
//                }
//                else countWrong++;
//            }
//        });

        toSpeech = new TextToSpeech(this, this);
        btnHear = (ImageView) findViewById(R.id.bgRight);

        btnHear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
                Toast.makeText(WritingActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.writingList);
        mSource = new ArrayList<Writing>();
        mAdapter = new WritingAdapter(this, mSource);


        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Writing writing = dataSnapshot.getValue(Writing.class);
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if(dictionary.favorite_word) {
                    mSource.add(new Writing(dictionary.word));
                    name = dictionary.word;
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);

    }






    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }
        super.onDestroy();
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = toSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //btnHear.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {

        String text = name;

        toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
