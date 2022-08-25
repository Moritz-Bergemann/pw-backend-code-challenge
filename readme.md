Assumptions made:
- Input validation:
  - Postcodes can be any non-negative integer.
  - Names must be at least 1 character and up to 100 characters long.
  - Battery capacity cannot be negative
  - 2 given batteries can have the same name, postcode, and wattage capacity.

Other Notes (Flesh out later):
- Chose to have no batteries found return empty list 200 rather than 404 - as long as the query was still valid the 
  - request is also valid.

Current Issues:
- The use of `ResponseStatusException` allowed for the quick implementation of informative error messages, but required `server.error.include-message=always` to be set in `application.properties`. This is a security concern as it could lead to the leaking of internal errors. This could be adjusted by moving to the use of a more fine-grained rest error handling system, such as `ControllerAdvice`.
- The use of annotation-based input validation on `AddBatteryDto` allowed for the quick implementation of input validation, but does not provide informative error messaging and leaks some internal representations. This could be fixed by programatically checking input objects and using custom error handlers as above.