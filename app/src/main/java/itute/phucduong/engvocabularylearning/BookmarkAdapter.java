package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookmarkAdapter  extends ArrayAdapter {
    private ArrayList<Bookmark> listData;
    private LayoutInflater layoutInflater;
    private int resource;
    Holder holder;
    private View view;
    private Context context;
    ListView bookmarkList;

    public BookmarkAdapter(Context aContext,int resource, ArrayList<Bookmark> objects) {
        super(aContext,resource, objects);
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

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, parent,false);

        holder = new Holder();
        Bookmark bookmark = this.listData.get(position);

        holder.wordFW = (TextView)view.findViewById(R.id.txtWordFW);
        holder.meanFW = (TextView)view.findViewById(R.id.txtMeanFW);

        holder.wordFW.setText(bookmark.getName());
        holder.meanFW.setText(bookmark.getMean());



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
                //if (listener != null) {
                    Object o = listData.get(position);
                    Bookmark b = (Bookmark) o;

                    Toast.makeText(context, b.getName(), Toast.LENGTH_SHORT).show();

                    //listener.onItemClick(b.getName());
            }
        });


        return view;
    }

    static class Holder {
        TextView wordFW;
        TextView meanFW;
    }

}
