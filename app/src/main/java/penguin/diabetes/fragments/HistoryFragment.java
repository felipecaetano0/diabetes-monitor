package penguin.diabetes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;

import penguin.diabetes.R;
import penguin.diabetes.db.DataBaseManager;
import penguin.diabetes.ui.layoutAdapter.HistoryAdapter;
import penguin.diabetes.ui.measure.Measure;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View root;

    private static RecyclerView rvHistory;
    private static HistoryAdapter historyAdapter;
    private static String[] measures_string;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_history, container, false);

        rvHistory = root.findViewById(R.id.rvHistory);
        loadHistoryList();

        return root;
    }


    // When reopening the app, make sure it draws the fragment widgets correctly
    @Override
    public void onResume() {
        super.onResume();
        rvHistory = root.findViewById(R.id.rvHistory);
        loadHistoryList();
    }


    public void loadHistoryList(){
        // ---- Populate list
        LinkedList<Measure> list = DataBaseManager.getMeasures();

        // TODO: turn this array into a linked list
        measures_string = new String[list.size()];

        for (int i = 0; i < measures_string.length; i++){
            Measure m = list.get(measures_string.length - 1 - i);
            measures_string[i] = m.toHumanReadableString();
        }

        // ---- Inflating views
        // Adapter Setup
        historyAdapter = new HistoryAdapter( measures_string );

        // Recycler View Setup
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHistory.setLayoutManager(layoutManager);

        //rvHistory.setHasFixedSize(true);
        rvHistory.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        rvHistory.setAdapter(historyAdapter);

//        Snackbar.make( root , "Loading history method called\nDB item count: " + historyAdapter.getItemCount(), Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    public static void refresh(){
        LinkedList<Measure> list = DataBaseManager.getMeasures();
        measures_string = new String[list.size()];
        for (int i = 0; i < measures_string.length; i++){
            Measure m = list.get(measures_string.length - 1 - i);
            measures_string[i] = m.toHumanReadableString();
        }
        historyAdapter = new HistoryAdapter( measures_string );
        rvHistory.setAdapter(historyAdapter);
    }
}