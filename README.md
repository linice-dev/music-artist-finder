# Top 5 albums of my favourite artist

This service lets a user to search artist, mark it as a favourite artist and see top 5 albums of that artist.

Complete application would have two pages:

1. Search and save a favourite artist
    1. Endpoints for this functionality:
        * `/artists?query={keyword}` GET endpoint, used to get artist list for provided query keyword
        * `/user/{userId}/save-favourite-artist` POST endpoint with request body similar to
          ```javascript
          {
              favouriteArtistId: {favouriteArtistId}
          }
          ```
          It saves user's favourite artist into database, from which the data can be retrieved later using user's favourite artist endpoint.
1. View top 5 albums of a selected favourite artist
    1. Endpoints for this functionality:
        * `/user/{userId}/favourite-artist` GET endpoint, used to get user's favourite artist id.
        * `/artist/{artistId}/top-albums` GET endpoint, used to get artist's top 5 albums. It returns response in json format either from iTunes API, or from
          database, which serves as data cache needed to minimize iTunes API and increase service response time and robustness

Since iTunes allows calling them only 100 times per hour, whereas we have 30 million users / month, there is a counter, saved in database, which can be fetched
by only one request at a time. This is implemented using database record pessimistic lock in order to avoid situation where multiple requests acquire counter,
which is near limit and exceeds limit, because one request does not see changes made by other request at the same time.

Also, to minimize iTunes request count, spring cache is used for iTunes http client.

# What was used in this service

1. Java
1. Spring framework
1. Spring Boot
1. Liquibase for database versioning
1. Immutables.org for values object construction
1. Jackson for values object serialization and deserialization

# Architecture

1. This service is uses domain driven design
1. This service has hexagonal (onion) architecture
1. Business functionality is covered with test cases for business logic (based on ATTD for acceptance criteria), MVC tests are used to test correctness of
   requests and responses structures. 
    