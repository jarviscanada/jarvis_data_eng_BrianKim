package ca.jrvs.apps.practice;
import java.util.regex.*;

public class RegexExcImpl implements RegexExc {

  private Pattern pattern;
  private Matcher matcher;

  @Override
  public boolean matchJpeg(String filename) {
    pattern = Pattern.compile("(.jpg|.jpeg)", Pattern.CASE_INSENSITIVE);
    matcher = pattern.matcher(filename);
   return matcher.find();
  }

  @Override
  public boolean matchIp(String ip) {
    pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
    matcher = pattern.matcher(ip);
    return matcher.find();
  }

  @Override
  public boolean isEmptyLine(String line) {
    pattern = Pattern.compile("^\\s*$");
    matcher = pattern.matcher(line);
    return matcher.find();
  }

  public static void main(String[] args) {
    RegexExcImpl regex = new RegexExcImpl();
    String fileName1 = "example.jpeg";
    String fileName2 = "example.jpg";
    String fileName3 = "example.png";
    String ip1 = "234.456.23.23";
    String ip2 = "-12.1024.43.0";
    String line = "                 ";

    System.out.println(regex.matchJpeg(fileName1));
    System.out.println(regex.matchJpeg(fileName2));
    System.out.println(regex.matchJpeg(fileName3));

    System.out.println(regex.matchIp(ip1));
    System.out.println(regex.matchIp(ip2));

    System.out.println(regex.isEmptyLine(line));
  }
}
