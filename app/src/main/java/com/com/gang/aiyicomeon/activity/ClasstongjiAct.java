package com.com.gang.aiyicomeon.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.com.gang.aiyicomeon.adapter.SimpleAdapter;
import com.example.administrator.ayicomeon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClasstongjiAct extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView_togji;
    private SimpleAdapter mAdapter;
    private List<String> mDates_fenzhong;
    //这是截取每一分钟的list，每个值2位数字
    private List<String> mDate_class_onemin;
    //这是截取每三分钟的list，每个值6位数字
    private List<String> mDates_class;
    private List<Map<String, Object>> xiaoxin_xiangce;
    private String kechengdata = "000000000000001111110000001111111111111111110000000000111111110011001100000011111111111111";
    private String classname_chuan2 = "";
    private String classroom_chuan2 = "";
    private String classteacher_chuan2 = "";
    private TextView jilu_classname, jilu_classroom, jilu_classteacher;
    private ImageView jiluclose;
    private LinearLayout lin_chagestate, lin_chageshuaxin;
    private int onclickstate = 0;
    private final static String TAG = "ClasstongjiAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classtongji);
        initView();
        initDate_fenzhong();
        initDate_class();
        indates();
        //initDate_class_onemin();
        mAdapter = new SimpleAdapter(ClasstongjiAct.this, xiaoxin_xiangce);
        mRecyclerView_togji.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SimpleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String date) {
                //显示每分钟细节的弹出框
                final Dialog dialog = new Dialog(ClasstongjiAct.this,
                        R.style.ActionSheetDialogStyle);
                dialog.setContentView(R.layout.dialog_minxiangqing3);
                //将对话框的大小按屏幕大小的百分比设置
                dialog.show();
            }
        });

        //切换到水平的gridview
        mRecyclerView_togji.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        jilu_classname.setText(classname_chuan2);
        jilu_classroom.setText(classroom_chuan2);
        jilu_classteacher.setText(classteacher_chuan2);
    }

    private void initView() {
        mRecyclerView_togji = (RecyclerView) findViewById(R.id.ayitecanrecycle1);

        jiluclose = (ImageView) findViewById(R.id.jilu_close);
        jiluclose.setOnClickListener(this);
        lin_chagestate = (LinearLayout) findViewById(R.id.lin_changestate);
        lin_chagestate.setOnClickListener(this);
        lin_chageshuaxin = (LinearLayout) findViewById(R.id.lin_changestate);
        lin_chageshuaxin.setOnClickListener(this);
    }



    /*  private void ineDate_class() {
          mDates_class = new ArrayList<String>();
          for (int i = 1; i <= 45; i++) {
              //截取从首字符起begin个单位s＝s.substring(int begin)
              String jiequclass_one = kechengdata.substring(2);
              String jiequclass_hou = "删除截取到的字符串";
              mDates_fenzhong.add(jiequclass_one);

          }
      }*/

    /**
     * 下面三个方法中的死数据将来是从后台获取的
     */
    //构造死数据，对应的是分钟数
    private void initDate_fenzhong() {
        mDates_fenzhong = new ArrayList<String>();
        for (int i = 1; i <= 15; i++) {
            mDates_fenzhong.add(i + "分");
        }
    }

    //构造死数据，对应的是每3分钟的状态
    private void initDate_class() {
        mDates_class = new ArrayList<String>();
        for (int i = 1; i <= kechengdata.length() - 6; i = i + 6) {
            mDates_class.add(kechengdata.substring(i - 1, i + 5));
        }
    }

    //截取每一分钟两个数字的list，用来做一个纪律判定旷课、迟到、早退
    /*private void initDate_class_onemin() {
        //前10分钟，后10分钟，中间25分钟分别包含的1的个数初始化
        int front_min10 = 0;
        List<String> data_front_min10 = new ArrayList<>();
        int later_min10 = 0;
        List<String> data_later_min10 = new ArrayList<>();
        int middle_min25 = 0;
        List<String> data_middle_min125 = new ArrayList<>();
        mDate_class_onemin = new ArrayList<>();
        for (int i = 1; i <= kechengdata.length(); i = i + 2) {
            mDate_class_onemin.add(kechengdata.substring(i - 1, i + 1));
        }
        LogUtil.d(TAG, "我先看看代码是先执行完for循环，还是同时执行" + mDate_class_onemin.size());
        for (int i = 0; i < mDate_class_onemin.size(); i++) {
            //这是取到了前10分钟的list数据
            if (i <10) {
                data_front_min10.add(mDate_class_onemin.get(i));
                for (int j = 0; j < 10; j++) {
                    if (data_front_min10.get(j).equals("11"))
                        front_min10++;
                }
            }//这是取到了后10分钟的数据
            else if (i > mDate_class_onemin.size() - 35) {
                data_later_min10.add(mDate_class_onemin.get(i));
                for (int j = 0; j < 10; j++) {
                    if (data_later_min10.get(j).equals("11"))
                        later_min10++;
                }
            }//这是中间剩余25分钟的数据
            else {
                data_middle_min125.add(mDate_class_onemin.get(i));
                for (int j = 0; j < data_middle_min125.size(); j++) {
                    if (data_middle_min125.get(j).equals("11"))
                        middle_min25++;
                }
            }
        }
        LogUtil.d(TAG, "前10分钟" + front_min10 + "后10分钟" + later_min10 + "中间2分钟" + middle_min25);
    }*/

    //构造死数据，对应的是课程
    private void initDate_lala() {
        //省略一会的课程名称先写死
    }

    private void indates() {
        xiaoxin_xiangce = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 14; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("分钟数", mDates_fenzhong.get(i));
            map.put("每分钟学生状态", mDates_class.get(i));
            map.put("课程名称", "计算机科学与技术");
            xiaoxin_xiangce.add(map);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jilu_close:
               /* Intent intent = new Intent();
                intent.setClass(ClasstongjiAct.this, MineKebiaoAct.class);
                startActivity(intent);*/
                finish();
                break;
            case R.id.lin_changestate:
                //切换到gridview
                mRecyclerView_togji.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            default:
                break;
        }
    }
}
