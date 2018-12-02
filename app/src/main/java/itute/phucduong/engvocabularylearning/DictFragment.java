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
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DictFragment extends Fragment {

    ListView dictList;

    private FragmentListener listener;  // Declare a variable for this listener in fragment
    ArrayAdapter<String> adapter;

    private ArrayList<String> mSource = new ArrayList<String>();

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public DictFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dict, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Lấy danh sách dictionary
        dictList = view.findViewById(R.id.dictList);
        mSource = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, mSource);
        dictList.setAdapter(adapter);

        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                mSource.add(dictionary.word);
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
        });

        dictList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listener != null) {
                    listener.onItemClick(mSource.get(position));
                    //Toast.makeText(getActivity(), mSource.get(position),Toast.LENGTH_SHORT).show();  //lấy 1 item
                    //Toast.makeText(getActivity(), mSource.toString(), Toast.LENGTH_LONG).show();  //lấy list tất cả từ [...]
                }
            }
        });


    }



    // Filter
    public void filterValue(String value) {

        //adapter.getFilter().filter(value);

        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {
            if (adapter.getItem(i).startsWith(value)) {
                dictList.setSelection(i);
                break;
            }
        }
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
