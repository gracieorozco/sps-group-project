package com.google.sps.data;

public final class Post {

    private final String user_name;
    private String post_title;
    private String post_content;

    public Post(String user, String title, String content) {
        this.user_name = user;
        this.post_title = title;
        this.post_content = content;
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

}