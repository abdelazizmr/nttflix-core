# NTTFLIX Web Application Made By : [Abdelaziz Mrabet, Houda el Mesbahi]

NTTFLIX is a web application designed for managing movies and categories. It provides an administration interface for managing movies and categories using JavaServer Faces (JSF) Framework. Additionally, it includes a RESTful web service to interact with the movie and category data.

## Features

### Administration Part
- **Movies Management:** Allows administrators to add, edit, and delete movies.
- **Categories Management:** Enables administrators to manage categories, including adding, editing, and deleting categories.

### RESTful Web Service Endpoints

- **/movies:** Endpoint to retrieve a list of all movies.
- **/movies/{id}:** Endpoint to retrieve details of a specific movie by its ID.
- **/categories:** Endpoint to fetch a list of all categories.
- **/categories/{id}/movies:** Endpoint to retrieve movies belonging to a specific category by its ID.
- **/movies/{id}/comments:** Endpoint to retrieve comments related to a movie (pending..).
- **/movies/{id}/comment:** Endpoint to post a comment related to a movie (pending..).

## Frontend Interface

The frontend interface of the application is built using JavaServer Faces (JSF). It provides the following functionalities:

- **Display All Movies:** Lists all movies available in the database.
- **Consult Specific Movie:** Allows users to view details of a specific movie.
- **Select Movies by Category:** Enables users to filter movies by selecting a specific category.

