package penguin.diabetes.ui.measure;

import java.util.Date;

import penguin.diabetes.ui.time.DatePickerAdapter;
import penguin.diabetes.ui.time.TimePickerAdapter;

public class Measure {
    private int day, month, year, hour, minute;
    private int humor;
    private int measure;
    private Date date;

    public Measure(){}

    public Measure(DatePickerAdapter date, TimePickerAdapter time, MeasureAdapter measureAdapter, HumorAdapter humorAdapter) {
        day = date.getDay();
        month = date.getMonth();
        year = date.getYear();
        hour = time.getHour();
        minute = time.getMin();
        humor = humorAdapter.getHumor();
        measure = measureAdapter.getMeasure();

    }

    public String toHumanReadableString(){
        return "Medida: " + measure +
                " - Data: " + day + "/" + month + "/" + year +
                " - Hora: " + hour + ":" + minute +
                " - Humor: " + humor;       // FIXME/ENHANCE ME: make it show a string for humor
    }

    public Date getDate(){
        date = new Date(year, month, day, hour, minute);
        return date;
    }
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public float getHumor() {
        return humor;
    }

    public int getMeasure() {
        return measure;
    }

    public void setDay(String day) {
        this.day = Integer.parseInt(day);
    }

    public void setMonth(String month) {
        this.month = Integer.parseInt(month);
    }

    public void setYear(String year) {
        this.year = Integer.parseInt(year);
    }

    public void setHour(String hour) {
        this.hour = Integer.parseInt(hour);
    }

    public void setMinute(String minute) {
        this.minute = Integer.parseInt(minute);
    }

    public void setHumor(String humor) {
        this.humor = Integer.parseInt(humor);
    }

    public void setMeasure(String measure) {
        this.measure = Integer.parseInt(measure);
    }
}
