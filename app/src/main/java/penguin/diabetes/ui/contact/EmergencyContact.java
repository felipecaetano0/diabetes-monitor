package penguin.diabetes.ui.contact;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;

import java.util.ArrayList;

import penguin.diabetes.R;
import penguin.diabetes.activities.MainActivity;
import penguin.diabetes.db.DataBaseManager;

public class EmergencyContact {
    private int minimumMeasureDangerZone, maximumMeasureDangerZone;
    private static String phone;
    private static EmergencyContact instance;

    private EmergencyContact(){
    };

    public static EmergencyContact getInstance(){
        if(instance == null){
            instance = new EmergencyContact();
        }
        return instance;
    }

    public void updateDB(){
        SQLiteDatabase dataBase = DataBaseManager.getDataBase();
        Cursor cursor = dataBase.rawQuery("SELECT * FROM contact", null);
        // This logic assures that always will have just one row in contact table
        if(cursor.getCount() > 0){
            // Contact table already has a value
            dataBase.execSQL("UPDATE contact SET min = " + this.getMinimumMeasureDangerZone()  + ", " +
                    "max = " + this.getMaximumMeasureDangerZone() + ", " +
                    "phone = " + this.getPhone()  + " " +
                    "WHERE id = 1;");

        }else{
            // Contact table is empty
            dataBase.execSQL("INSERT INTO contact (id, min, max, phone) VALUES ( 1," +
                    this.getMinimumMeasureDangerZone() + ", " +
                    this.getMaximumMeasureDangerZone() + ", " +
                    this.getPhone()  + ");"
            );
        }
        cursor.close();
    }

    public EmergencyContact getFromDB(){
        EmergencyContact contact = EmergencyContact.getInstance();  // Assuring we have a instance of this class
        Cursor cursor = DataBaseManager.getDataBase().rawQuery("SELECT * FROM contact", null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            contact.setMinimumMeasureDangerZone(cursor.getString(cursor.getColumnIndex("min")));
            contact.setMaximumMeasureDangerZone(cursor.getString(cursor.getColumnIndex("max")));
            contact.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            cursor.close();
            return contact;
        }else{
            cursor.close();
            return null;      // if Database don't have a emergency contact, it return null
        }

    }

    public static void sendMessageToEmergencyContact(int measureValue){
        SmsManager smsManager = SmsManager.getDefault();
        EmergencyContact contact = EmergencyContact.getInstance().getFromDB();
        // SMS message needs to be divided in parts of 160 characters max
        ArrayList<String> message;

        String str = MainActivity.getMainContext().getString(R.string.sms_message_pt1) +
            measureValue + MainActivity.getMainContext().getString(R.string.sms_message_pt2);

        message = smsManager.divideMessage(str);

        if(contact != null) {
            // TODO/FIXME: check if it works with no country prefix
            smsManager.sendMultipartTextMessage(contact.getPhone(), null,message, null,null);
        }
    }

    public int getMinimumMeasureDangerZone() {
        return minimumMeasureDangerZone;
    }

    public void setMinimumMeasureDangerZone(String minimumMeasureDangerZone) {
        this.minimumMeasureDangerZone = Integer.parseInt(minimumMeasureDangerZone);
    }

    public int getMaximumMeasureDangerZone() {
        return maximumMeasureDangerZone;
    }

    public void setMaximumMeasureDangerZone(String maximumMeasureDangerZone) {
        this.maximumMeasureDangerZone = Integer.parseInt(maximumMeasureDangerZone);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
