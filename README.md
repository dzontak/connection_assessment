# Connection Enterprise - Spring Boot Assessment

### tested on java version

```
openjdk version "1.8.0_292"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_292-b10)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.292-b10, mixed mode)

To configure mulitple jdk version on mac see: 
https://afternoondev.medium.com/installing-java-8-java-11-on-macos-bigsur-with-homebrew-2ff424f1d226
```

### Swagger url

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI definition url

```
http://localhost:8080/api-docs
```

### Running the app

```
./gradlew bootRun
```

### Running tests

```
/gradlew clean build
```

## Endpoints

#### READ ONE:

```
Request:
URL: /movies/{id}
Method: GET
Response:
Returns a movie object equal to the id supplied. 
Example: 
{
    "genres": [
        {
            "id": 1,
            "code": "Action"
        },
        {
            "id": 2,
            "code": "Adventure"
        },
        {
            "id": 3,
            "code": "Sci-Fi"
        }
    ],
    "actors": [
        {
            "id": 4,
            "name": "Chris Pratt"
        },
        {
            "id": 5,
            "name": " Vin Diesel"
        },
        {
            "id": 6,
            "name": " Bradley Cooper"
        },
        {
            "id": 7,
            "name": " Zoe Saldana"
        }
    ],
    "id": 9,
    "movieRank": 1,
    "title": "Guardians of the Galaxy",
    "description": "A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.",
    "releaseYear": 2014,
    "runtime": 121,
    "rating": 8.1,
    "votes": 757074,
    "revenue": 333.13,
    "metaScore": 76,
    "director": {
        "id": 8,
        "name": "James Gunn"
    }
}

The response code is 200, and the response body is a movie object equal to the id provided.
In case there are no such a movie return status code 404.

curl --location --request GET 'http://localhost:8080/movies/9'
```

#### FIND ALL MOVIES:

```
Request:
URL: /movies
Method: GET
Response:
Returns a collection of all movies. 
Example: 
[
    {
        "genres": [
            {
                "id": 1,
                "code": "Action"
            },
            {
                "id": 2,
                "code": "Adventure"
            },
            {
                "id": 3,
                "code": "Sci-Fi"
            }
        ],
        "actors": [
            {
                "id": 4,
                "name": "Chris Pratt"
            },
            {
                "id": 5,
                "name": " Vin Diesel"
            },
            {
                "id": 6,
                "name": " Bradley Cooper"
            },
            {
                "id": 7,
                "name": " Zoe Saldana"
            }
        ],
        "id": 9,
        "movieRank": 1,
        "title": "Guardians of the Galaxy",
        "description": "A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.",
        "releaseYear": 2014,
        "runtime": 121,
        "rating": 8.1,
        "votes": 757074,
        "revenue": 333.13,
        "metaScore": 76,
        "director": {
            "id": 8,
            "name": "James Gunn"
        }
    },
    ...]
    
The response code is 200, and the response body is a list of movies.

curl --location --request GET 'http://localhost:8080/movies'

```

#### FIND MOVIES BY GENRE:

```
Request:
URL: /movies/filter/{genre}
Method: GET
Response:
Returns a collection of all movies equal to the genre supplied. 
Example: 
[
    {
        "genres": [
            {
                "id": 25,
                "code": "Animation"
            },
            {
                "id": 26,
                "code": "Comedy"
            },
            {
                "id": 27,
                "code": "Family"
            }
        ],
        "actors": [
            {
                "id": 28,
                "name": "Matthew McConaughey"
            },
            {
                "id": 29,
                "name": "Reese Witherspoon"
            },
            {
                "id": 30,
                "name": " Seth MacFarlane"
            },
            {
                "id": 31,
                "name": " Scarlett Johansson"
            }
        ],
        "id": 33,
        "movieRank": 4,
        "title": "Sing",
        "description": "In a city of humanoid animals, a hustling theater impresario's attempt to save his theater with a singing competition becomes grander than he anticipates even as its finalists' find that their lives will never be the same.",
        "releaseYear": 2016,
        "runtime": 108,
        "rating": 7.2,
        "votes": 60545,
        "revenue": 270.32,
        "metaScore": 59,
        "director": {
            "id": 32,
            "name": "Christophe Lourdelet"
        }
    }, 
    ....]

The response code is 200, and the response body is a list of movies equal with the genre provided.

curl --location --request GET 'http://localhost:8080/movies/filter/Comedy'
```

#### CREATE ONE:

```
Request:
URL: /movies
Method: POST
Payload (Example): 

{
    "genres": [
        {
            "code": "Drama"
        }
    ],
    "actors": [
        {
            "name": "Brad Pitt"
        },
        {
            "name": "Edward Norton"
        }
    ],
    "movieRank": 1,
    "title": "Fight Club",
    "releaseYear": 1999,
    "runtime": 139,
    "director": {
        "name": "David Fincher"
    }
}


Response:
The request contains an object with all the data about the movie.
The response code is 201 and the response body is the movie object created. 

Example: 
{
    "genres": [
        {
            "id": 47,
            "code": "Drama"
        }
    ],
    "actors": [
        {
            "id": 420,
            "name": "Brad Pitt"
        },
        {
            "id": 1303,
            "name": "Edward Norton"
        }
    ],
    "id": 4059,
    "movieRank": 1,
    "title": "Fight Club",
    "description": null,
    "releaseYear": 1999,
    "runtime": 139,
    "rating": null,
    "votes": null,
    "revenue": null,
    "metaScore": null,
    "director": {
        "id": 480,
        "name": "David Fincher"
    }
}

curl --location --request POST 'http://localhost:8080/movies/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "genres": [
        {
            "code": "Drama"
        }
    ],
    "actors": [
        {
            "name": "Brad Pitt"
        },
        {
            "name": "Edward Norton"
        }
    ],
    "movieRank": 1,
    "title": "Fight Club",
    "releaseYear": 1999,
    "runtime": 139,
    "director": {
        "name": "David Fincher"
    }
}'

```

#### UPDATE ONE:

```
Request:
URL: /movies/{id}
Method: PATCH
Payload (Example): {
    "genres": [
        {
            "code": "Drama"
        },
                {
            "code": "Action"
        }
    ],
    "movieRank": 1,
    "title": "Fight Club",
    "releaseYear": 1999,
    "runtime": 140
}
Response:
Returns the movie object patched. 
Example: {
    "genres": [
        {
            "id": 47,
            "code": "Drama"
        },
        {
            "id": 1,
            "code": "Action"
        }
    ],
    "actors": [
        {
            "id": 420,
            "name": "Brad Pitt"
        },
        {
            "id": 1303,
            "name": "Edward Norton"
        }
    ],
    "id": 4059,
    "movieRank": 1,
    "title": "Fight Club",
    "description": null,
    "releaseYear": 1999,
    "runtime": 140,
    "rating": null,
    "votes": null,
    "revenue": null,
    "metaScore": null,
    "director": {
        "id": 480,
        "name": "David Fincher"
    }
}
The response code is 200 and the response body is the movie object patched.
In case there are no such movies return status code 404.


curl --location --request PATCH 'http://localhost:8080/movies/4059' \
--header 'Content-Type: application/json' \
--data-raw '{
    "genres": [
        {
            "code": "Drama"
        },
                {
            "code": "Action"
        }
    ],
    "movieRank": 1,
    "title": "Fight Club",
    "releaseYear": 1999,
    "runtime": 140
}'
```

#### DELETE ONE:

```
Request:
URL: /movies/{id}:
Method: DELETE
Response:
The response code is 200.
In case there are no such movies return status code 404.
```
