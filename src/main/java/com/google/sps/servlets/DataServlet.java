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

import com.google.gson.Gson;
import com.google.sps.data.Post;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private ArrayList<Post> posts = new ArrayList<Post>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Post temp_post = new Post("Ana", "English Revision", "Could someone revise my essay before next week?");
    posts.add(temp_post);
    temp_post = new Post("Ben", "Math Problems", "Does anyone know how to solve problem 3 on page 345?");
    posts.add(temp_post);
    temp_post = new Post("Connor", "Computer Science Help", "How should I get started in website development?");
    posts.add(temp_post);
    temp_post = new Post("David", "Physics Question", "How should I study for the next exam?");
    posts.add(temp_post);
    temp_post = new Post("Ellen", "History Study Guide", "Who wants to study together for the midterm?");
    posts.add(temp_post);
    temp_post = new Post("Flora", "Psychology Vocabulary", "Does someone have the vocabulary for chapter 5?");
    posts.add(temp_post);
    String json = convertToJsonUsingGson(posts);
    response.setContentType("application/json;");
    response.getWriter().println(json);

  }

  public String convertToJsonUsingGson(ArrayList<Post> post_arraylist) {
    Gson gson = new Gson();
    String json = gson.toJson(post_arraylist);
    return json;
  }
}
