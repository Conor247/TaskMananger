# Task Manager

This is a Spring Webflux Reactive CRUD Application that creates, retrieves, updates and deletes Tasks in MongoDB.

## Prerequisites

To run the project you will need to have the following installed

* Java 17
* Docker

This project uses gradle as a build tool. It contains the gradlew wrapper script so there's no need to install gradle.

### Enironment Variables

This is required when building and running in the local environment.

Name ``SPRING_DATA_MONGODB``

Value ``mongodb://admin:admin@localhost:27017``

The docker scripts will set the environment variable value to suit the dockerised version of the application + mongodb.
## Testing

Open a bash terminal at the project directory and use the following commands to run tests and generate reports.
* Test reports can be found in build>>reports, open the respective index.html files in a browser to view them.
```bash
./gradlew test
````
* Run all JUnit tests.

```bash
./gradlew jacocoTestReport
````
* Generates Jacoco test report on the line coverage.

```bash
./gradlew pitest
````
* Generates pitest report on the line coverage and mutation coverage.


## Running

Open a bash terminal at the project directory and use the following commands to set up your environment.
```bash
./gradlew clean build
````
* Ensures a clean build of the Task Manager Web App is done.

```bash
docker-compose up --build
```
* This will create the docker images, volumes and containers for
* * Task Manager WebApp and expose the Endpoints at Port: ``8084``
* * MongoDB and expose it at Port: ``27017``

```bash
docker-compose up -d
```
* This will start the existing docker images associated with Task Manager and MongoDB
* It is not required after running the compose up --build command as it will already be running.

```bash
docker-compose down
```
* Used to stop and remove Docker containers, networks, volumes, and images created by the docker-compose script.
```bash
./gradlew bootRun
```
* Unnecessary but will run the WebApp locally and expose the Endpoints at Port: ``8084``

## Endpoints

``
POST /create/task
``
* Create tasks and subtasks at once using a JSON request body payload.
* * The subtasks are given a formatted id when a Task is created.

```json
{
    "title": "Create Task Manager App",
    "description": "Create a task manager app in which you use ReactiveMongoTemplate to interact with the database.",
    "subTasks": [
        {
            "title": "Implement the endpoints in a reactive manner.",
            "description": "Use `mono` and `flux` from Project Reactor.",
            "subTasks": [
                {
                    "title": "Write the Get Controller and Service",
                    "description": "GET"
                },
                {
                    "title": "Write the POST Controller and Service",
                    "description": "POST"
                },
                {
                    "title": "Write the DELETE Controller and Service",
                    "description": "DELETE"
                },
                {
                    "title": "Write the UPDATE Controller and Service",
                    "description": "PUT"
                }
            ]
        },
        {
            "title": "Containerise the Java SpringBoot application.",
            "description": "This will make everything very easy to setup on another machine.",
            "subTasks": [
                {
                    "title": "Write the necessary docker files",
                    "description": "This will make everything very easy to setup on another machine.",
                    "subTasks": [
                        {
                            "title": "Write the Dockerfile",
                            "description": "This will make everything very easy to setup on another machine."
                        },
                        {
                            "title": "Write the dockerignore file",
                            "description": "This will make everything very easy to setup on another machine."
                        },
                        {
                            "title": "Write the docker.compose file",
                            "description": "This will make everything very easy to setup on another machine."
                        }
                    ]
                }
            ]
        }
    ]
}
```
* This Endpoint will return an ObjectId generated during the creation of any document in MongoDB which can be used in the subsequent endpoints.

``
POST /create/task/{id}/subtask
``
* Create subtasks using a JSON request body payload and task id.

```json
{
  "title": "New SubTask Title",
  "description": "New SubTask Description"
}
```

``
POST /create/task/{id}/subtask/{subtaskId}
``
* Create nested subtasks using a JSON request body payload, task id and subtask id.

```json
{
  "title": "New Nested SubTask Title",
  "description": "New Nested SubTask Description"
}
```

``
GET /get/task/{id}
``
* Retrieve a mono (single) Task and its Sub Tasks by id.

``
GET /get/task/all
``
* Retrieve a flux (multiple) of all Tasks and their Sub Tasks.

``
PUT /update/task/{id}
``
* Update a single task using a JSON request body payload and task id.

```json
{
  "title": "Updated Title",
  "description": "Updated Description"
}
```

``
PUT /update/task/{id}/subtask/{subtaskId}
``
* Update a SubTask using a JSON request body payload, task id and subtask id.

```json
{
  "title": "Updated Subtask Title",
  "description": "Updated Subtask Description"
}
```

``
DELETE /delete/task/{id}
``
* Delete a Task by id.

``
DELETE /delete/task/{id}/subtask/{subtaskId}
``
* Delete a SubTask by id.


