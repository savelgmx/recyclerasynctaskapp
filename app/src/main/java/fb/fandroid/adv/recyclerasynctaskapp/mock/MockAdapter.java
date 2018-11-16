package fb.fandroid.adv.recyclerasynctaskapp.mock;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fb.fandroid.adv.recyclerasynctaskapp.R;

/**
 * Created by Administrator on 09.10.2018.
 * MockAdapter
 * Адаптер нужен, чтобы снабжать RecyclerView данными.
 * Он – своего рода посредник между объектами,
 * о которых RecyclerView ничего не знает, и самим RecyclerView.
 */

public class MockAdapter  extends RecyclerView.Adapter<MockHolder>{
    private final List<Mock> mMockList= new ArrayList<>();
    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    /*
    Для начала нам нужно получить LayoutInflater.
    Его мы можем получить с помощью контекста, который хранится в
    ViewGroup parent.
     */
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());// Теперь у нас есть Inflater.
        View view = inflater.inflate(R.layout.li_mock, parent, false);
        //Inflater нужен для того, чтобы из xml-разметки сделать view.
        return new MockHolder(view); // возвращаем наш ViewHolder,которому передаем на вход созданную view.
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
      /*
    На вход он получает holder, либо созданный,
    либо переиспользованный, и position, то есть позицию объекта из адаптера.
     */
      holder.bind(mMockList.get(position));
    }

  /*  @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
   это для случая когда надо работать с разными холдерами
    }
*/


    @Override
    public int getItemCount() {
        return mMockList.size();
    }

    public void addData(List<Mock> mocks,boolean refresh) {

        if(refresh){
            mMockList.clear();
         }
        mMockList.addAll(mocks);
        notifyDataSetChanged();
    }
}
