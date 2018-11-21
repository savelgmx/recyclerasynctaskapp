package fb.fandroid.adv.recyclerasynctaskapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


/*
перенести запрос к ContentProvider из главного потока в фоновый.


Какой метод нужно использовать: initLoader() или restartLoader()?
Как передать id из onItemClick() в onCreateLoader()?
Лоадер не запускается.
Какой метод нужно вызвать через точку после инициализации лоадера в методе onItemClick(), чтобы заставить его запуститься?

И на сладкое.
Добавьте в MainActivity пункт меню в тулбар. Меню должно быть “ifRoom”
При нажатии на это меню, запрос номера должен останавливаться.
Чтобы успеть нажать на пункт меню после того, как нажали на элемент списка,
добавьте двухсекундную задержку в метод loadInBackground() до запроса к контент провайдеру
через метод TimeUnit.SECONDS.sleep(2).
После отмены запроса, должен появиться тост с текстом “Запрос отменен”
Без самого запроса при нажатии на пункт меню ничего не должно происходить.

http://androiddocs.ru/loaders-ispolzuem-asynctaskloader/
https://medium.com/@sanjeevy133/an-idiots-guide-to-android-asynctaskloader-76f8bfb0a0c0


http://www.androiddocs.com/training/contacts-provider/retrieve-names.html

 */

public class MainActivity extends AppCompatActivity
        implements ContactsAdapter.OnItemClickListener,LoaderManager.LoaderCallbacks<String>{
/*
В MainActivity:
- Добавляем интерфейс LoaderManager.LoaderCallbacks<String>, добавляем и реализуем методы.
- onCreateLoader() должен возвращать созданный нами лоадер.
- В onLoadFinished() добавляем метод для проверки полученного номера (String data)
и запускаем звонок или показываем тост с ошибкой, если номера нет.
- В методе onItemClick() запускаем вызываем LoaderManager и инициализируем Loader.
*/
public static final String LOG_TAG="asynctask";

    private Bundle mBundle;
    public static final int LOADER_ID = 1;
    private Loader<String> mLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBundle = new Bundle();
        mBundle.putString(ContactsLoader.ARG_WORD, "test");
        mLoader = getSupportLoaderManager().initLoader(LOADER_ID, mBundle, this);

      //  mBundle.putString(ContactsLoader.ARGS_ID,id);

   //  }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }


    }

    @Override
    public void onItemClick(String id) {


 /*       Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{id, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

            if (cursor!=null&&cursor.moveToFirst()){
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                cursor.close();
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+number)));

            }
*/
        mBundle.putString(ContactsLoader.ARGS_ID, id);
        if(getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().restartLoader(0, mBundle, this).forceLoad();
        }else{
            getSupportLoaderManager().initLoader(0, mBundle, this).forceLoad();
        }
    }

    @NonNull
    @Override


    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

/*
        добавляем и реализуем методы.
        - onCreateLoader() должен возвращать созданный нами лоадер.
 Loader<String> mLoader = null;
            mLoader = new ContactsLoader(this, args);
        return mLoader;
        */
        Log.d(LOG_TAG, "onCreateLoader");
        return new ContactsLoader(this,args);
     }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

 /*       - В onLoadFinished() добавляем метод для проверки полученного номера (String data)
        и запускаем звонок или показываем тост с ошибкой, если номера нет.
              */
        if(data != null){
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + data)));
        }else{
            Toast.makeText(this, "ERROR empty number", Toast.LENGTH_SHORT).show();
        }
        Log.d(LOG_TAG,"Load finished");

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(LOG_TAG,"Loader reset");

    }
}
