package fb.fandroid.adv.recyclerasynctaskapp;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 16.11.2018.
 */
public class ContactsLoader extends AsyncTaskLoader<String> {
/*
 1)  Создать класс - наследник AsyncTaskLoader<String>
    Создадим класс загрузчика, который наследуется от AsyncTaskLoader.
    Наш загрузчик будет возвращать строку, поэтому укажем <String>
*/

    public static final String LOG_TAG = "asynctask";
    public static final String ARGS_ID = "args_id";
    private String mId;//Строковое поле для хранения id
    // (который приходит на вход в метод onItemClick()).


    public ContactsLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args!=null) mId = args.getString(ARGS_ID);
        Log.d(LOG_TAG,"Constructor ContactsLoader mId="+String.valueOf(mId));
    }

    public void setId(String id) {
        //Добавить логику добавления этого id (через сеттер)
        mId = id;
    }

    @Override

    public String loadInBackground() {

 /*
    loadInBackground() — метод,
    в котором собственно и должна быть создана вся работа по загрузке данных.
 В этом методе проходит запрос к контент провайдеру
 и возвращается найденный номер или null, если номера нет.*/

        Log.d(LOG_TAG,"onLoadisBackground with id="+String.valueOf(mId));

/*
        Чтобы успеть нажать на пункт меню после того, как нажали на элемент списка,
        добавьте двухсекундную задержку в метод loadInBackground() до запроса к контент провайдеру
        через метод TimeUnit.SECONDS.sleep(2).
*/
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*
            Завершать задачу надо нам самим.
            Для этого мы читаем isLoadInBackgroundCanceled() и, если он true, то завершаем метод doInBackground.
            Мы просто добавили проверку isLoadInBackgroundCanceled. Если он возвращает true, то выходим (return)
*/

        if (isLoadInBackgroundCanceled()) {
            Log.d(LOG_TAG, "isLoadInBackgroundCancelled: " + isLoadInBackgroundCanceled());
            return null;
        }


        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{mId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        Log.d(LOG_TAG,"cursor="+String.valueOf(cursor));

        if(cursor!=null & cursor.moveToFirst() )
        {
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d(LOG_TAG,"number="+number);
            cursor.close();
            return number;
        } else return null;

    }

    @Override
    public void forceLoad() {
//    forceLoad() — «принудительная» загрузка новых данных
        Log.d(LOG_TAG, "forceLoad");
        super.forceLoad();
        setId(mId);
    }

    @Override
    protected void onStartLoading() {

        //onStartLoading() — срабатывает при запуске загрузчика
        // (но это еще не означает загрузку данных)

        super.onStartLoading();
        Log.d(LOG_TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        //onStopLoading() — срабатывает при остановке загрузчика
        super.onStopLoading();
        Log.d(LOG_TAG, "onStopLoading");
        cancelLoad();// Отмена текушей задачи загрузчика,если возможно
    }

    @Override
    public void deliverResult(String data) {
        // deliverResult() — получает и возвращает итоговый результат работы загрузчика
        Log.d(LOG_TAG, "deliverResult");
        super.deliverResult(data);
    }

}
