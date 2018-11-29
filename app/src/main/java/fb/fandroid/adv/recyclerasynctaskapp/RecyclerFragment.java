package fb.fandroid.adv.recyclerasynctaskapp;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 08.10.2018.
 */

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = "asynctask";
    private final ContactsAdapter mContactsAdapter = new ContactsAdapter();
    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ContactsAdapter.OnItemClickListener mListener;

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/*        При нажатии на это меню, запрос номера должен останавливаться.
                После отмены запроса, должен появиться тост с текстом “Запрос отменен”
        Без самого запроса при нажатии на пункт меню ничего не должно происходить.
 */
        inflater.inflate(R.menu.menu_main, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.item2:
                //При нажатии на это меню, запрос номера должен останавливаться.
                Log.d(LOG_TAG,"item2 selected");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ContactsAdapter.OnItemClickListener) {

            mListener= (ContactsAdapter.OnItemClickListener) context;

        }
    }


    @Nullable
    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecycler = view.findViewById(R.id.recycler);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mContactsAdapter);
        mRecycler.addItemDecoration(new CardDecoration());
        mContactsAdapter.setListener(mListener);
    }
    @Override
    public void onRefresh() {

        getLoaderManager().restartLoader(0,null,this);//иницыализируем Лоадер


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                ContactsContract.Contacts._ID
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        mContactsAdapter.swapCursor(data);

        if (mSwipeRefreshLayout.isRefreshing()){//мы еще и убираем индикатор загрузки в SwipeRefreshLayout.
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public void onDetach(){
        mListener=null;
        super.onDetach();
    }
}


