This program looks up variables on paneldata.org. It retrieves the corresponding web page per variable, extracts question information, and stores it in a CSV file. 

# Input 
To look up a variable, it should be included in the `variables.csv` CSV file. An entry in that file consists of the variable dataset ID followed by its name. For example:

```
pl,plj0014_v1
biol,lr3039
pl,plj0001
```

The input CSV cannot contain blank lines.

# Output
The output is stored in the `output.csv` CSV file.

The output corresponding to the above input is:
```
variable_dataset_id, variable_name, variable_reference_url, question_period, question_instrument, question_text, question_reference_url
pl,plj0014_v1,https://paneldata.org/soep-core/data/pl/plj0014_v1,2019,Individual (CAPI) 2019,What is your country of citizenship?,https://paneldata.org/soep-core/inst/soep-core-2019-pe-lgb/178
biol,lr3039,https://paneldata.org/soep-core/data/biol/lr3039,2019,"Individual and Biography (M3-M5, Initial interview) 2019","How much was your last monthly net income for this occupation, i.e. the amount paid to you in the aforementioned currency?",https://paneldata.org/soep-core/inst/soep-core-2019-pb-m345-erst/Q194
pl,plj0001,https://paneldata.org/soep-core/data/pl/plj0001,n/a,n/a,n/a,n/a
```

Note that
- if a variable references multiple questions, only the first question found on paneldata.org is included in the output CSV
- if a variable referenecs no questions, it's still included in the output CSV, but with "n/a" for the question fields

The code already has support for multiple questions, so this should be straightforward to change.

# Running
To run the application, you'll need Java, specifically JRE 8 and basic familiarity with a terminal, like Terminal on macOS. If you don't have Java, you can [get it from Oracle](https://www.oracle.com/java/technologies/javase-jre8-downloads.html).

You can either download the application from the GitHub [Releases page](https://github.com/emersonf/soep-variable-resolver/releases) (look under Assets) or [build it](#compiling) yourself. Once you have the JAR file, open a terminal and run:

```
java -jar soep-variable-resolver-<version number>.jar
```

You should see the application logs scroll by. If the run succeeds, you'll see output that the "Job ... [COMPLETED]", which looks like this:

```
2021-07-09 20:54:35.542  INFO 19597 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=questionDownloadingJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED] in 1s631ms
```

Please note that each run overwrites any CSV file created by the previous run.

# Compiling

To compile the code, you'll need JDK 8 or newer. 

Simply run `./gradlew build` from a terminal. The application will be created in the `build/libs/soep-variable-resolver-<version number>.jar` file. Please note that there are two JARs in that directory, you'll need the larger one, not the one with the `-plain` suffix.

# Modifying

The application is implemented using the following frameworks and technologies.

- The code is written in Kotlin and Spring.
- The batch job that orchestrates everything is implemented using Spring Batch.
- JSoup handles the HTML parsing.
- The build system is Gradle.
