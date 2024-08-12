# Task Manager

Spring Webflux Reactive CRUD Application that creates, retrieves, updates and deletes Tasks in MongoDB.

## Prerequisites

To run the project you will need to have the following installed

* Java 17
* Docker

### Environment Variables

* This is required when building and running in the local environment.

|        Variable        |                  Value                  |
|:----------------------:|:---------------------------------------:|
| `SPRING_DATA_MONGODB`  | `mongodb://admin:admin@localhost:27017` |

# Testing

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

# Running

Open a bash terminal at the project directory and use the following commands to set up your environment.
```bash
./gradlew clean build
````
* Ensures a clean build of the Task Manager Web App is done.

```bash
docker-compose up --build
```
* This will create the docker images, volumes and containers for
  * Task Manager WebApp and expose the Endpoints at Port: ``8084``
  * MongoDB and expose it at Port: ``27017``

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

# Endpoints

## `Create Task`

* With Subtasks if desired, in one request.
    * Subtasks are given a formatted id.
    * Returns the generated Task ObjectId.

| Key          |  Information   |
|:-------------|:--------------:|
| Request Type |      POST      |
| Request URL  | `/create/task` |
| Header       |                |
| Body Type    |      JSON      |

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
                  "title": "POST",
                  "description": "Write the POST Controller and Service"
                },
                {
                    "title": "GET",
                    "description": "Write the Get Controller and Service"
                },
                {
                    "title": "DELETE",
                    "description": "Write the DELETE Controller and Service"
                },
                {
                    "title": "UPDATE",
                    "description": "Write the UPDATE Controller and Service"
                },
                {
                  "title": "Queries",
                  "description": "Make use of Queries to Get, Update and Delete Multiple Tasks"
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

## `Create Subtask`

| Key          |    Information    |
|:-------------|:-----------------:|
| Request Type |       POST        |
| Request URL  | `/create/subtask` |
| Header       |       `id`        |
| Body Type    |       JSON        |

```json
{
  "title": "New SubTask Title",
  "description": "New SubTask Description"
}
```

##  `Create Nested Subtask`

| Key          |       Information        |
|:-------------|:------------------------:|
| Request Type |           POST           |
| Request URL  | `/create/nested-subtask` |
| Header       |           `id`           |
| Header       |       `subtaskId`        |
| Body Type    |           JSON           |


```json
{
  "title": "New Nested Subtask Title",
  "description": "New Nested Subtask Description"
}
```

## `Get Task`

| Key          | Information |
|:-------------|:-----------:|
| Request Type |     GET     |
| Request URL  | `/get/task` |
| Header       |    `id`     |
| Body Type    |             |


## `Get All Tasks`

| Key          |   Information    |
|:-------------|:----------------:|
| Request Type |       GET        |
| Request URL  | `/get/all-tasks` |
| Header       |                  |
| Body Type    |                  |


## ``Update Task``

| Key          |  Information   |
|:-------------|:--------------:|
| Request Type |      PUT       |
| Request URL  | `/update/task` |
| Header       |      `id`      |
| Body Type    |      JSON      |


```json
{
  "title": "Updated Title",
  "description": "Updated Description"
}
```

## `Update Subtask`

| Key          |    Information    |
|:-------------|:-----------------:|
| Request Type |        PUT        |
| Request URL  | `/update/subtask` |
| Header       |       `id`        |
| Header       |    `subtaskId`    |
| Body Type    |       JSON        |

```json
{
  "title": "Updated Subtask Title",
  "description": "Updated Subtask Description"
}
```

## `Delete Task`

| Key          |  Information   |
|:-------------|:--------------:|
| Request Type |     DELETE     |
| Request URL  | `/delete/task` |
| Header       |      `id`      |
| Body Type    |                |


## `Delete Subtask`

| Key          |    Information    |
|:-------------|:-----------------:|
| Request Type |      DELETE       |
| Request URL  | `/delete/subtask` |
| Header       |       `id`        |
| Header       |    `subtaskId`    |
| Body Type    |                   |
