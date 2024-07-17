# Task Manager

This is a Spring Webflux Reactive CRUD Application that interacts with MongoDB.

## Prerequisites

To run the project you will need to have the following installed

* Java 17
* Docker

This project uses gradle as a build tool. It contains the gradlew wrapper script so there's no need to install gradle.

To interact with the Endpoints I recommend using PostMan.

To visualise the MongoDB I recommend using MongoDB Compass.

## Running

Open a bash terminal at the project directory and use the following commands to set up your environment.

``bash
docker-compose up -d
``
* This will create the mongoDB docker container which is exposed at Port: 27017

``bash
./gradlew bootRun
``
* This will run the WebApp locally and expose the Endpoints at Port: 8084

## Endpoints

``
POST /create/task
``
* Used for creating tasks using a JSON request body payload.

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
* This Endpoint will return an ObjectId generated during the creation of any document in MongoDB which can be used in the following endpoints.

``
GET /get/task/{id}
``
* Used for retrieving a mono (single) Task and its Sub Tasks by id.

``
GET /get/task/all
``
* Used for retrieving a flux (multiple) of all Tasks and their Sub Tasks.

``
PUT /update/task/{id}
``
* Used for updating a single task by passing its id as a parameter.

* This also requires a JSON request body payload.

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
                            "title": "Commit the docker files to github",
                            "description": "git commit -m"
                        }
                    ]
                }
            ]
        }
    ]
}
```
``
DELETE delete/task/{id}
``
* Used to delete a Task by id.


