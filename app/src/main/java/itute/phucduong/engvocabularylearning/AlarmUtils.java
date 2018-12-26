package itute.phucduong.engvocabularylearning;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import itute.phucduong.engvocabularylearning.service.SchedulingService;
import itute.phucduong.engvocabularylearning.MainActivity;

public class AlarmUtils {
    public static int INDEX = 0;

    public static void create(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_remind);

        EditText txt = (EditText) dialog.findViewById(R.id.txtTimeRemind);
        int trm = Integer.parseInt(txt.getText().toString());

        Intent intent = new Intent(context, SchedulingService.class);

        for (int i = 0; i < 3; i++) {
            intent.putExtra(Constant.KEY_TYPE, INDEX);
            PendingIntent pendingIntent =
                    PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            INDEX++;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, INDEX);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager
                        .setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + trm, pendingIntent);
            } else {
                alarmManager
                        .set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + trm, pendingIntent);
            }
        }


        /*
        for (int i = 0; i < 3; i++) {

            intent.putExtra(Constant.KEY_TYPE, INDEX);
            PendingIntent pendingIntent = PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            INDEX++;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (trm * 1000), pendingIntent);

            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (trm * 1000), pendingIntent);
            }
*/


            //Toast.makeText(context, "Alarm set in " + trm + " minute(s)", Toast.LENGTH_SHORT).show();

//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.MINUTE, INDEX);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//
//            } else {
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            }

    }
}
