package penguin.diabetes.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import penguin.diabetes.R;
import penguin.diabetes.databinding.FragmentMainBinding;
import penguin.diabetes.fragments.GraphFragment;
import penguin.diabetes.fragments.HistoryFragment;
import penguin.diabetes.fragments.MeasureFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

//    public MeasureFragment MeasureFragment = null;
//    public HistoryFragment HistoryFragment = null;
//    public GraphFragment GraphFragment = null;

//    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static Fragment newInstance(int index) {

//        if(MeasureFragment == null){
//            MeasureFragment = new MeasureFragment();
//        }
//        if(HistoryFragment == null){
//            HistoryFragment = new HistoryFragment();
//        }
//        if (GraphFragment == null) {
//            GraphFragment = new GraphFragment();
//        }
        Fragment fragment = null;
        switch (index) {
            case 1:
                MeasureFragment measureFragment = new MeasureFragment();
                fragment = measureFragment;
                break;
            case 2:
                HistoryFragment historyFragment = new HistoryFragment();
                fragment = historyFragment;
                break;
            case 3:
                GraphFragment graphFragment = new GraphFragment();
                fragment = graphFragment;
                break;
        }
        // ---
        // Transferencia de dados entre Fragments:
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.sectionLabel;
//        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}