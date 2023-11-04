# BackBond 

This application presists government interest data and allows a client to query it through the provided endpoints.

## Context

I created this application because my dad would view the government interest rate data from the government ran website [here](https://home.treasury.gov/resource-center/data-chart-center/interest-rates/TextView?type=daily_treasury_yield_curve), and by taking one look you can probably tell that it is not very insightful. This sparked the creation for BackBond which works hand in hand with the frontend, *FrontBond*, which provides an easy to use interface to visualizing the government interest rate data in a pretty way.

## API Docs ##

### Status ###

GET `/status`

Status of API.

* Status code 200-"OK" the API is working properly
* Anything else- something is not right

### Count entries ###

Number of entries stored in the database.

GET `/count`

### List of entries ###

All data throughout every entry.

GET `/entries`

Request parameters:
* **OPTIONAL** startDate: the starting date to query from
    - Ends at the current date if no `endDate` is provided
* **OPTIONAL** endDate: the date to stop the query at
    - Starts from the first entry if no `startDate` is provided

### Select column ###

Interest rate data for a specific column.

GET `/entries/:col`

Request parameters:
* **OPTIONAL** startDate: the starting date to query from
    - Ends at the current date if no `endDate` is provided
* **OPTIONAL** endDate: the date to stop the query at
    - Starts from the first entry if no `startDate` is provided

## API Authentication ##

In order to use the API, you must have the BackBond API key
included in the authorization header of your request.

**Possible errors**

- Status code 401—"Invalid API Key." You will receive this response if your API key is invalid.
