package penguin.diabetes.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import penguin.diabetes.ui.contact.EmergencyContact;
import penguin.diabetes.ui.measure.Measure;

public class DataBaseManager {

    private static SQLiteDatabase dataBase;

    public DataBaseManager(SQLiteDatabase db) {
        dataBase = db;

        dataBase.execSQL("CREATE TABLE IF NOT EXISTS measure " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "day INT, month INT, year INT, hour INT, minute INT, humor INT, measure INT)");

        dataBase.execSQL("CREATE TABLE IF NOT EXISTS contact " +
                "(id INT PRIMARY KEY, min INT, max INT, phone VARCHAR(13))");

    }
/*
        // criar tabela
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS measure (day INT, month INT ....) ");
        // inserir valores
        dataBase.execSQL("INSERT INTO measure (day, month) VALUES (12, 12, 'FRASES' .....)");
        // recuperar
        Cursor cursor = dataBase.rawQuery("SELECT day, month FROM measure", null);

        int dayIndex = cursor.getColumnIndex("day");
        int monthIndex = cursor.getColumnIndex("month");
        cursor.moveToFirst();
        while (cursor != null) {
            Log.i("DataBaseManager: ", cursor.getString(dayIndex));
            cursor.moveToNext();
        }
*/


    public static void insertMeasure(Measure measure){
        dataBase.execSQL("INSERT INTO measure (day, month, year, hour, minute, humor, measure) VALUES (" +
                         measure.getDay() + ", " +
                         measure.getMonth() + ", " +
                         measure.getYear() + ", " +
                         measure.getHour() + ", " +
                         measure.getMinute() + ", " +
                         measure.getHumor() + ", " +
                         measure.getMeasure() + ");"
        );
    }

    public static LinkedList<Measure> getMeasures(){
        LinkedList<Measure> list = new LinkedList<>();      // ListIterator<Measure> it = list.listIterator();

        Cursor cursor = dataBase.rawQuery("SELECT * FROM measure", null);
        if(cursor.moveToFirst()){
            do{
                Measure m = new Measure();
                m.setDay(cursor.getString(cursor.getColumnIndex("day")));
                m.setMonth(cursor.getString(cursor.getColumnIndex("month")));
                m.setYear(cursor.getString(cursor.getColumnIndex("year")));
                m.setHour(cursor.getString(cursor.getColumnIndex("hour")));
                m.setMinute(cursor.getString(cursor.getColumnIndex("minute")));
                m.setHumor(cursor.getString(cursor.getColumnIndex("humor")));
                m.setMeasure(cursor.getString(cursor.getColumnIndex("measure")));

                list.add(m);

            }while(cursor.moveToNext());
        }
        Collections.sort(list, new Comparator<Measure>() {
            @Override
            public int compare(Measure measure, Measure measure2) {
                return measure.getDate().compareTo(measure2.getDate());
            }
        });
        return list;
    }



    public static SQLiteDatabase getDataBase() {
        return dataBase;
    }
}
