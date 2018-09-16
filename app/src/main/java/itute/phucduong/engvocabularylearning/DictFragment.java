package itute.phucduong.engvocabularylearning;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;

public class DictFragment extends Fragment {

    private FragmentListener listener;  // Declare a variable for this listener in fragment

    ListView dictList;
    ArrayAdapter<String> adapter;

    public DictFragment() {
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
        return inflater.inflate(R.layout.fragment_dict, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy danh sách dictionary
        dictList = view.findViewById(R.id.dictList);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getListofWords());
        dictList.setAdapter(adapter);
        dictList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!= null) listener.onItemClick(getListofWords()[position]);
            }
        });

    }


    String[] getListofWords () {
        String[] source = new String[] {
                "abandon",
                "accept",
                "access",
                "add",
                "all",
                "and",
                "apple",
                "baby",
                "ball",
                "bag",
                "banana",
                "bold",
                "button",
                "cat",
                "cut",
                "danger",
                "elevator",
                "food",
                "good",
                "house",
                "ink",
                "juice"
        };
        return source;
    }


    // Filter
    public void filterValue(String value) {

        //adapter.getFilter().filter(value);

        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {
            if (adapter.getItem(i).startsWith(value)) {
                dictList.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

}
