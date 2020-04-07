package com.google.sps.data;
import java.util.ArrayList;
import com.google.sps.data.Comment;

public final class Post {

    private final long id;
    private String post_title;
    private final String user_name;
    private String post_content;
    private long time_posted;
//    private ArrayList<Comment> comments;

    public Post(long id, String title, String user, String content, long time) {
        this.id = id;
        this.post_title = title;
        this.user_name = user;
        this.post_content = content;
        this.time_posted = time;
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

    /*
    public void addComment(Comment c){
        comments.add(c); 
        return;
    }
    

    public ArrayList<Comment> GetComments() {
        return this.comments;
    }
    */

}
