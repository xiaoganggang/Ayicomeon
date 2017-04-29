package com.com.gang.aiyicomeon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ayicomeon.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 * http://m.blog.csdn.net/article/details?id=50802898学习RecyleView加载不同的view
 */
public class SimpleAdapter1 extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<String> mDates;
    //增加脚布局需要用的这个与listview不同没有添加addView的api
    private static final int TYPE_ITEM = 0;       //普通Item View
    private static final int TYPE_FOOTER = 1;    //底部FootView

    //一个构造函数
    public SimpleAdapter1(Context context, List<String> mDates) {
        this.context = context;
        this.mDates = mDates;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return mDates.size() + 1;    //因为在底部添加了脚布局，所以Count的数量要+1
    }

    //BaseAdapter中还有getViewTypeCount()这个方法。这里我们使用RecyclerView.Adapter。只用getItemViewType就可以了
    public int getItemViewType(int position) {
      /*  if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else {
            return 2;
        }*/
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }

    }

    /*//绑定一个viewholder,在这里就行复制内容显示等
    public void onBindViewHolder(MyViewHolder holder, int position) {
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
    }*/

    //创建一个viewholder
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //判断返回的ViewType是那种类型
        if (viewType == TYPE_ITEM) {
            ItemViewHolder ItemViewHolder = new ItemViewHolder(mInflater.inflate(R.layout.ayitecanitem, parent, false));
            return ItemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            FootViewHolder footViewHolder = new FootViewHolder(mInflater.inflate(R.layout.listview_tecan_bottom, parent, false));
            return footViewHolder;

        }
        return null;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//根据返回的holder判断是普通条目还是脚布局，然后进行设置
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).a.setText(mDates.get(position));
        } else if (holder instanceof FootViewHolder) {

        }
    }


    public static class ItemViewHolder extends ViewHolder {
        TextView a;
        ImageView tecanpc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            a = (TextView) itemView.findViewById(R.id.ayitecanitemtt);
            tecanpc = (ImageView) itemView.findViewById(R.id.tecanpcitem);
        }

    }

    //滑到底的布局
    public static class FootViewHolder extends ViewHolder {
ImageView morejia;
        public FootViewHolder(View itemView) {
            super(itemView);
            morejia=(ImageView)itemView.findViewById(R.id.moretecan);
        }
    }
}




