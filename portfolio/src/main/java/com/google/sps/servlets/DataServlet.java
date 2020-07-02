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
import com.google.sps.data.Task;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private ArrayList<String> comments;
    
    @Override
    public void init() {
        comments = new ArrayList<String>();
    
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Task> comment_log = new ArrayList<>();
        Query query = new Query("Task");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()) {
            String character_Name = (String) entity.getProperty("character_Name");
            String character_Class = (String) entity.getProperty("character_Class");
            String character_Comment = (String) entity.getProperty("character_Comment");

            response.setContentType("application/json;");
            String json = convertToJson(comments);
            response.getWriter().println(json);
            Task comment = new Task(character_Name, character_Class, character_Comment);
            comment_log.add(comment);
        }
    }

    public String convertToJsonUsingGson(ArrayList<String> comments) {
        Gson gson = new Gson();
        String json = gson.toJson(comments);
        return json;
    }
    private String convertToJson(ArrayList<String> comments) {
        String json = "{";
        json += "\"Name\": ";
        json += "\"" + comments.get(0) + "\"";
        json += ", ";
        json += "\"Class\": ";
        json += "\"" + comments.get(1) + "\"";
        json += ", ";
        json += "\"Comment\": ";
        json += "\"" + comments.get(2) + "\"";
        json += "}";
        return json;
  }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String character_Name = request.getParameter("character-Name");
        String character_Class = request.getParameter("character-Class");
        String character_Comment = request.getParameter("character-Comment");        

        System.out.println(character_Name);
        System.out.println(character_Class);
        System.out.println(character_Comment);
       
        comments.add(character_Name);
        comments.add(character_Class);
        comments.add(character_Comment);
        
        System.out.println(comments.get(0));
        System.out.println(comments.get(1));
        System.out.println(comments.get(2));

        Entity taskEntity = new Entity("Comment");
        taskEntity.setProperty("character_Name", character_Name);
        taskEntity.setProperty("character_Class", character_Class);
        taskEntity.setProperty("character_Comment", character_Comment);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);

        response.sendRedirect("/index.html");
    }
    private String getParameter(HttpServletRequest request, String name) {
    String value = request.getParameter(name);
    if (value == null) {
      return null;
    }
    return value;
  }
    
}
