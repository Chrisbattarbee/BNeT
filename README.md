# BNeT

A crude botnet written in Java for educational purposes. The botnet works with in a standard call back to home style. The client simply connects to the server and waits for incoming task objects to execute. The server waits for clients to connect and processes any command that the user of the server would like to run on the client machines. The tasks are easily extensible and just require an implentation of the abstract class Task as well as an implemntation of the corresponding responsehandler to be processed by the server after the execution of the task on the client machine. Examples have been included in the "Tasks" source directory. After a task has been added it simply needs to be added to the main server by extending the case statement located in Task.java.
