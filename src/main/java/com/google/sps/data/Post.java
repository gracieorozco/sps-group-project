package com.google.sps.data;

public final class Post {

    private final long id;
    private String post_title;
    private final String user_name;
    private String post_content;
    private long time_posted;
    private String email;

    public Post(long id, String title, String user, String content, long time, String email) {
        this.id = id;
        this.post_title = title;
        this.user_name = user;
        this.post_content = content;
        this.time_posted = time;
        this.email = email;
    }

    public long GetID() {
        return this.id;
    }

    public String GetPostTitle() {
        return this.post_title;
    }

    public String GetUserName() {
        return this.user_name;
    }

    public String GetPostContent() {
        return this.post_content;
    }

    public long GetTimePosted() {
        return this.time_posted;
    }

    public String GetEmail() {
        return this.email;
    }

}