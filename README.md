# BackBond 

BacBond is an application designed to store and provide access to government interest data through specific endpoints for client queries.

## Purpose

The motivation behind developing BackBond originated from the need to create a more insightful and user-friendly interface for accessing government interest rate data. The existing presentation of this information on the [official government website](https://home.treasury.gov/resource-center/data-chart-center/interest-rates/TextView?type=daily_treasury_yield_curve) was found to be less intuitive. BackBond works hand in hand with its frontend counterpart, *FrontBond*, which provides a visually appealing and easy-to-use interface to interpert government interest data effectively.

## Schema

Understanding the database structure is important for effective data querying. The current database structure is relatively simple, consisting a single table:

|Key|
|---|
|BC_1MONTH|
|BC_2MONTH| 
|BC_3MONTH|
|BC_4MONTH|
|BC_6MONTH|
|BC_1YEAR|
|BC_2YEAR|
|BC_3YEAR|
|BC_5YEAR|
|BC_7YEAR|
|BC_10YEAR|
|BC_20YEAR|
|BC_30YEAR|

These keys will later be represented as the `col` value in the API documentation.

## API Docs ##

BackBond functions as a RESTful API, delivering data in JSON format which ensures ease of parsing.

### Authentication ###

To access the API, your request must include the BackBond API key in the authorization header. Failure to provide a valid API key will result in a 401 status code with the message "Invalid API Key."

**Possible errors**

- Status code 401—"Invalid API Key." You will receive this response if your API key is invalid.

### Status ###

GET `/status`

* **Status code 200-"OK"**: the API is working properly
* **Other status codes**: something is not right

### Count entries ###

GET `/count`

Retreives the total number of entries stored in the database.

### List of entries ###

GET `/entries`

Request parameters:

* **OPTIONAL** startDate: the starting date to query from. Ends at the current date if no `endDate` is provided.
* **OPTIONAL** endDate: end date for the query. Starts from the first entry if `startDate` is not provided.

### Select column ###

GET `/entries/:col`

Request parameters:

* **OPTIONAL** startDate: the starting date to query from. Ends at the current date if no `endDate` is provided.
* **OPTIONAL** endDate: end date for the query. Starts from the first entry if `startDate` is not provided.

## Conclusion

Thanks for visiting BackBond. Any suggestions or other concerns can be submitted under the [issues](https://github.com/deposit-closet/BackBond/issues) tab of the repository. Any feedback is appreciated!
