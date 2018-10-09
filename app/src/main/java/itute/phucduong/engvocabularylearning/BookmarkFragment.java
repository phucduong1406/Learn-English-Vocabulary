package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class BookmarkFragment extends Fragment {

    ListView bookmarkList;
    MenuItem menuSetting;

    private FragmentListener listener;  // Declare a variable for this listener in fragment
    ArrayAdapter<String> adapter;

    private ArrayList<String> mSource = new ArrayList<String>();

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);

        // Lấy danh sách favorite

        bookmarkList = view.findViewById(R.id.bookmarkList);
        mSource = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, mSource);
        bookmarkList.setAdapter(adapter);

        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if(dictionary.favorite_word)
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

        bookmarkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listener != null)
                    listener.onItemClick(mSource.get(position));
            }
        });





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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here

        // Clear current all the menu items
        menu.clear();

        // Add the new menu items
        inflater.inflate(R.menu.practice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Option menu dict type (EV, VE)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.flashcard:
                return true;
            case R.id.writing:
                return true;
            case R.id.speaking:
                return true;
            case R.id.test:
                return true;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
