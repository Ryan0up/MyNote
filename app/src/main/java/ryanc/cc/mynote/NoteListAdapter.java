package ryanc.cc.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by ryan0up on 2017/7/3.
 */

public class NoteListAdapter extends BaseAdapter {

    private List<NoteBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public NoteListAdapter(Context context,List<NoteBean> list){
        mContext = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mTvNoteTitle = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.mTvNoteDate = (TextView)convertView.findViewById(R.id.tv_date);
            viewHolder.mTvNoteContent = (TextView)convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder  = (ViewHolder)convertView.getTag();
        }
        NoteBean bean = mList.get(position);
        viewHolder.mTvNoteTitle.setText(bean.getNoteTitle());
        viewHolder.mTvNoteDate.setText(bean.getNoteDate());
        viewHolder.mTvNoteContent.setText(bean.getNoteContent());
        return convertView;
    }

    private static class ViewHolder{
        public TextView mTvNoteTitle;
        public TextView mTvNoteDate;
        public TextView mTvNoteContent;
    }
}
