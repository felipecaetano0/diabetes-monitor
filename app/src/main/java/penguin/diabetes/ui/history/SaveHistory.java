package penguin.diabetes.ui.history;


import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import penguin.diabetes.R;
import penguin.diabetes.activities.MainActivity;
import penguin.diabetes.db.DataBaseManager;
import penguin.diabetes.ui.measure.Measure;

public class SaveHistory implements View.OnClickListener {
    @Override
    public void onClick(View view) {

        LinkedList<Measure> measureLinkedList = DataBaseManager.getMeasures();

//        File path = MainActivity.getMainContext().getFilesDir();
        File path = new File("/storage/self/primary/Download");
        try{
            FileOutputStream writer = new FileOutputStream(new File(path, "DiabetesHistory.txt"));

            for (Measure measure : measureLinkedList) {
                writer.write(measure.toHumanReadableString().getBytes());
                writer.write("\n".getBytes());
            }

            writer.close();
            Toast.makeText(MainActivity.getMainContext(), R.string.history_toast_indicating_folder_to_save, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(MainActivity.getMainContext(), R.string.history_toast_saving_failure, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

//        File file = new File(Environment.getExternalStorageDirectory() +
//                File.separator + "DiabetesHistory.txt");
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            OutputStream fileOutputStream = new FileOutputStream(file);
//            for (Measure measure : measureLinkedList) {
//                fileOutputStream.write(measure.toHumanReadableString().getBytes());
//            }
//            fileOutputStream.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
