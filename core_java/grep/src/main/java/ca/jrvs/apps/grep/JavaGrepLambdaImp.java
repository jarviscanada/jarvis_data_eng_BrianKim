package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepLambdaImp javaGrepImp = new JavaGrepLambdaImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception e) {
      throw new RuntimeException("Failed to process file.", e);
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    File root = new File(rootDir);
    listFileHelper(root, fileList);
    return fileList;
  }

  public void listFileHelper(File file, List<File> result) {
    if (file.listFiles().length < 1) {
      return;
    }

    Arrays.stream(file.listFiles())
        .forEach(f -> {
          if (f.isDirectory()) {
            listFileHelper(f, result);
          } else {
            result.add(f);
          }
        });
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lineList;

    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      lineList = reader.lines().collect(Collectors.toList());
      reader.close();
    } catch (Exception e) {
      throw new RuntimeException("Failed to read lines in inputFile", e);
    }
    return lineList;
  }
}
