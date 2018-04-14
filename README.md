# coffee-list

A task list that uses a local database for authentication

![Task list screen](http://tomual.com/images/blog/ss%20(2018-02-23%20at%2007.27.38).png)

## Features

* Add and remove tasks
* Check tasks off to indicate completion
* Menu options to:
  * Mark all as complete
  * Mark all as incomplete
  * Delete complete tasks
  * Delete all tasks
* User authentication using local database

## Installation

* Create config.properties at root
* Set properties for database in the following format:
```
database=jdbc:mysql://localhost:3300/dbname
dbuser=root
dbpassword=
```

## Screenshots

![Authentication screen](http://tomual.com/images/blog/ss%20(2018-02-23%20at%2007.26.13).png)

![Add task screen](http://tomual.com/images/blog/thing.png)
