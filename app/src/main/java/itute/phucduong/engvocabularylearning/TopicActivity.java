package itute.phucduong.engvocabularylearning;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    TopicFragment topicFragment;
    List<Topic> mSource = new ArrayList<>();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    //TopicListAdapter adapter;


    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRcvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        topicFragment = new TopicFragment();

        // Fragment mặc định
        goToFragment(topicFragment, true);

        topicFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                goToFragment(DetailFragment.getNewInstance(value), false);
            }
        });



        /** Topic use Listview */
        /*ListView topicList = findViewById(R.id.topicList);
        mSource = new ArrayList<Topic>();
        adapter = new TopicListAdapter(this, mSource);

        topicList.setAdapter(adapter);

        mData.child("Topic").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Topic topic = dataSnapshot.getValue(Topic.class);
                mSource.add(new Topic(topic.getName(), topic.getDetail(), "topic_" + topic.getName(), "icon_" + topic.getName()));
                adapter.notifyDataSetChanged();
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
        });*/


        /** Topic use RecyclerView */
        mRecyclerView = (RecyclerView) findViewById(R.id.topicList);
        mSource = new ArrayList<Topic>();
        //adapter = new TopicListAdapter(this, mSource);
        mRcvAdapter = new RecyclerViewAdapter(mSource);

        mData.child("Topic").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Topic topic = dataSnapshot.getValue(Topic.class);
                mSource.add(new Topic(topic.getName(), topic.getDetail(), "topic_" + topic.getName(), "icon_" + topic.getName()));
                mRcvAdapter.notifyDataSetChanged();
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




        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);

    }

    // Replace fragment
    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_topic, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);  //chuyển giữa các fragment đẹp hơn
        fragmentTransaction.commit();
    }
}
