package penguin.diabetes.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import penguin.diabetes.R;
import penguin.diabetes.db.DataBaseManager;
import penguin.diabetes.ui.contact.EmergencyContact;
import penguin.diabetes.ui.contact.EmergencyContactDialog;
import penguin.diabetes.ui.measure.HumorAdapter;
import penguin.diabetes.ui.measure.Measure;
import penguin.diabetes.ui.measure.MeasureAdapter;
import penguin.diabetes.ui.time.DatePickerAdapter;
import penguin.diabetes.ui.time.TimePickerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasureFragment extends Fragment {

    private Button btDate, btHour;
    private EditText etMeasure;
    private RatingBar rbHumor;
    private TextView tvHumorRating;
    private FloatingActionButton fabSave, fabMessage;

    private Measure measure;

    private DatePickerAdapter datePickerAdapter;
    private TimePickerAdapter timePickerAdapter;
    private MeasureAdapter measureAdapter;
    private HumorAdapter humorAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MeasureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeasureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeasureFragment newInstance(String param1, String param2) {
        MeasureFragment fragment = new MeasureFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_measure, container, false);

        btDate = (Button) root.findViewById(R.id.btDate);
        btHour = (Button) root.findViewById(R.id.btHour);
        etMeasure = (EditText) root.findViewById(R.id.etMeasure);
        rbHumor = (RatingBar) root.findViewById(R.id.rbHumor);
        tvHumorRating = (TextView) root.findViewById(R.id.tvHumorRatting);

        datePickerAdapter = new DatePickerAdapter(this.getContext(), btDate);
        timePickerAdapter = new TimePickerAdapter(this.getContext(), btHour);
        measureAdapter = new MeasureAdapter(etMeasure);
        humorAdapter = new HumorAdapter(rbHumor, tvHumorRating);


        // Floating Action Buttons:
        fabSave = root.findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new FabSaveListener());

        fabMessage = root.findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new FabMessageListener());
        return root;
    }

    private class FabMessageListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            EmergencyContactDialog contactDialog = new EmergencyContactDialog(getContext());
            contactDialog.show();
        }
    }


    private class FabSaveListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(measureAdapter.getMeasure() == -1){
                Snackbar.make(view, R.string.med_invalid_value, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                boolean fail = false;
                measure = new Measure(datePickerAdapter, timePickerAdapter, measureAdapter, humorAdapter);
                if(measureAdapter.isDangerZone()){
                    try{
                        EmergencyContact.sendMessageToEmergencyContact(measure.getMeasure());
                    } catch (Exception e){
                        e.printStackTrace();
                        Snackbar.make(view, R.string.med_permission_denied, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                try{
                    DataBaseManager.insertMeasure(measure);
                }catch (Exception e){
                    e.printStackTrace();
                    fail = true;
                    Snackbar.make(view, R.string.med_save_fail_db, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                try{
                    HistoryFragment.refresh();
                }catch (Exception e){
                    e.printStackTrace();
                    fail = true;
                    Snackbar.make(view, R.string.med_save_fail_history, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                if(!fail) {
                    Snackbar.make(view, R.string.med_save_success, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        }
    }
}