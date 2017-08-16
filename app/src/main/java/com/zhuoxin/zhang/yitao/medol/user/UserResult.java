package com.zhuoxin.zhang.yitao.medol.user;

import com.google.gson.annotations.SerializedName;



public class UserResult {

    private int code;
    @SerializedName("msg")
    private String message;
    @SerializedName("data")
    private User user;

    @Override
    public String toString() {
        return "UserResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
