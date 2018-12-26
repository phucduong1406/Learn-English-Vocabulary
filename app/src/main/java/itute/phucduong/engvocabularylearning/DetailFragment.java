package itute.phucduong.engvocabularylearning;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.RECEIVER_VISIBLE_TO_INSTANT_APPS;


public class DetailFragment extends Fragment {

    private String value = "";

    private TextView textWord;
    private TextView textPronun;
    private ImageButton btnBookmark, btnVolume;
    ImageButton btnHear;
    private WebView textMean;

    private int mDicType;

    TextToSpeech toSpeech;
    private static final int REQUEST_CODE = 111;



    private FragmentListener listener;  // Declare a variable for this listener in fragment



    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public DetailFragment() {
        // Required empty public constructor
    }

    //
    public static DetailFragment getNewInstance(String value) {
        DetailFragment fragment = new DetailFragment();
        fragment.value = value;
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detail, container, false);

        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        textWord = view.findViewById(R.id.textWord);
        btnHear = view.findViewById(R.id.btnHear);
        click();
        return view;
    }

    private void click() {
        /**
         * Text to speech (phát âm)
         */
        btnHear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = textWord.getText().toString();
                toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i != TextToSpeech.ERROR) {


                                /*if (flagLang == 0) {
                                    toSpeech.setLanguage(Locale.ENGLISH);
                                } else if (flagLang == 1) {
                                    toSpeech.setLanguage(Locale.forLanguageTag("vi-VN"));
                                }*/

                            toSpeech.setLanguage(Locale.ENGLISH);
                            toSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
            }
        });
    }

    //
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);

        btnHear = view.findViewById(R.id.btnHear);
        textWord = view.findViewById(R.id.textWord);
        textPronun = view.findViewById(R.id.textPronun);
        btnBookmark = view.findViewById(R.id.btnBookmark);
        textMean = view.findViewById(R.id.textWordTranslate);

        // DB
        mData.child("Dictionary").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                final Dictionary dictionary = dataSnapshot.getValue(Dictionary.class);

                assert dictionary != null;
                if (value.equals(dictionary.word)) {
                    textWord.setText(value);
                    textPronun.setText(dictionary.pronun);
                    textMean.loadDataWithBaseURL(null, dictionary.detail, "text/html", "utf-8", null);

                    // Set icon
                    int icon;
                    if (!dictionary.favorite_word) {

                        icon = R.drawable.ic_star_border;
                        btnBookmark.setImageResource(icon);
                    } else {

                        icon = R.drawable.ic_star;
                        btnBookmark.setImageResource(icon);
                    }

                    btnBookmark.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!dictionary.favorite_word) {
                                btnBookmark.setImageResource(R.drawable.ic_star);
                                mData.child("Dictionary").child(value).child("favorite_word").setValue(true);


                            } else {
                                btnBookmark.setImageResource(R.drawable.ic_star_border);
                                mData.child("Dictionary").child(value).child("favorite_word").setValue(false);
                            }

                        }
                    });
                }


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







        final ImageButton speak = view.findViewById(R.id.btnVoice);
        String txtSpeechInput;
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
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
        //inflater.inflate(R.menu.add, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    // Option menu
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





    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (result.get(0).equals(textWord.getText().toString()))
                        Toast.makeText(getActivity(), R.string.speech_right, Toast.LENGTH_SHORT).show();

                    if (!result.get(0).equals(textWord.getText().toString()))
                        Toast.makeText(getActivity(), R.string.speech_wrong, Toast.LENGTH_SHORT).show();

                }
                break;
            }

        }
    }

}



