package itute.phucduong.engvocabularylearning;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FlashcradsActivity extends AppCompatActivity {

    List<Flashcard> mSource = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    RecyclerView mRecyclerView;
    FlashcardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcrads);

        mRecyclerView = (RecyclerView) findViewById(R.id.flashcardList);
        mSource = new ArrayList<Flashcard>();
        mAdapter = new FlashcardAdapter(this, mSource);



        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Flashcard flashcard = dataSnapshot.getValue(Flashcard.class);
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if(dictionary.favorite_word) {
                    mSource.add(new Flashcard(dictionary.word, flashcard.getPronun(), flashcard.getMean(), "flashcrad_" + flashcard.getName()));
                    Toast.makeText(FlashcradsActivity.this, "Word: " + flashcard.getName(), Toast.LENGTH_LONG).show();
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



//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

       mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }
}
