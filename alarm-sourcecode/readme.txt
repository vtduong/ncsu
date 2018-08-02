Intelligent Alarm Application
=============================
You are given an context-aware alarm application. The application
modifies (advances or postpones) a user-set alarm based on various
contextual attributes. In the current implementation, the application 
advances the alarm if the transit system is running late due to adverse 
weather. Your task in this part is to comprehend what the application 
does, how, and why. Accordingly, you need to extend the application 
for the flight scheduler scenario to postpone the alarm if the flight 
is delayed. 

Note that the application is not well tested and may not fully
function. It is necessary for you to read the source code of the
application to determine what is going on. 

Importing the application
=========================
You are given source code of the application in four projects. Three of
them are Java projects and one is an Android project exported from
Eclipse. If you use Android Studio, you can convert to the desired
format.

Running the application
=======================
1. Run the IntelligentAlarmManager project. Use "jade.Boot" as the
main class and "-gui -nomtp alarm-manager:edu.ncsu.csc450.intelligentalarm.IntelligentAlarmManagerAgent"
as program arguments.

2. Run the Notifiers project. Use "edu.ncsu.csc450.notifsystem.Start"
as the main class and "-exitwhenempty true" as program arguments.

3. Run the IntelligentAlarm Android application. Once the application
is launched, you need to first set an alarm and then press the various
buttons to test the scenarios the application seeks to address.

Tasks
========= 
1. Do not postpone the alarm if the flight is not delayed. 
2. Postpone the alarm by 30 minutes if the flight is delayed by 30 minutes.  
3. Postpone the alarm by 60 minutes if the flight is delayed by 60 minutes.