# Kishikishi
This is an augmented reality app based on a children's book called Kishikishi by Helvi Itenge. This was a group project for the course Emerging Issues in Software Engineering (ESD811S) at Namibia University of Science and Technology (NUST) in 2017. 

The project was developed in Unity using the Vuforia AR plugin. Every page in the book itself is a unique target or marker which the Vuforia camera detects and then places the relevant augmented content on the page so you will need the book to test this out.

This particular repo holds the final project which was exported from Unity and finalized in Android Studio. The Unity export added one activity (UnityPlayerActivity.java) which encapsulates all the AR and animation code. It is not a good idea to edit this file as it is auto generated, instead edit the Unity project and then export again.

To finish it off we added a menu activity (MenuActivity.java) to control the flow and tie everything together as well as a puzzle activity (PuzzleActivity.java) just as an added extra. If you wish to see the original Unity project source code please see the repo https://github.com/BertieC/KishiFinal.git 
