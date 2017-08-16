package com.zhuoxin.zhang.yitao.medol.user;

/**
 * Created by Administrator on 2017/8/16.
 */

public class Register {

    /**
     * code : 1
     * msg : succeed
     * data : {"username":"qwer4","name":"yt52e5de5c4bb5453791d091905b8d1827","uuid":"6634D8CC14F64E3585C90941060A4A28","password":"111111"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Register{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * username : qwer4
         * name : yt52e5de5c4bb5453791d091905b8d1827
         * uuid : 6634D8CC14F64E3585C90941060A4A28
         * password : 111111
         */

        private String username;
        private String name;
        private String uuid;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", uuid='" + uuid + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
