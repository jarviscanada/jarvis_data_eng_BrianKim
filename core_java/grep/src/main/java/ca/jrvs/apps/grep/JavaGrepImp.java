package ca.jrvs.apps.grep;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.BasicConfigurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {
  final static Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex, rootPath, outFile;

  public static void main(String[] args) {
    if (args.length != 3)
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception e) {
      JavaGrepImp.logger.error(e.getLocalizedMessage(), e);
    }
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    for (File file : listFiles(rootPath)) {
      for (String line : readLines(file)) {
        if (containsPattern(line))
          matchedLines.add(line);
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    File root = new File(rootDir);
    listFilesHelper(root, fileList);

    return fileList;
  }

  public void listFilesHelper(File file, List<File> result) {
    if (file.listFiles().length < 1)
      return;

    for (File f: file.listFiles()) {
      if (f.isDirectory())
        listFilesHelper(f, result);
      else
        result.add(f);
    }
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lineList = new ArrayList<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      lineList = reader.lines().collect(Collectors.toList());
      reader.close();
    } catch (Exception e) {
      JavaGrepImp.logger.error(e.getLocalizedMessage(), e);
    }
    return lineList;
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(this.regex);
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    FileWriter file = new FileWriter(this.outFile);
    BufferedWriter writer = new BufferedWriter(file);

    for (String line : lines)
      writer.write(line+"\n");
    writer.close();
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
