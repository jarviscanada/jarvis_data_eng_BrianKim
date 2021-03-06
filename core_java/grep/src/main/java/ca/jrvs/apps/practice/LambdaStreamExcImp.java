package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

  public static void main(String[] args) {

  }

  @Override
  public Stream<String> createStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    Stream<String> strStream = createStream(strings);
    strStream.forEach(c -> c.toUpperCase());
    return strStream;
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern regex = Pattern.compile(pattern);
    stringStream = stringStream.filter(regex.asPredicate());
    return stringStream;
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    List<Integer> intList = intStream
        .boxed()
        .collect(Collectors.toList());
    return intList;
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.range(start, end + 1);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    DoubleStream doubleStream = intStream.asDoubleStream()
        .map(Math::sqrt);
    return doubleStream;
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(x -> x % 2 == 1);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    Consumer<String> printer = output -> {
      System.out.println(prefix + output + suffix);
    };
    return printer;
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    for (String msg : messages) {
      printer.accept(msg);
    }
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    intStream
        .filter(d -> d % 2 ==1)
        .forEach(d -> printer.accept(Integer.toString(d)));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    Stream<Integer> intStream = ints
        .flatMap(Collection::stream)
        .map(i -> i*i)
        .sequential();
    return intStream;
  }
}
