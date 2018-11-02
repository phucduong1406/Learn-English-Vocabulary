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

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<Topic> data = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(List<Topic> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.topic_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Topic topic = this.data.get(position);
        holder.name.setText(topic.getName());
        holder.detail.setText(topic.getDetail());
//        int imageId = this.getMipmapImageIdByName(topic.getImage());
//        holder.image.setImageResource(imageId);
//
//        int iconId = this.getMipmapIconIdByName(topic.getIcon());
//        holder.icon.setImageResource(iconId);
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


//    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
//    public int getMipmapImageIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//
//        // Trả về 0 nếu không tìm thấy.
//        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
//        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
//        return resID;
//    }
//
//    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
//    public int getMipmapIconIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//
//        // Trả về 0 nếu không tìm thấy.
//        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
//        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
//        return resID;
//    }



}
