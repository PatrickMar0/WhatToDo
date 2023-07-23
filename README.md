# csc3400sp22team3
WhatToDo Android App


Our app uses authentication, many parts of the app are accessible without authentication, however there are certain parts that are only accessible or functional to an authenticated user.

We do have a login page, however, due to the nature of how firebase authentication works it requires a SHA key, for most apps when you release them you get a SHA key for the release version. However, since our app is not in release and just in development it uses developer SHA keys, these are unique to each development environment. Because of this in order to use authentication inside of our app, we must add the SHA key of people who want to authenticate while using the development version of the app.

Please Send Chase, preferably on slakc, your SHA-1 key, and he will add it to the project so that you can authenticate on the app.



To get your SHA-1 Key follow these steps:

First, after opening android studio, open the gradle tab, located usually as a tab on the right side of the screen. 

Once open check first if there is a message in blue saying, "Task list not built". If you see this message click on it to open a popup. If this message is not present skip next step.

On the popup unselect "do not build gradle task list during gradle sync". Then click save. Once it has been saved you need to generate your task list by syncing gradle. Either press the gradle sync button, or switch the order of something in a gradle file to force a sync.

Once gradle has been synced, you should have a list of tasks. If you return to the gradle tab, expand the following selections: app -> Tasks -> Android. After expanding these you should be able to see "signingReport". Double click this to start the task.

The Task will run in a terminal and if you scroll through the output there will be a line dedicated to the SHA-1 key. Please paste this and send it to Chase.


Before you do anything else there is something you need to undo, currently the run button is set to just run the  signingReport task, to return it to its standard in which it runs the app please do the following:

At the top of the screen next to the play/run button there should be two dropdowns, one to select an emulator and the other to select the task. Select the task dropdown which is probably displaying signingReport as the selected task. Then choose app from the list of options. Now you can run the app again as normal.


After completing this and when Chase has successfully added you SHA-1 key to the firebase project, you will be able to use the app to its full extent and you can now sign into the app through the login page.

If you have any difficulties or questions please feel free to reach out to Chase.
