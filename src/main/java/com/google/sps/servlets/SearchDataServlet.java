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
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/search_data")
public class SearchDataServlet extends HttpServlet {

    private String search_string_pass = "";

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

        // If post contains search term, add it to ArrayList
        Post post = new Post(id, post_title, user_name, post_content, timestamp);
        if ((search_string_pass == null) || (search_string_pass == "")) {
            posts.add(post);
        } else {
            if (post_content.contains(search_string_pass)) {
                posts.add(post);
            }
        }
        // long id = entity.getKey().getId();
        // String post_title = (String) entity.getProperty("post_title");
        // String user_name = (String) entity.getProperty("user_name");
        // String post_content = (String) entity.getProperty("post_content");
        // long timestamp = (long) entity.getProperty("timestamp");
        // Post post = new Post(id, post_title, user_name, post_content, timestamp);
        // posts.add(post);
    }

    // Convert the ArrayList to a string in JSON format and print the response
    String json = convertToJsonUsingGson(posts);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String search_string = request.getParameter("search_text_input");
    System.out.println("****** doPost SEARCHING STRING: " + search_string);

    get_search_string(search_string);

    response.sendRedirect("search_results.html");
  }

  public String convertToJsonUsingGson(ArrayList<Post> post_arraylist) {
    Gson gson = new Gson();
    String json = gson.toJson(post_arraylist);
    return json;
  }

  private void get_search_string(String string_input) {
      search_string_pass = string_input;
      return;
  }
}
