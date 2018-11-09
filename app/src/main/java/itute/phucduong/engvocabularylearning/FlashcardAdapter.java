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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.RecyclerViewHolder> {

    private List<Flashcard> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public FlashcardAdapter(Context aContext, List<Flashcard> data) {
        this.context = aContext;
        this.data = data;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @NonNull
    @Override
    public FlashcardAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fc_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    // Gán dữ liệu từ data vào viewHolder
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {


        Flashcard flashcard = this.data.get(position);
        holder.name.setText(flashcard.getName());
        holder.pronun.setText(flashcard.getPronun());
        holder.mean.setText(flashcard.getMean());


        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/engvoclearning.appspot.com/o/flashcard%2Fflashcard_" + flashcard.getName() + ".jpg?alt=media").into(holder.image);
        Log.i("CheckNameFC", ":<3:"+ flashcard.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView mean;
        TextView pronun;
        ImageView image;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvFCName);
            pronun = (TextView) itemView.findViewById(R.id.tvFCPronun);
            mean = (TextView) itemView.findViewById(R.id.tvFCMean);
            image = (ImageView) itemView.findViewById(R.id.imgFC);
        }
    }

}
