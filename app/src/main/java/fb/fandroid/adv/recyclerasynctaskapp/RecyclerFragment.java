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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;


/**
 * Created by Administrator on 08.10.2018.
 */

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecycler;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final ContactsAdapter mContactsAdapter = new ContactsAdapter();
    private View mErrorView;
    private Random mRandom = new Random();
    private ContactsAdapter.OnItemClickListener mListener;
   // private RecyclerView.Adapter mMockAdapter;


    public static RecyclerFragment newInstance() {
     return new RecyclerFragment();
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
        mErrorView=view.findViewById(R.id.error_view);

         }


@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//    mRecycler.setAdapter(mMockAdapter);
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


