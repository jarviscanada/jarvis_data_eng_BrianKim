# Introduction

Java Grep App is a program which implements the Linux `grep` string pattern searching utility that matches a regular expression. 
This app recursively searches a given directory for plain-text data sets for lines that match the given regular expression. When the matched lines are found, they are written out to a file. <br/>
Technologies used: Java, Lambda, Stream API, BufferedReader/Writer, SLF4J, Maven, Docker, IntelliJ

# Quick Start
`mvn clean compile package` <br/>
Compile the Maven project and package the program into .jar file.<br/>
`java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [outFile]`<br/>
Run the .jar file with three arguments regex, rootDirectory, outFile to do the `grep` search. <br/>
`cat [outFile]` <br/>
Inspect the output file of matched lines.

#Implementation
Grep App is implemented with `JavaGrep` interface. It is implemented by two classes `JavaGrepImp` and `JavaGrepLambdaImp`. <br/>
In each implemented classes, the main method is used to handled CLI arguments which saves them to private member variables using the accessor methods. <br/>
With the helper methods in the class, `process` function works as the high-level work method which utilizes the necessary helper functions to read directory, search for string pattern and write to destination file, if found. <br/>
For debugging uses, the `slf4j` Logger framework is used to display error, message, info, etc.

## Pseudocode
`process` method
```Java
matchedLines = []
for file in listFilesRecursively(rootDir)
    for line in readFile(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
While listing files and reading each files line by line, memory issue can arise when there are huge size of files in the target directory or the file is too big to efficiently search for the regex pattern. <br/>
For this issue to be avoided, the Stream API is used to for large iterations more efficiently than for-loops.

# Test
This application has been tested manually with several sample files of different file sizes to compare results and efficiency.

# Deployment
Grep app is dockerized to a docker image and pushed to the Docker Hub, so that it can easily be consumed by the user. <br/>
- `docker pull kimbrian94/grep` to pull the image from docker hub.
- `docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log kimbrian94/grep .[regex] /data /log/grep.out` to run docker container.

# Improvement
1. Improve list/read/match operations' runtime with a more efficient data structures.
2. Avoid run time exceptions such as `NullPointerException` with null-checking.
3. Remove redundant try/catch blocks inside the methods where exceptions are automatically thrown with `throws`
