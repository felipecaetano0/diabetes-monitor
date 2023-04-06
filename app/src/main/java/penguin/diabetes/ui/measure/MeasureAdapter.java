package penguin.diabetes.ui.measure;

import android.graphics.Color;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import penguin.diabetes.R;
import penguin.diabetes.activities.MainActivity;
import penguin.diabetes.ui.contact.EmergencyContact;

public class MeasureAdapter implements View.OnKeyListener {
    private EditText view;
    private int measure;
    private static int minimumMeasureDangerZone, maximumMeasureDangerZone;
    private boolean dangerZone = false;


    public MeasureAdapter(EditText etMeasure){
        view = etMeasure;
        EmergencyContact contact = EmergencyContact.getInstance().getFromDB();
        if (contact != null) {
            minimumMeasureDangerZone = contact.getMinimumMeasureDangerZone();
            maximumMeasureDangerZone = contact.getMaximumMeasureDangerZone();
        } else {
            minimumMeasureDangerZone = 0;
            maximumMeasureDangerZone = 1000;
        }
        view.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                getMeasure();
                return true;
            }

        }
        return false;
    }

    public int getMeasure() {
        if(this.view.getText().toString().equals("")){
            Toast.makeText(MainActivity.getMainContext(), R.string.measure_invalid_value,
                    Toast.LENGTH_LONG).show();
            return -1;
        }else {
            this.measure = Integer.parseInt(this.view.getText().toString());
            if (measure >= maximumMeasureDangerZone || measure <= minimumMeasureDangerZone) {
                this.view.setTextColor(Color.RED);
                dangerZone = true;
            } else {
                this.measure = Integer.parseInt(this.view.getText().toString());
                this.view.setTextColor(Color.BLACK);
                dangerZone = false;
            }
            return measure;
        }
    }



    public boolean isDangerZone(){
        return dangerZone;
    }

    public static int getMinimumMeasureDangerZone() {
        return minimumMeasureDangerZone;
    }

    public static void setMinimumMeasureDangerZone(int m) {
        minimumMeasureDangerZone = m;
    }

    public static int getMaximumMeasureDangerZone() {
        return maximumMeasureDangerZone;
    }

    public static void setMaximumMeasureDangerZone(int m) {
        maximumMeasureDangerZone = m;
    }
}
