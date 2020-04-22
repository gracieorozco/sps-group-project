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
import com.google.sps.data.Comment;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/comment_data")
public class CServlet extends HttpServlet {

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Variable set up for query
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    // Loop through the query and set properties of a post object to add into an ArrayList
    ArrayList<Comment> comments = new ArrayList<Comment>();
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        //String comment_title = (String) entity.getProperty("comment_title");
        String user_name = (String) entity.getProperty("user_name");
        String comment_content = (String) entity.getProperty("comment_content");
        long timestamp = (long) entity.getProperty("timestamp");
        long post_parent_id = (long) entity.getProperty("post_parent_id");
        Comment comment = new Comment(post_parent_id, id, /*comment_title, */user_name, comment_content, timestamp);
        comments.add(comment);
    }
    // Convert the ArrayList to a string in JSON format and print the response
    String json = convertToJsonUsingGson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form
    String username = request.getParameter("user-input");
    //String title = request.getParameter("title-input");
    String content = request.getParameter("content-input");
    String pid = request.getParameter("reply-id");

    long l_pid = Long.parseLong(pid);

    long timestamp = System.currentTimeMillis();
    // Create an entity and set the properties
    Entity comment_entity = new Entity("Comment");
    comment_entity.setProperty("user_name", username);
    //comment_entity.setProperty("comment_title", title);
    comment_entity.setProperty("comment_content", content);
    comment_entity.setProperty("timestamp", timestamp);
    comment_entity.setProperty("post_parent_id", l_pid);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(comment_entity);
    response.sendRedirect("posts.html");
  }

  public String convertToJsonUsingGson(ArrayList<Comment> comment_arraylist) {
    Gson gson = new Gson();
    String json = gson.toJson(comment_arraylist);
    return json;
  }
}
