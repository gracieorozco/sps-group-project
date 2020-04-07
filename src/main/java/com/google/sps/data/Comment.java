package com.google.sps.data;

public final class Comment {

    private final long id;
    private String comment_content;
    private long time_posted;

    public Comment(long id,  String content, long time) {
        this.id = id;
        this.comment_content = content;
        this.time_posted = time;
    }

    public long GetID() {
        return this.id;
    }

    public String GetCommentContent() {
        return this.comment_content;
    }

    public long GetTimePosted() {
        return this.time_posted;
    }

}
