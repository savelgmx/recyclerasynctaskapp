package fb.fandroid.adv.recyclerasynctaskapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Administrator on 16.11.2018.
 1) Создать класс - наследник AsyncTaskLoader<String>.
 2) Создать в нем строковое поле для хранения id (который приходит на вход в метод onItemClick()).
 3) Добавить логику добавления этого id (через конструктор или сеттер)
 4) Реализовать метод String loadInBackground(), скопировав в него текущую логику onItemClick().
 Соответственно, в этом методе проходит запрос к контент провайдеру и возвращается найденный номер или null, если номера нет.
 5) Лоадер готов.
 */

public class ContactsLoader extends AsyncTaskLoader<String> {
    public ContactsLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return null;
    }
}
