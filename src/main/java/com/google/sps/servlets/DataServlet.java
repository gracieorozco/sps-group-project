// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Post;
import com.google.sps.data.Comment;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Variable set up for query
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Post").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    // Loop through the query and set properties of a post object to add into an ArrayList
    ArrayList<Post> posts = new ArrayList<Post>();
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        String post_title = (String) entity.getProperty("post_title");
        String user_name = (String) entity.getProperty("user_name");
        String post_content = (String) entity.getProperty("post_content");
        long timestamp = (long) entity.getProperty("timestamp");
        //ArrayList<Comment> comments = (List) entity.getProperty("comments");
        Post post = new Post(id, post_title, user_name, post_content, timestamp);
        posts.add(post);
    }
    // Convert the ArrayList to a string in JSON format and print the response
    String json = convertToJsonUsingGson(posts);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form
    //String check = request.getParameter("title-input");
    //if(check != null){
        //we're dealing with a post
        String username = request.getParameter("user-input");
        String title = request.getParameter("title-input");
        String content = request.getParameter("content-input");
        long timestamp = System.currentTimeMillis();
        ArrayList<Comment> comments = new ArrayList<Comment>();
        // Create an entity and set the properties
        Entity post_entity = new Entity("Post");
        post_entity.setProperty("user_name", username);
        post_entity.setProperty("post_title", title);
        post_entity.setProperty("post_content", content);
        post_entity.setProperty("comments", comments);
        post_entity.setProperty("timestamp", timestamp);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(post_entity);
  //  }
    
    /*
    else {
        //we're dealing with a comment 
        //we need to know what post to add on to
        //what post is this?  
        String post_id = request.getParameter("post-id");
        String comment_content = request.getParameter("content-input");
        //then we iterate
        //
        //
        //new comment
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Post").addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);

        //update entity with new comment
        for (Entity entity : results.asIterable()) {
            long cand_id = (long) entity.getProperty("id");
            String cand_id_str = Long.toString(cand_id);
            if(  cand_id_str == post_id ){
                //then this is our post we want
                //we have to add a comment to this
                ArrayList<Comment> n_com = entity.getProperty("comments");
                n_com.add(new Comment(cand_id, comment_content, System.currentTimeMillis()));
                Entity task = Entity.newBuilder(datastore.get( entity.getKey() ) ).set("comments", n_com).build();
                datastore.update(task);
                break;
            }
        }
    }
    */
    response.sendRedirect("posts.html");
 }

  public String convertToJsonUsingGson(ArrayList<Post> post_arraylist) {
    Gson gson = new Gson();
    String json = gson.toJson(post_arraylist);
    return json;
  }
}
