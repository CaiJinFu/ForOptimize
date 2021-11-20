import java.util.ArrayList;
import java.util.List;

/**
 * @author caijinfu
 * @date 2021/11/20
 */
public class MainClass {
  public static void main(String[] args) {
    //    unoptimized();
    //    optimizeOne();
    //    optimizeTwo();
    //    optimizeThree();
    //    optimizeFour();
    //    outBig();
    //    innerBig();
    //    catchInner();
    //    catchOuter();
    calculationInner();
    calculationOuter();
  }

  private static void optimizeFour() {
    List<String> list = getList();
    long startTime = System.nanoTime();
    for (String s : list) {}

    long endTime = System.nanoTime();
    System.out.println("优化optimizeFour耗时：" + (endTime - startTime));
  }

  private static void optimizeThree() {
    List<String> list = getList();
    long startTime = System.nanoTime();
    for (int i = list.size() - 1; i >= 0; i--) {}

    long endTime = System.nanoTime();
    System.out.println("优化optimizeThree耗时：" + (endTime - startTime));
  }

  private static void optimizeTwo() {
    List<String> list = getList();
    long startTime = System.nanoTime();
    for (int i = 0, len = list.size(); i < len; i++) {}

    long endTime = System.nanoTime();
    System.out.println("优化optimizeTwo耗时：" + (endTime - startTime));
  }

  private static void optimizeOne() {
    List<String> list = getList();
    long startTime = System.nanoTime();
    int size = list.size();
    for (int i = 0; i < size; i++) {}

    long endTime = System.nanoTime();
    System.out.println("优化optimizeOne耗时：" + (endTime - startTime));
  }

  private static void unoptimized() {
    List<String> list = getList();
    long startTime = System.nanoTime();
    for (int i = 0; i < list.size(); i++) {}

    long endTime = System.nanoTime();
    System.out.println("未优化list耗时：" + (endTime - startTime));
  }

  private static List<String> getList() {
    ArrayList<String> strings = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      strings.add("" + i);
    }
    return strings;
  }

  private static void innerBig() {
    long startTime = System.nanoTime();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10000000; j++) {}
    }
    long endTime = System.nanoTime();
    System.out.println("外小内大耗时：" + (endTime - startTime));
  }

  private static void outBig() {
    long startTime = System.nanoTime();
    for (int i = 0; i < 10000000; i++) {
      for (int j = 0; j < 10; j++) {}
    }
    long endTime = System.nanoTime();
    System.out.println("外大内小耗时：" + (endTime - startTime));
  }

  /** 在内部捕获异常 */
  private static void catchInner() {
    long stratTime = System.nanoTime();
    for (int i = 0; i < 10000000; i++) {
      try {
      } catch (Exception e) {
      }
    }
    long endTime = System.nanoTime();
    System.out.println("在内部捕获异常耗时：" + (endTime - stratTime));
  }

  /** 在外部捕获异常 */
  private static void catchOuter() {
    long stratTime = System.nanoTime();
    try {
      for (int i = 0; i < 10000000; i++) {}
    } catch (Exception e) {

    }
    long endTime = System.nanoTime();
    System.out.println("在外部捕获异常耗时：" + (endTime - stratTime));
  }

  /**
   * 未提取无关的表达式
   */
  private static void calculationInner() {
    int a = 3;
    int b = 7;
    long stratTime = System.nanoTime();
    for (int i = 0; i < 10000000; i++) {
      i = i * a * b;
    }
    long endTime = System.nanoTime();
    System.out.println("未提取耗时：" + (endTime - stratTime));
  }

  /**
   * 提取无关的表达式
   */
  private static void calculationOuter() {
    int a = 3;
    int b = 7;
    int c = a * b;
    long stratTime = System.nanoTime();
    for (int i = 0; i < 10000000; i++) {
      i = i * c;
    }
    long endTime = System.nanoTime();
    System.out.println("已提取耗时：" + (endTime - stratTime));
  }

}
