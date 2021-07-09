This program looks up variables on paneldata.org. It retrieves the corresponding web page per variable, extracts question information, and stores it in a CSV file. 

# Input 
To look up a variable, it should be included in the `variables.csv` CSV file. An entry in that file consists of the variable dataset ID followed by its name. For example:

```
pl,plj0014_v1
biol,lr3039
```

# Output
The output is stored in the `output.csv` CSV file.

The output corresponding to the above input is:
```
variable_dataset_id, variable_name, variable_reference_url, question_period, question_instrument, question_text, question_reference_url
pl,plj0014_v1,https://paneldata.org/soep-core/data/pl/plj0014_v1,2019,Individual (CAPI) 2019,What is your country of citizenship?,https://paneldata.org/soep-core/inst/soep-core-2019-pe-lgb/178
biol,lr3039,https://paneldata.org/soep-core/data/biol/lr3039,2019,"Individual and Biography (M3-M5, Initial interview) 2019","How much was your last monthly net income for this occupation, i.e. the amount paid to you in the aforementioned currency?",https://paneldata.org/soep-core/inst/soep-core-2019-pb-m345-erst/Q194

```

Note that if a variable references multiple questions, only the first question found on paneldata.org is included in the output CSV. 

The code already has support for multiple questions, so this should be straightforward to change.

# Running
To run the application, you'll need Java, specifically JRE 8.

Please note that each run overwrites the output of the previous run.

# Compiling

To compile the code, you'll need JDK 8. 

Simply run `./gradlew build` from a terminal. 

# Modifying

The application is implemented using the following frameworks and technologies.

- The code is written in Kotlin and Spring.
- The batch job that orchestrates everything is implemented using Spring Batch.
- JSoup handles the HTML parsing.
- The build system is Gradle.
