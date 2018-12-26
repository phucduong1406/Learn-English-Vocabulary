package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class BookmarkAdapter  extends ArrayAdapter {

    private BookmarkAdapterListener bookmarkAdapterListener;
    private RemoveClick removeClick;
    private PronunClick pronunClick;
    private ArrayList<Bookmark> listData;
    private LayoutInflater layoutInflater;
    private int resource;
    Holder holder;
    private View view;
    private Context context;
    ListView bookmarkList;

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();


    public BookmarkAdapter(Context aContext, int resource, ArrayList<Bookmark> objects,
                           BookmarkAdapterListener bookmarkAdapterListener, RemoveClick removeClick, PronunClick pronunClick) {
        super(aContext,resource, objects);
        this.bookmarkAdapterListener = bookmarkAdapterListener;
        this.removeClick = removeClick;
        this.pronunClick = pronunClick;
        this.context = aContext;
        this.resource=resource;
        this.listData = objects;
        layoutInflater = LayoutInflater.from(aContext);
    }

//    @Override
//    public int getCount() {
//        return listData.size();
//    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, parent,false);

        holder = new Holder();
        Bookmark bookmark = this.listData.get(position);

        holder.wordFW = (TextView)view.findViewById(R.id.txtWordFW);
        holder.meanFW = (TextView)view.findViewById(R.id.txtMeanFW);

        holder.wordFW.setText(bookmark.getName());
        holder.meanFW.setText(bookmark.getMean());

        Object o = listData.get(position);
        final Bookmark b = (Bookmark) o;
        final String name = b.getName();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkAdapterListener.onItemClick(name);
            }
        });



        ImageView btnDeleteFW = (ImageView) view.findViewById(R.id.btnDeleteFW);
        btnDeleteFW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeClick.RemoveClick(name);
                Toast.makeText(context, name + " was deleted", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView btnPronunFW = (ImageView) view.findViewById(R.id.btnPronunFW);
        btnPronunFW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pronunClick.PronunClick(name);

            }
        });



        return view;
    }

    static class Holder {
        TextView wordFW;
        TextView meanFW;
    }

}
