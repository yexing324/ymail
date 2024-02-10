package org.ymail.entity;

public class UploadResp {
    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UploadResp{" +
                "errno='" + errno + '\'' +
                ", data=" + data +
                '}';
    }

    int errno=0;
    Data data = new Data();
}
class Data {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Data{" +
                "url='" + url + '\'' +
                '}';
    }

    private String url = "http://localhost/ymail/upload/getImg?id=%E3%80%8A%E4%BB%99%E5%89%91%E9%97%AE%E6%83%85%E8%92%99%E7%9C%BC%E5%B0%91%E5%A5%B3%E3%80%8B%E7%82%BC%E4%B8%B9%E7%94%BB%E5%B8%88%204K%E5%A3%81%E7%BA%B8_%E5%BD%BC%E5%B2%B8%E5%9B%BE%E7%BD%91";
}