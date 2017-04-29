package com.com.gang.aiyicomeon.javabeano;

import me.zhouzhuo.zzletterssidebar.anotation.Letter;
import me.zhouzhuo.zzletterssidebar.entity.SortModel;

/**
 * Created by Administrator on 2017/4/2.
 */

public class Friend_getFriends extends SortModel {

    /**
     * sno : 2014060306003
     * name : 赵思琦
     * avatar : http://10.11.3.177:80/infoCollect/im/x/d/avatar_2014060306003.jpg
     * id : s:2014060306003
     */
    @Letter(isSortField = true)
    private String sno;
    private String name;
    private String avatar;
    private String id;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
