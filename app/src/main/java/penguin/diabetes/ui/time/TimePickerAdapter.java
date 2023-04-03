package penguin.diabetes.ui.time;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;


import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimePickerAdapter implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private Context context;
    private Button view;
    private TimePickerDialog timePickerDialog;

    private Calendar calendar = null;
    private int hour, min;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public  TimePickerAdapter(Context context, Button buttonToChange){
        this.context = context;
        view = buttonToChange;

        if (calendar == null){
            calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            view.setText(convertToString());
            view.setOnClickListener(this);
        }

        timePickerDialog = new TimePickerDialog(this.context, this, hour, min, DateFormat.is24HourFormat(this.context));
    }

    @Override
    public void onClick(View view) {
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        hour = hourOfDay;
        min = minutes;
        view.setText(convertToString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String convertToString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.of(hour, min);
        return formatter.format(time);     // Integer.toString(hour) + ":" + Integer.toString(min);
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
