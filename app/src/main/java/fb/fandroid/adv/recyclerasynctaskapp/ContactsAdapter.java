package fb.fandroid.adv.recyclerasynctaskapp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import fb.fandroid.adv.recyclerasynctaskapp.mock.Mock;


/**
 * Created by Administrator on 09.10.2018.
 * MockAdapter
 * Адаптер нужен, чтобы снабжать RecyclerView данными.
 * Он – своего рода посредник между объектами,
 * о которых RecyclerView ничего не знает, и самим RecyclerView.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {

    private final List<Mock> mMockList= new ArrayList<>();
    private Cursor mCursor;
    private OnItemClickListener mListener;

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_mock, parent, false);
        return new ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        if(mCursor.moveToPosition(position)){
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
            holder.bind(new Mock(name, id));
            holder.setListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        if (cursor != null && cursor != mCursor) {
            if (mCursor != null) mCursor.close();
            mCursor = cursor;
            notifyDataSetChanged();
        }

    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }


}
