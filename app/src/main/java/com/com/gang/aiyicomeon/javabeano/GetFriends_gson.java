package com.com.gang.aiyicomeon.javabeano;

import java.util.List;

/**
 * Created by Administrator on 2017/4/2.
 */

public class GetFriends_gson {


    /**
     * code : 0
     * message : ok
     * data : {"list":[{"sno":"2014060306003","name":"赵思琦","avatar":"http://10.11.3.177:80/infoCollect/im/x/d/avatar_2014060306003.jpg","id":"s:2014060306003"},{"sno":"2014060306006","name":"张琪爽","avatar":"http://10.11.3.177:80/infoCollect/im/c/d/avatar_2014060306006.jpg","id":"s:2014060306006"},{"sno":"2014060306005","name":"李逍遥","avatar":"http://10.11.3.177:80/infoCollect/im/s/c/avatar_2014060306005.jpg","id":"s:2014060306005"},{"sno":"2014060306004","name":"姬志欣","avatar":"http://10.11.3.177:80/infoCollect/im/e/w/avatar_2014060306004.jpg","id":"s:2014060306004"}],"total":"4"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"sno":"2014060306003","name":"赵思琦","avatar":"http://10.11.3.177:80/infoCollect/im/x/d/avatar_2014060306003.jpg","id":"s:2014060306003"},{"sno":"2014060306006","name":"张琪爽","avatar":"http://10.11.3.177:80/infoCollect/im/c/d/avatar_2014060306006.jpg","id":"s:2014060306006"},{"sno":"2014060306005","name":"李逍遥","avatar":"http://10.11.3.177:80/infoCollect/im/s/c/avatar_2014060306005.jpg","id":"s:2014060306005"},{"sno":"2014060306004","name":"姬志欣","avatar":"http://10.11.3.177:80/infoCollect/im/e/w/avatar_2014060306004.jpg","id":"s:2014060306004"}]
         * total : 4
         */

        private String total;
        private List<ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * sno : 2014060306003
             * name : 赵思琦
             * avatar : http://10.11.3.177:80/infoCollect/im/x/d/avatar_2014060306003.jpg
             * id : s:2014060306003
             */

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
    }
}
