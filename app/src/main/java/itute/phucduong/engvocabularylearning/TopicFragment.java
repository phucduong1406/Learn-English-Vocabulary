package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TopicFragment extends Fragment {

    ListView topicList;

    private FragmentListener listener;  // Declare a variable for this listener in fragment
    ArrayAdapter<Topic> adapter;

    private List<Topic> mSource = new ArrayList<Topic>();

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     /*   // Lấy danh sách topic
        List<Topic> image_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.topicList);
        listView.setAdapter(new TopicListAdapter(getContext(), image_details));

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
            }
        });

*/


//        topicList = view.findViewById(R.id.topicList);
//        mSource = new ArrayList<Topic>();
//
//        mSource.add(new Topic("Food","Food","topic_food","icon_food"));
//
//        topicList.setAdapter(new TopicListAdapter(getContext(), mSource));
//
//        mData.child("Topic").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Topic topic = dataSnapshot.getValue(Topic.class);
//                mSource.add(new Topic(topic.getName(), topic.getDetail(), topic.getImage(), topic.getIcon()));
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



    }

    private  List<Topic> getListData() {
        List<Topic> list = new ArrayList<Topic>();
        Topic food = new Topic("Food","Food","topic_food","icon_food");
        Topic enviroment = new Topic("education","education","topic_enviroment","icon_enviroment");
        Topic clothes = new Topic("clothes","clothes","topic_clothes","icon_clothes");


        list.add(food);
        list.add(enviroment);
        list.add(clothes);

        return list;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

}
