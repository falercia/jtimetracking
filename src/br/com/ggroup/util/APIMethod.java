package br.com.ggroup.util;

import java.util.HashMap;

/**
 *
 * @author Fabio Garcia
 */
public class APIMethod {

   public static final int RETURN_TYPE_ARRAY = 1, RETURN_TYPE_OBJECT = 2;
   private static APIMethodAbstract allBoards;
   private static APIMethodAbstract allActiveSprintsFromBoard;
   private static APIMethodAbstract allIssuesFromSprint;
   private static APIMethodAbstract addWorklog;
   private static APIMethodAbstract user;
   private static APIMethodAbstract token;

   public static APIMethodAbstract allBoards(HashMap<String, String> params) {
      if (allBoards == null) {
         allBoards = new APIMethodAbstract() {
            @Override
            public String getHTTPVerb() {
               return "GET";
            }

            @Override
            public String getUrl() {
               return "/rest/agile/1.0/board";
            }

            @Override
            public int returnType() {
               return RETURN_TYPE_OBJECT;
            }
         };
      }
      return allBoards;
   }

   public static APIMethodAbstract allActiveSprintsFromBoard(final HashMap<String, String> params) {
      if (allActiveSprintsFromBoard == null) {
         allActiveSprintsFromBoard = new APIMethodAbstract() {
            @Override
            public String getHTTPVerb() {
               return "GET";
            }

            @Override
            public String getUrl() {
               return replaceParameters("/rest/agile/1.0/board/{boardId}/sprint?state=active", params);
            }

            @Override
            public int returnType() {
               return RETURN_TYPE_OBJECT;
            }
         };
      }
      return allActiveSprintsFromBoard;
   }

   public static APIMethodAbstract allIssuesFromSprint(final HashMap<String, String> params) {
      if (allIssuesFromSprint == null) {
         allIssuesFromSprint = new APIMethodAbstract() {
            @Override
            public String getHTTPVerb() {
               return "GET";
            }

            @Override
            public String getUrl() {
               return replaceParameters("/rest/agile/1.0/sprint/{sprintId}/issue?fields=assignee,description,summary,status,worklog", params);
            }

            @Override
            public int returnType() {
               return RETURN_TYPE_OBJECT;
            }
         };
      }
      return allIssuesFromSprint;
   }

   public static APIMethodAbstract addWorklog(final HashMap<String, String> params) {
      if (addWorklog == null) {
         addWorklog = new APIMethodAbstract() {
            @Override
            public String getHTTPVerb() {
               return "POST";
            }

            @Override
            public String getUrl() {
               return replaceParameters("/rest/api/2/issue/{issueIdOrKey}/worklog", params);
            }

            @Override
            public int returnType() {
               return RETURN_TYPE_OBJECT;
            }
         };
      }
      return addWorklog;
   }

   public static APIMethodAbstract getUser(final HashMap<String, String> params) {
      if (user == null) {
         user = new APIMethodAbstract() {
            @Override
            public String getHTTPVerb() {
               return "GET";
            }

            @Override
            public String getUrl() {
               return replaceParameters("/rest/api/2/user?key={key}", params);
            }

            @Override
            public int returnType() {
               return RETURN_TYPE_OBJECT;
            }
         };
      }
      return user;
   }

}
