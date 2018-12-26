package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<Topic> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public RecyclerViewAdapter(Context aContext, List<Topic> data) {
        this.context = aContext;
        this.data = data;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.topic_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    // Gán dữ liệu từ data vào viewHolder
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {


        Topic topic = this.data.get(position);
        holder.name.setText(topic.getName());
        holder.detail.setText(topic.getDetail());

        //Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/engvoclearning.appspot.com/o/topic%2F" + topic.getImage() + ".png?alt=media").into(holder.image);



        //Log.i("CheckName", "::"+ topic.getImage());


        int imageId = this.getMipmapImageIdByName(topic.getImage());
        holder.image.setImageResource(imageId);

        int iconId = this.getMipmapIconIdByName(topic.getIcon());
        holder.icon.setImageResource(iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView detail;
        ImageView image;
        ImageView icon;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            detail = (TextView) itemView.findViewById(R.id.detail);
            image = (ImageView) itemView.findViewById(R.id.image);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }


    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapImageIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CheckImage", "Res Name: "+ resName+" => Res ID = "+ resID);
        return resID;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapIconIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CheckIcon", "Res Name: "+ resName+" => Res ID = "+ resID);
        return resID;
    }



}
