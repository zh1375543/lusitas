package com.wzx.tipcontent.bean;

import java.io.Serializable;
import java.util.List;

public class workBean {


    /**
     * code : 200
     * data : [{"id":3,"lineClassIfyID":2,"classIfyName":"红包","title":"看看推荐怎么填","contents":"测试推荐咯","image":"/LineReportPic/20210721215743.jpg","createTime":"2021-07-27T00:00:00","commentCount":0,"status":0},{"id":2,"lineClassIfyID":2,"classIfyName":"红包","title":"继续测试","contents":"测试一下测试一下测试一下测试一下","image":"/LineReportPic/20210721215743.jpg","createTime":"2021-07-21T21:58:13","commentCount":15,"status":0},{"id":1,"lineClassIfyID":1,"classIfyName":"京东","title":"test","contents":"test","image":"/LineReportPic/20210720185142.jpg","createTime":"2021-07-20T18:51:44","commentCount":7,"status":0}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 3
         * lineClassIfyID : 2
         * classIfyName : 红包
         * title : 看看推荐怎么填
         * contents : 测试推荐咯
         * image : /LineReportPic/20210721215743.jpg
         * createTime : 2021-07-27T00:00:00
         * commentCount : 0
         * status : 0
         */

        private int id;
        private int lineClassIfyID;
        private String classIfyName;
        private String title;
        private String contents;
        private String image;
        private String createTime;
        private int commentCount;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLineClassIfyID() {
            return lineClassIfyID;
        }

        public void setLineClassIfyID(int lineClassIfyID) {
            this.lineClassIfyID = lineClassIfyID;
        }

        public String getClassIfyName() {
            return classIfyName;
        }

        public void setClassIfyName(String classIfyName) {
            this.classIfyName = classIfyName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
