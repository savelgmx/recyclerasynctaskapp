package fb.fandroid.adv.recyclerasynctaskapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by Administrator on 16.11.2018.

 3) Добавить логику добавления этого id (через конструктор или сеттер)
 4) Реализовать метод String loadInBackground(), скопировав в него текущую логику onItemClick().
 Соответственно, в этом методе проходит запрос
  к контент провайдеру и возвращается найденный номер или null, если номера нет.
 5) Лоадер готов.
 */



public class ContactsLoader extends AsyncTaskLoader<String> {
/*
 1)  Создать класс - наследник AsyncTaskLoader<String>
    Создадим класс загрузчика, который наследуется от AsyncTaskLoader.
    Наш загрузчик будет возвращать строку, поэтому укажем <String>
*/

    public static final String LOG_TAG = "asynctask";
    public static final String ARG_WORD = "word";
    public static final String ARGS_ID = "args_id";
    private String mIdWord;
    private String id;//Строковое поле для хранения id
    // (который приходит на вход в метод onItemClick()).



    public ContactsLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            mIdWord = args.getString(ARG_WORD);
    }

    @Override

    public String loadInBackground() {
  /*
    loadInBackground() — метод,
    в котором собственно и должна быть создана вся работа по загрузке данных.
*/

        if (mIdWord == null) {
            return null;
        }
        Log.d(LOG_TAG, "loadInBackground");
        return generateOnItemClick(mIdWord);
    }

    private String generateOnItemClick(String mIdWord) {
        //здесь генерируется событие нажатия на


     return null;
    }

    @Override
//    forceLoad() — «принудительная» загрузка новых данных
    public void forceLoad() {
        Log.d(LOG_TAG, "forceLoad");
        super.forceLoad();
    }

    @Override

    //onStartLoading() — срабатывает при запуске загрузчика
    // (но это еще не означает загрузку данных)

    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(LOG_TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        //onStopLoading() — срабатывает при остановке загрузчика
        super.onStopLoading();
        Log.d(LOG_TAG, "onStopLoading");
    }

    @Override
    public void deliverResult(String data) {
       // deliverResult() — получает и возвращает итоговый результат работы загрузчика
        Log.d(LOG_TAG, "deliverResult");
        super.deliverResult(data);
    }



}
