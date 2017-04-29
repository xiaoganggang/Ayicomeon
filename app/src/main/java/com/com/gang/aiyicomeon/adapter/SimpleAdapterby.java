package com.com.gang.aiyicomeon.adapter;

import android.content.Context;
import android.graphics.Outline;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ayicomeon.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/25.备用
 * http://m.blog.csdn.net/article/details?id=50802898学习RecyleView加载不同的view
 */
public class SimpleAdapterby extends RecyclerView.Adapter<MyViewHolderby> {
    private LayoutInflater mInflater;
    private Context context;
    private List<String> mDates;
    //增加脚布局需要用的这个与listview不同没有添加addView的api
    private static final int TYPE_ITEM = 0;       //普通Item View
    private static final int TYPE_FOOTER = 1;    //底部FootView

    //一个构造函数
    public SimpleAdapterby(Context context, List<String> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return mDates.size();
    }

    //BaseAdapter中还有getViewTypeCount()这个方法。这里我们使用RecyclerView.Adapter。只用getItemViewType就可以了
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    //绑定一个viewholder,在这里就行复制内容显示等
    public void onBindViewHolder(MyViewHolderby holder, int position) {
        holder.a.setText(mDates.get(position));
        //获取Outline
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //修改outlin为特定形状
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };
        holder.tecanpc.setOutlineProvider(viewOutlineProvider);
       /* switch (getItemViewType(position)){
            case 0:
                final ViewHolderOne holderOne = (ViewHolderOne) holder;
                holderOne.text1.setText(beans.get(position).getTxt());
                break;
            case 1:
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
                holderTwo.text1.setText(beans.get(position).getTxt());
                holderTwo.text2.setText(beans.get(position).getTxt());
                break;
            case 2:
                ViewHolderThree holderTwo = (ViewHolderTwo) holder;
                holderThree.text1.setText(beans.get(position).getTxt());
                holderThree.text2.setText(beans.get(position).getTxt());
                break;
        }*/
    }

    //创建一个viewholder
    public MyViewHolderby onCreateViewHolder(ViewGroup parent, int viewType) {
      /*
      *当我们想加载不同的布局RecyleView的时候，可以使用下面的办法
      View view = null;
        ViewHolder holder = null;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case 1:
                view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, parent, false);
                holder = new ViewHolderTwo(view);
                break;
            case 2:
                view = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_2, parent, false);
                holder = new ViewHolderThree(view);
                break;
        }*/

        View view = mInflater.inflate(R.layout.ayitecanitem, parent, false);
        MyViewHolderby viewHolder = new MyViewHolderby(view);
        return viewHolder;
    }
}

class MyViewHolderby extends ViewHolder {
    TextView a;
    ImageView tecanpc;

    public MyViewHolderby(View itemView) {
        super(itemView);
        a = (TextView) itemView.findViewById(R.id.ayitecanitemtt);
        tecanpc = (ImageView) itemView.findViewById(R.id.tecanpcitem);
    }
}