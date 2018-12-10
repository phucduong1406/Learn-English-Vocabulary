package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    public ImageView btnCheckWT, btnVolWT;
    public EditText txtNameWT;
    public TextView txtPosWT;


    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        btnCheckWT = (ImageView) itemView.findViewById(R.id.btnCheckWT);
        btnVolWT = (ImageView) itemView.findViewById(R.id.btnVolWT);
        txtNameWT = (EditText) itemView.findViewById(R.id.txtNameWT);
        txtPosWT = (TextView) itemView.findViewById(R.id.txtPosWT);

        btnCheckWT.setOnClickListener(this);
        //btnVolWT.setOnClickListener(this);
        //itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),true);
        return true;
    }
}

public class WritingAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements TextToSpeech.OnInitListener {

    private List<Writing> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    TextToSpeech toSpeech;
    String name;



    public int countRight = 0, countWrong = 0;

    public WritingAdapter(Context aContext, List<Writing> data) {
        this.context = aContext;
        this.data = data;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wt_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Writing writing = this.data.get(position);
        name = writing.getName();
        //holder.name.setText(writing.getName());
        int pos = position + 1;
        holder.txtPosWT.setText(pos + "/" + getItemCount());



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText(context, "Long Click: "+ data.get(position), Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();


                    if(writing.getName() == name) {
                        countRight++;

                    }
                    else countWrong++;



                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return data.size();
    }



    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = toSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //btnHear.setEnabled(true);
                toSpeech.speak(name, TextToSpeech.QUEUE_FLUSH, null);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

}

