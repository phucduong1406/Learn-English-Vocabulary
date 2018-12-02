package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WritingAdapter extends RecyclerView.Adapter<WritingAdapter.RecyclerViewHolder> {

    private List<Writing> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public WritingAdapter(Context aContext, List<Writing> data) {
        this.context = aContext;
        this.data = data;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @NonNull
    @Override
    public WritingAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wt_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    // Gán dữ liệu từ data vào viewHolder
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {


        Writing writing = this.data.get(position);
        //holder.name.setText(writing.getName());


        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/engvoclearning.appspot.com/o/flashcard%2Fflashcard_" + writing.getName() + ".jpg?alt=media").into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtNameWT);
            image = (ImageView) itemView.findViewById(R.id.imgWT);
        }
    }
}
