package com.google.sps.data;

public final class Post {

    private final long id;
    private String post_title;
    private final String user_name;
    private String post_content;
    private long time_posted;

    private long unique_id;

    public Post(long unique_id, long id, String title, String user, String content, long time) {

        this.unique_id = unique_id;

        this.id = id;
        this.post_title = title;
        this.user_name = user;
        this.post_content = content;
        this.time_posted = time;
    }

    public long GetUniqueId(){
        return this.unique_id; 
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

}
