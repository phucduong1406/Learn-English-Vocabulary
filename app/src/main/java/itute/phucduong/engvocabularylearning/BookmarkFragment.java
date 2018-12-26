package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import itute.phucduong.engvocabularylearning.listener.BookmarkAdapterListener;
import itute.phucduong.engvocabularylearning.listener.PronunClick;
import itute.phucduong.engvocabularylearning.listener.RemoveClick;

public class BookmarkFragment extends Fragment implements BookmarkAdapterListener, RemoveClick, PronunClick {

    private FragmentListener listener;  // Declare a variable for this listener in fragment

    ListView bookmarkList;
    ArrayList<Bookmark> mSource = new ArrayList<>();
    BookmarkAdapter mAdapter;
    ImageView btnDeleteFW;
    TextToSpeech toSpeech;


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

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);  // lưu dữ liệu offline của firebase




        btnDeleteFW = view.findViewById(R.id.btnDeleteFW);


        // Lấy danh sách favorite
        bookmarkList = view.findViewById(R.id.bookmarkList);
        mSource = new ArrayList<Bookmark>();

        mAdapter = new BookmarkAdapter(getActivity(),R.layout.bookmark_item, mSource,this, this, this);
        bookmarkList.setAdapter(mAdapter);


        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if(dictionary.favorite_word)
                    mSource.add(new Bookmark(dictionary.word, dictionary.mean));
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



    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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



    // Option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.flashcard:
                i = new Intent(getActivity(), FlashcradsActivity.class);
                startActivity(i);
                return true;
            case R.id.test:
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }


    @Override
    public void onItemClick(String name) {
        if (listener != null) {
            listener.onItemClick(name);
        }
    }


    @Override
    public void RemoveClick(final String name) {
        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);
                if (dictionary.word == name)
                    mData.child("Dictionary").child(dictionary.word).child("favorite_word").setValue(false);
                mAdapter.notifyDataSetChanged();


                // Reload current fragment

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

    }

    @Override
    public void PronunClick(final String name) {
        toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    toSpeech.setLanguage(Locale.ENGLISH);
                    toSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}