package penguin.diabetes.ui.time;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerAdapter implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private int year, month, day;
    private Calendar calendar = null;
    private int style = AlertDialog.THEME_HOLO_LIGHT;

    private DatePickerDialog datePickerDialog;

    private Context context;
    private Button view;

    private final String[] MONTH_STR = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

    public DatePickerAdapter(Context context, Button buttonToChange){
        this.context = context;
        view = buttonToChange;

        if (calendar == null) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        view.setText(convertToString());
        view.setOnClickListener(this);
        datePickerDialog = new DatePickerDialog(context, style, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
        String date = convertToString();
        view.setText(date);
    }

    @Override
    public void onClick(View view) {
        showDialog();
    }

    public String getCurrentDate(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return this.toString();
    }

    public String convertToString(){
        return Integer.toString(day) + " " + MONTH_STR[month] + " " + Integer.toString(year);
    }

    public void showDialog(){
        datePickerDialog.show();
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
