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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Post;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/data")
public class DataServlet extends HttpServlet {

@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      //set up count initially
      /*
      Entity countEntity = new Entity("Count");
      countEntity.setProperty("count", 0);
      datastore.put(countEntity);
      */

    // Variable set up for query
    Query query = new Query("Post").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    // Loop through the query and set properties of a post object to add into an ArrayList
    ArrayList<Post> posts = new ArrayList<Post>();
    for (Entity entity : results.asIterable()) {
        long id = entity.getKey().getId();
        String post_title = (String) entity.getProperty("post_title");
        String user_name = (String) entity.getProperty("user_name");
        String post_content = (String) entity.getProperty("post_content");
        String email = (String) entity.getProperty("email");
        long unique_id = (long) entity.getProperty("unique_id");
        long timestamp = (long) entity.getProperty("timestamp");
        Post post = new Post(unique_id, id, post_title, user_name, post_content, timestamp, email);
        posts.add(post);
    }
    // Convert the ArrayList to a string in JSON format and print the response
    String json = convertToJsonUsingGson(posts);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //since we're adding another post, we need to update/create the Count entity
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    //Entity countEntity = datastore.get("Count");

    /*
    //get previous count and update
    long count = 0;
    long unique_id = 0;
    Query query = new Query("Count").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    Entity countEntity = null;
    for (Entity entity : results.asIterable()) {
        countEntity = entity;
    }
    count = (long) countEntity.getProperty("count"); 
    unique_id = count + 1;
    countEntity.setProperty("count", count + 1);

    datastore.put(countEntity);
    */

    Query query = new Query("Post").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    long cnt = 0;
    //find out how many posts there are
    for (Entity entity : results.asIterable()) {
        ++cnt;
    }
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        // Get the input from the form
        String username = request.getParameter("user-input");
        String title = request.getParameter("title-input");
        String content = request.getParameter("content-input");
        long timestamp = System.currentTimeMillis();
        String userEmail = userService.getCurrentUser().getEmail();
        // Create an entity and set the properties
        Entity post_entity = new Entity("Post");
        post_entity.setProperty("unique_id", cnt + 1);
        post_entity.setProperty("user_name", username);
        post_entity.setProperty("post_title", title);
        post_entity.setProperty("post_content", content);
        post_entity.setProperty("timestamp", timestamp);

        datastore.put(post_entity);
        response.sendRedirect("index.html");
    } else {
        String urlToRedirectToAfterUserLogsIn = "/login";
        String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
        response.getWriter().println("<p>In order to make a post, you must log in.</p>");
        response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

  }

  public String convertToJsonUsingGson(ArrayList<Post> post_arraylist) {
    Gson gson = new Gson();
    String json = gson.toJson(post_arraylist);
    return json;
  }
}
