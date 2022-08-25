# Powerledger Backend Challenge
This API is an implementation of the Powerledger Backend API Challenge

## Running and installation
To run the API, use Maven:
```shell
mvn clean run
```

To run unit tests:
```shell
mvn clean test
```

## API Documentation
The API exposes 2 endpoints - `POST` and `GET` for `/api/battery`.

### `/api/battery` - `POST`
Add a list of batteries to the database, the batteries must be defined in the post body.

**`POST` request to `/api/battery`:**
```json
{
    "batteries": [
        {
            "name": "battery1",
            "postcode": 100,
            "wattageCapacity": 10.0
        },
      {
        "name": "battery2",
        "postcode": 102,
        "wattageCapacity": 20.0
      },
      // ...
    ]
}
```

The API will then return the produced battery objects, including unique IDs (currently not usable, implemented for future extensibility.)

```json
{
    "batteries": [
        {
            "id": 1,
            "name": "battery1",
            "postcode": 101,
            "wattageCapacity": 10.0
        },
        {
            "id": 2,
            "name": "battery2",
            "postcode": 102,
            "wattageCapacity": 20.0
        },
        // ...
    ]
}
```

### `/api/battery` - `GET`
Retrieve a list of batteries (as well as statistics) from the database based on query-parameters postcode range.

**`GET` request to `/api/battery?startPostcode=101&endPostcode=103**
<no body>

The above query would result in the following output (given batteries are defined as in `POST` request):
```json
{
    "averageWattageCapacity": 20.0,
    "totalWattageCapacity": 60.0,
    "batteryNames": [
        "battery1",
        "battery2",
        //...
    ]
}
```

## Other details
### Assumptions made
- Input validation:
  - Postcodes can be any non-negative integer.
  - Names must be at least 1 character and up to 100 characters long.
  - Battery capacity cannot be negative.
  - 2 given batteries **can** have the same name, postcode, and wattage capacity.
  - 

### Design decisions
- I chose to have no batteries found for a postcode request return empty list 200 rather than 404 - as long as the query was still valid the request is also valid.\
- The database is currently an in-memory H2 database. The implementation would allow for switching to a different non in-memory with minimal changes.
- 

### Current issues
- The use of `ResponseStatusException` allowed for the quick implementation of informative error messages, but required `server.error.include-message=always` to be set in `application.properties`. This is a security concern as it could lead to the leaking of internal errors. This could be adjusted by moving to the use of a more fine-grained rest error handling system, such as `ControllerAdvice`.
- The use of annotation-based input validation on `AddBatteryDto` allowed for the quick implementation of input validation, but does not provide informative error messaging and leaks some internal representations. This could be fixed by programatically checking input objects and using custom error handlers as above.
- Unit tests for the `BatteryRepository` were not implemented due to time limitations.