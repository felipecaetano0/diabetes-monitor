package penguin.diabetes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.LinkedList;

import penguin.diabetes.R;
import penguin.diabetes.db.DataBaseManager;
import penguin.diabetes.ui.measure.Measure;
import penguin.diabetes.ui.time.DatePickerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphFragment extends Fragment {

    private static View root;
    private GraphView graph;

    Button btDate;
    DatePickerAdapter datePickerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphFragment newInstance(String param1, String param2) {
        GraphFragment fragment = new GraphFragment();
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
        root = inflater.inflate(R.layout.fragment_graph, container, false);

        graph = (GraphView) root.findViewById(R.id.graph);
        loadGraph();

        return root;
    }

    // When reopening the app, make sure it draws the fragment widgets correctly
    @Override
    public void onResume() {
        super.onResume();
        graph = (GraphView) root.findViewById(R.id.graph);

        btDate = root.findViewById(R.id.btGraphDate);
        datePickerAdapter = new DatePickerAdapter(getContext(), btDate);

        loadGraph();
    }

    public void loadGraph(){
        // Converting to DataPoint array
        LinkedList<Measure> measures = DataBaseManager.getMeasures();

        DataPoint[] points = new DataPoint[measures.size()];
        for (int i = 0; i < points.length; i++) {
            Measure measure = measures.get(i);
            points[i] = new DataPoint(measure.getDate(), measure.getMeasure());
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(root.getContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 3 because of the space

        // set manual x bounds to have nice steps
//        graph.getViewport().setMinX(d1.getTime());
//        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        // -- Scrolling
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        // -- Zooming
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(35, 50)
        });


        // -- Y axis boundary setup
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(50);

        // -- X axis boundary setup
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(35);

        // -- Scrolling
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        // -- Zooming
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(series);

        */
    }
}