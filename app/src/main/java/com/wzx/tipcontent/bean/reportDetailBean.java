package com.wzx.tipcontent.bean;

import java.io.Serializable;
import java.util.List;

public class reportDetailBean {


    /**
     * code : 200
     * data : {"id":23,"lineClassIfyID":1,"classIfyName":"京东","title":"你要知道\u201c并不是每一场相遇都会有结局\u201d,但每一场相遇都会有它的意义","contents":"你要知道\u201c并不是每一场相遇都会有结局\u201d,但每一场相遇都会有它的意义","image":"/LineReportPic/20210802232249.jpg","createTime":"2021-08-02 23:22:50","commentCount":15,"status":0,"commentsdata":[{"id":42,"contents":"哈哈","createTime":"2021-08-04 09:56:02"},{"id":41,"contents":"你好","createTime":"2021-08-03 23:09:30"},{"id":40,"contents":"你好","createTime":"2021-08-03 23:08:48"},{"id":39,"contents":"你好","createTime":"2021-08-03 20:33:00"},{"id":38,"contents":"你好","createTime":"2021-08-03 20:30:42"},{"id":37,"contents":"很好","createTime":"2021-08-03 15:20:28"},{"id":36,"contents":"很好","createTime":"2021-08-03 15:16:51"},{"id":34,"contents":"很好","createTime":"2021-08-03 15:01:08"},{"id":35,"contents":"很好","createTime":"2021-08-03 15:01:08"},{"id":33,"contents":"很好","createTime":"2021-08-03 15:01:02"},{"id":32,"contents":"很好","createTime":"2021-08-03 14:57:06"},{"id":31,"contents":"很好","createTime":"2021-08-03 14:52:59"},{"id":30,"contents":"很好","createTime":"2021-08-03 14:20:18"},{"id":29,"contents":"很好","createTime":"2021-08-03 14:09:09"},{"id":28,"contents":"很好","createTime":"2021-08-03 14:06:22"}],"recommendLineReport":[{"id":4,"title":"时间从来不能阻挡梦想的脚步，有着一份固执的坚持，学会让梦想每天壮大一点点"},{"id":24,"title":"如果重来一次，我就记住初见你的模样，然后躲在人群里，就算再喜欢我也不会出来"},{"id":25,"title":"经历于年龄无关，我讲的是心事，你听得是故事。你所谓的感同身受，不过是隔岸观火"}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 23
         * lineClassIfyID : 1
         * classIfyName : 京东
         * title : 你要知道“并不是每一场相遇都会有结局”,但每一场相遇都会有它的意义
         * contents : 你要知道“并不是每一场相遇都会有结局”,但每一场相遇都会有它的意义
         * image : /LineReportPic/20210802232249.jpg
         * createTime : 2021-08-02 23:22:50
         * commentCount : 15
         * status : 0
         * commentsdata : [{"id":42,"contents":"哈哈","createTime":"2021-08-04 09:56:02"},{"id":41,"contents":"你好","createTime":"2021-08-03 23:09:30"},{"id":40,"contents":"你好","createTime":"2021-08-03 23:08:48"},{"id":39,"contents":"你好","createTime":"2021-08-03 20:33:00"},{"id":38,"contents":"你好","createTime":"2021-08-03 20:30:42"},{"id":37,"contents":"很好","createTime":"2021-08-03 15:20:28"},{"id":36,"contents":"很好","createTime":"2021-08-03 15:16:51"},{"id":34,"contents":"很好","createTime":"2021-08-03 15:01:08"},{"id":35,"contents":"很好","createTime":"2021-08-03 15:01:08"},{"id":33,"contents":"很好","createTime":"2021-08-03 15:01:02"},{"id":32,"contents":"很好","createTime":"2021-08-03 14:57:06"},{"id":31,"contents":"很好","createTime":"2021-08-03 14:52:59"},{"id":30,"contents":"很好","createTime":"2021-08-03 14:20:18"},{"id":29,"contents":"很好","createTime":"2021-08-03 14:09:09"},{"id":28,"contents":"很好","createTime":"2021-08-03 14:06:22"}]
         * recommendLineReport : [{"id":4,"title":"时间从来不能阻挡梦想的脚步，有着一份固执的坚持，学会让梦想每天壮大一点点"},{"id":24,"title":"如果重来一次，我就记住初见你的模样，然后躲在人群里，就算再喜欢我也不会出来"},{"id":25,"title":"经历于年龄无关，我讲的是心事，你听得是故事。你所谓的感同身受，不过是隔岸观火"}]
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
        private List<CommentsdataBean> commentsdata;
        private List<RecommendLineReportBean> recommendLineReport;

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

        public List<CommentsdataBean> getCommentsdata() {
            return commentsdata;
        }

        public void setCommentsdata(List<CommentsdataBean> commentsdata) {
            this.commentsdata = commentsdata;
        }

        public List<RecommendLineReportBean> getRecommendLineReport() {
            return recommendLineReport;
        }

        public void setRecommendLineReport(List<RecommendLineReportBean> recommendLineReport) {
            this.recommendLineReport = recommendLineReport;
        }

        public static class CommentsdataBean {
            /**
             * id : 42
             * contents : 哈哈
             * createTime : 2021-08-04 09:56:02
             */

            private int id;
            private String contents;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class RecommendLineReportBean implements Serializable {
            /**
             * id : 4
             * title : 时间从来不能阻挡梦想的脚步，有着一份固执的坚持，学会让梦想每天壮大一点点
             */

            private int id;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
