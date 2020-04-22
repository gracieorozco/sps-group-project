package com.google.sps.data;

public final class Comment {

    private final long id;
    //private String comment_title;
    private final String user_name;
    private String comment_content;
    private long time_posted;

    private long post_parent_id;

    public Comment(long post_parent_id, long id, /*String title,*/ String user, String content, long time) {

        this.post_parent_id = post_parent_id;

        this.id = id;
        //this.comment_title = title;
        this.user_name = user;
        this.comment_content = content;
        this.time_posted = time;
    }

    public long GetPostParentId() {
        return this.post_parent_id; 
    }

    public long GetID() {
        return this.id;
    }

    /*
    public String GetCommentTitle() {
        return this.comment_title;
    }
    */

    public String GetUserName() {
        return this.user_name;
    }

    public String GetCommentContent() {
        return this.comment_content;
    }

    public long GetTimePosted() {
        return this.time_posted;
    }

}
