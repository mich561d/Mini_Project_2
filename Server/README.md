# Advanced Programming - MiniProject 1
Code examples for the advanced programming course

## What is the purpose of this project?

The purpose of this mini project is to showcase a a web server which will use
any class that implements a simple interface as content.

We were to create 3 different endpoints, one that gets all members,
one to get a single member and to create a new member.

This data should be exchanged in JSON format. Read and written to.

### Requirements:

- The web server shall be based on raw socket calls, no other middleware is allowed.
- The server endpoints shall be dynamically extracted from the content class using reflection
- Concurrency in the server should be handled using coroutines.
- Data shall be communicated using JSON.

### How to run / what rest calls are possible?

To start the web server, go to ``src\main\kotlin\mini_p_2\MiniProject1.kt`` and run the file.

##### Get all members
``localhost:4711/member``

##### Get a single member by Id
``localhost:4711/member/id``

##### Put member
``localhost:4711/name=name:id``

To save the member you are updating use:
``localhost:4711/exit``
which also terminates the server.

##### Test these endpoints in either Postman/Insomnia or in your web-browser
