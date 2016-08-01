# CA APM Mongo Name Formatter

# Description
Mongo Name Formatter used to show details of operations performed by MongoDB Java Driver. It provides "collection" and "query" parameters to be used in the metric name.

## Short Description
Mongo Name Formatter used to show details of operations performed by Mongo Java Driver.

## APM version
Tested with APM 10.2.

## Supported third party versions
Tested with Mongo Server 3.2 and Mongo Java Driver 2.13.3.

## Limitations
Query parameter is limited only to the first level keys in the JSON object. For example, the following query:

```json
{ "grades.score": { $gt: 30 } }
```

Will be shown as:

```
{ grades.score = ? }
```

## License
Please review the
**LICENSE**
file in this repository.  Licenses may vary by repository.  Your download and use of this software constitutes your agreement to this license.

# Installation Instructions

## Prerequisites
*What has to be done before installing the field pack.*

## Dependencies
Java 1.6 minimum.

## Installation
- Drop the generated ca.apm.fieldpacks.agent.MongoNameFormatter.jar file into the ext directory of the agent.
-  Drop the sample pbd into the hotdeploy directory, or add it to your pbl or profile configuration file.

## Configuration
Modify the pbd as any other regular pbd to customize the metrics gathered.

# Usage Instructions
Metrics should appear in the investigator showing the operations and query parameters used between the application and the Mongo server.

## Metric description
*Describe the metrics provided by this field pack or link to third party documentation.*

## Name Formatter Replacements
{operation}: will be replaced by the mongo operation (e.g., find, update, remove).
{query}: will be replaced by a trimmed down version of the query parameter, with only the first level keys of the json object.

## Debugging and Troubleshooting
Check if the name formatter class is correctly loaded by activating the debug trace in the agent.

# Change log

Version | Author | Comment
--------|--------|--------
1.0 | miguelfc | First version.
