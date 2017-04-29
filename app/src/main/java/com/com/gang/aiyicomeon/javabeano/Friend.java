package com.com.gang.aiyicomeon.javabeano;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Friend {
    public String mUserid;
    public String mName;
    public String mTouxiang;

    public Friend(String mUserid, String mName, String mTouxiang) {
        this.mUserid = mUserid;
        this.mTouxiang = mTouxiang;
        this.mName = mName;
    }

    public String getmUserid() {
        return mUserid;
    }

    public void setmUserid(String mUserid) {
        this.mUserid = mUserid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTouxiang() {
        return mTouxiang;
    }

    public void setmTouxiang(String mTouxiang) {
        this.mTouxiang = mTouxiang;
    }
}
