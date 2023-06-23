# BackBond API Docs #

This API allows you to query Daily Treasury Par Yield Curve Rate JSON data from various endpoints.

The API is available at `https://floapp.us/BackBond/api/v1`

## Endpoints ##

### Status ###

GET `/status`

Returns the status of the API.

### Count entries ###

GET `/count`

Returns the number of entries stored within the database.

### List of entries ###

GET `/entries`

Returns a list of all entries.

Request parameters:
* **OPTIONAL** startDate: the starting date to query from
* **OPTIONAL** endDate: the date to stop the query at
 
### Select column ###

GET `/entries/{col}`

This endpoint returns the interest rate data for a specific column of the table in the database.

These are all the allowed input values for the path variable `col`:

* BC_1MONTH
* BC_2MONTH
* BC_3MONTH
* BC_4MONTH
* BC_6MONTH
* BC_1YEAR
* BC_2YEAR
* BC_3YEAR
* BC_5YEAR
* BC_7YEAR
* BC_10YEAR
* BC_20YEAR
* BC_30YEAR

This endpoint also includes the start-end date range querying featured in the `/entries` endpoint.

## API Authentication ##

In order to use the API, you must have the BackBond API key
included in the authorization header of your request.

**Possible errors**

- Status code 401—"Invalid API Key." You will receive this response if your API key is invalid.
