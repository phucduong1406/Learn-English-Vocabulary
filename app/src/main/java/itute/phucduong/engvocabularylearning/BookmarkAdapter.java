package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {

    Context mContext;
    String[] mScource;

    private ListItemListener listener;
    private ListItemListener listenerDelete;

    public BookmarkAdapter(Context context, String[] source) {
        this.mContext = context;
        this.mScource = source;
    }

    @Override
    public int getCount() {
        return mScource.length;
    }

    @Override
    public Object getItem(int i) {
        return mScource[i];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.bookmark_item, viewGroup, false);
            viewHolder.textView = view.findViewById(R.id.textWord);
            viewHolder.btnDelete = view.findViewById(R.id.btnDelete);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(mScource[i]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(i);
            }
        });


        // Click delete button
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerDelete != null)
                    listenerDelete.onItemClick(i);
            }
        });

        return view;
    }




    public class ViewHolder {
        TextView textView;
        ImageView btnDelete;
    }

    public void setOnItemClick(ListItemListener listItemListener) {
        this.listener = listItemListener;
    }

    // Delete item from favorite words
    public void setOnItemDeleteClick(ListItemListener listItemListener) {
        this.listenerDelete = listItemListener;

    }


}
