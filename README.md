# Backend for the Alfresco Export Module

This repository contains the code for the backend of the ERMS Export Module (EEM). A more elaborate description of the EEM can be found on the GitHub repository hosting the code for the [frontend](https://github.com/magenta-aps/erms-export-ui-module).

This backend is a RESTful service written in Java using the framework [Jersey](https://jersey.java.net/).

# Installation

This section describes how to install the backend of the EEM.

## Setting up the database

Before installing the REST service, the DB has to be setup. A MySQL DB is used. The procedure for setting it up is as follows (assuming you are on a Linux server):

1. Install a MySQL or a MariaDB DBMS. Instructions for how to do this can be found elsewhere.
2. Create a DB called (e.g.) "exm" and a user called "eark". You can choose different names by changing these values in the pom.xml. Grant all privileges to the eark user on the exm database.
3. Log in to the DBMS and choose the exm database.
4. Create the tables needed by the EEM by running this script: [setup_db.sql](https://github.com/magenta-aps/E-Ark-ERMS-export-bridge/blob/master/db/setup_db.sql) (this can be done with the following command from the MySQL shell: `mysql> source path/to/setup_db.sql;`)

## Setting backend for development purposes

... more to come.

# Documentation for the frontend developers

This section describes the extraction resources that can be called from the frontend.

## Extracting Resources

In order use the extraction service, use the following resources:
(**NOTE** only one extraction can run at a time)

#### POST /webapi/extraction/extract

Send JSON like this:
(**NOTE** The CMIS nodes in the exportList MUST all be at the same semantic level!)

```
{
  "name": "this-is-the-profile-name",
  "mapName": "this-is-the-name-of-the-mapping-profile",
  "exportList": ["CmisObjectId1", "CmisObjectId3", ...],
  "excludeList": ["CmisObjectId3", "CmisObjectId4", ...]
}
```

In the case of success, you will get a JSON response saying

```
{
	"success": true,
	"message": "Extraction initiated - check /status for error messages"
}
```

In case of an unsuccessful extraction initiation, "success" will be `false` , and you will get a JSON message 
describing what the problem was.

#### GET /webapi/extraction/status

In case of success the backend will respond with a status of 
RUNNING, NOT_RUNNING or DONE. For example:

```
{
	"success": true,
	"message": "RUNNING"
}
```

If an error occured, "success" will be `false` and an appropriate error message will 
be returned.

#### GET /webapi/extraction/terminate

Will terminate a running process and return JSON indicating whether the 
termination was successful or not, i.e. 

```
{
	"success": true,
	"message": "Process terminated"
}
```

or 

```
{
	"success": false,
	"message": "No processes are running"
}
```

or

```
{
	"success": false,
	"message": "Process already done"
}
```

#### POST /webapi/extraction/ead/upload

Use to upload the EAD template. Send the EAD template XML file using multipart/form-data. 
The following key/value pairs are needed:

* key = `eadFile`, value = `name-of-ead-template-file`
* key = `file`, value = `stream-containing-the-file-content`
