# JTimeTracking

JTimeTracking is a plugin to Jira for register worklog time.

To open, double click in jar file JTimeTracking.jar, inside folder JTimeTracking\dist.

To use, you need enter the Jira URL and your credentials. Clicking the "Connect" button will call the Jira API to verify that the data entered is valid. On success, a window will pop up stating that the connection has been made and your user data will appear in the lower left corner.

You need select the board you want, and in doing so, it will list all the active sprints for that board. When you select the sprint, all issues from sprint will be displayed.


In the corresponding issue, you have the following options:
* Issue details: displays content of the issue;
* Start work: starts issuse time counting;
* Stop work: stop/pause issuse time counting;
* <b style='color:red'>*</b> Send to Jira: send data to Jira (in a new interface, where you can get some feedback). 
* Issue work log: shows log of the work done on this issue.

About option "Send to Jira", for now, he's only considering the full minute.
