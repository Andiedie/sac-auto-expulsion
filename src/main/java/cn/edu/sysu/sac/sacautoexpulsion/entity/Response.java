package cn.edu.sysu.sac.sacautoexpulsion.entity;

public class Response<T> {
    private String errmsg = "";
    private T data = null;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response(String errmsg, T data) {

        this.errmsg = errmsg;
        this.data = data;
    }

    public Response(String errmsg) {

        this.errmsg = errmsg;
    }

    public Response() {
    }
}
