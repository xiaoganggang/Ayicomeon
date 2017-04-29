package com.com.gang.aiyicomeon.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.com.gang.aiyicomeon.javabeano.Friend_getFriends;
import com.com.gang.aiyicomeon.zidingyiview.RoundImageView;
import com.example.administrator.ayicomeon.R;
import com.squareup.picasso.Picasso;


import java.util.List;

import me.zhouzhuo.zzletterssidebar.adapter.BaseSortListViewAdapter;
import me.zhouzhuo.zzletterssidebar.viewholder.BaseViewHolder;


/**
 * Created by zz on 2016/8/15.
 */
public class PersonListViewAdapter extends BaseSortListViewAdapter<Friend_getFriends, PersonListViewAdapter.ViewHolder> {
    private Context context;

    public PersonListViewAdapter(Context ctx, List<Friend_getFriends> datas) {
        super(ctx, datas);
        this.context = ctx;
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvName = (TextView) view.findViewById(R.id.list_item_tv_name);
        viewHolder.list_youxiang = (RoundImageView) view.findViewById(R.id.friend_list_touxiang);
        viewHolder.friend_list_num = (TextView) view.findViewById(R.id.friend_list_num);
        return viewHolder;
    }

    @Override
    public void bindValues(ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(mDatas.get(position).getName());
        String touxianglujing = mDatas.get(position).getAvatar();
        Picasso.with(context).load(touxianglujing).into(viewHolder.list_youxiang);
        viewHolder.friend_list_num.setText(mDatas.get(position).getId());
    }

    public static class ViewHolder extends BaseViewHolder {
        protected TextView tvName;
        protected RoundImageView list_youxiang;
        protected TextView friend_list_num;
    }

}
