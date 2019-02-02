package cc.urowks.ulibrary.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class InsworksBaseAdapter<T> extends BaseAdapter {
    public String TAG = getClass().getSimpleName();
    public ArrayList<T> datas;

    public InsworksBaseAdapter(ArrayList<T> datas) {
        this.datas = datas;
    }

    /**
     * 这个方法肯定不会返回null，最多返回datas.isEmpty()
     *
     * @return
     */
    public ArrayList<T> getDatas() {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        return datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), getItemLayoutId(position), null);
            holder = createViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = convertView.getTag();
        }
        T data = datas.get(position);
        showData(position, holder, data);
        return convertView;
    }

    /**
     * 获取item布局 id
     *
     * @param position
     * @return item布局id
     */
    public abstract int getItemLayoutId(int position);

    /**
     * 创建ViewHolder
     *
     * @param convertView
     * @param position
     * @return 返回ViewHolder对象
     */
    public abstract Object createViewHolder(View convertView, int position);

    /**
     * 展示数据
     *
     * @param position
     * @param viewHolder
     * @param data
     */
    public abstract void showData(int position, Object viewHolder, T data);


}











