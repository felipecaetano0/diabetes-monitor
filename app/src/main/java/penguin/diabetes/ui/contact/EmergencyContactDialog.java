package penguin.diabetes.ui.contact;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import penguin.diabetes.R;
import penguin.diabetes.activities.MainActivity;
import penguin.diabetes.ui.measure.MeasureAdapter;

public class EmergencyContactDialog extends Dialog{
    private Context context;
    private Button btCancel, btRegister;
    private EditText etLowGlucose, etHighGlucose, etEmergencyPhone;

    private String lowerGlucoseThreshold, upperGlucoseThreshold, phone;

    public EmergencyContactDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_add_contact);
        btCancel = findViewById(R.id.btCancelRegister);
        btRegister = findViewById(R.id.btRegister);
        etHighGlucose = findViewById(R.id.etHighGlucose);
        etLowGlucose = findViewById(R.id.etLowGlucose);
        etEmergencyPhone = findViewById(R.id.etEmergencyPhone);

        EmergencyContact contact = EmergencyContact.getInstance().getFromDB();
        if (contact != null) {
            etHighGlucose.setText(Integer.toString(contact.getMaximumMeasureDangerZone()));
            etLowGlucose.setText(Integer.toString(contact.getMinimumMeasureDangerZone()));
            etEmergencyPhone.setText(contact.getPhone());
        }

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void register(){
        lowerGlucoseThreshold = etLowGlucose.getText().toString();
        upperGlucoseThreshold = etHighGlucose.getText().toString();
        phone = etEmergencyPhone.getText().toString();
        if ( (lowerGlucoseThreshold.compareTo("") == 0) ||
             (upperGlucoseThreshold.compareTo("") == 0) ||
             (phone.compareTo("") == 0) ){
            Toast.makeText(MainActivity.getMainContext(), R.string.tdm_null_input,
                    Toast.LENGTH_LONG).show();
        }else{
            EmergencyContact contact = EmergencyContact.getInstance();
            contact.setPhone(phone);
            contact.setMaximumMeasureDangerZone(upperGlucoseThreshold);
            contact.setMinimumMeasureDangerZone(lowerGlucoseThreshold);

            MeasureAdapter.setMaximumMeasureDangerZone(contact.getMaximumMeasureDangerZone());
            MeasureAdapter.setMinimumMeasureDangerZone(contact.getMinimumMeasureDangerZone());

            // -------------------- SMS Permission request ---------------------------
            ActivityCompat.requestPermissions((Activity) MainActivity.getMainContext(),new String[]{Manifest.permission.SEND_SMS},1);

            try {
                contact.updateDB();
                Toast.makeText(MainActivity.getMainContext(), R.string.tdm_add_contact_success,
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.getMainContext(), R.string.tdm_add_contact_fail,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
