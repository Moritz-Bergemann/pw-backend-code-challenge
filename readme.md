Assumptions made:
- Input validation:
  - Postcodes can be any non-negative integer.
  - Names must be at least 1 character and up to 100 characters long.
  - Battery capacity cannot be negative
  - 2 given batteries can have the same name, postcode, and wattage capacity.

Other Notes (Flesh out later):
- Chose to have no batteries found return empty list 200 rather than 404 - as long as the query was still valid the 
  - request is also valid.