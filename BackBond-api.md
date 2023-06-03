# BackBond API Docs #

This API allows you to query Daily Treasury
Par Yield Curve Rate JSON data from various
endpoints.

The API is available at `https://floapp.us/BackBond/api/v1`

## Endpoints ##

### Status ###

GET `/status`

Returns the status of the API.

### List of entries ###

GET `/entries`

Returns a list of all entries.

- Path variables:
    - col: Select a specific column of the table to query data from.
- Parameters:
    - startDate: The date to start querying from.
    - endDate: The date to end querying data from.

### Count entries ###

GET `/count`

Returns the number of entries stored within the database.

## API Authentication ##

In order to use the API, you must have the BackBond API key
included in the authorization header of your request.

**Possible errors**

- Status code 401 - "Invalid API Key." You will receive this response if your API key is invalid.