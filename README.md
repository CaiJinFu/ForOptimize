
## 前言

for循环查询数据时，在数据量大的时候，不同的写法在耗时上会有些差距。

### 未优化的写法

先添加一下数据

```java
private static List<String> getList() {
  ArrayList<String> strings = new ArrayList<>();
  for (int i = 0; i< 1000; i++) {
    strings.add("" + i);
  }
  return strings;
}
```

```java
private static void unoptimized() {
  List<String> list = getList();
  long startTime = System.nanoTime();
  for (int i = 0; i < list.size(); i++) {

  }
  long endTime = System.nanoTime();
  System.out.println("未优化list耗时："+(endTime - startTime));
}
```

### 优化方式一：数组长度提取出来

```java
private static void optimizeOne() {
  List<String> list = getList();
  long startTime = System.nanoTime();
  int size = list.size();
  for (int i = 0; i < size; i++) {

  }
  long  endTime = System.nanoTime();
  System.out.println("优化list1耗时："+(endTime - startTime));
}
```

测试结果：

未优化list耗时：25700
优化optimizeOne耗时：5400

### 优化方式二：数组长度提取出来，跟方式一稍微有些不同

```java
private static void optimizeTwo() {
  List<String> list = getList();
  long startTime = System.nanoTime();
  for (int i = 0, len = list.size(); i < len; i++) {

  }
  long  endTime = System.nanoTime();
  System.out.println("优化list2耗时："+(endTime - startTime));
}
```

测试结果：

未优化list耗时：27300
优化optimizeTwo耗时：5300

### 优化方式三：采用倒序的写法

```java
private static void optimizeThree() {
  List<String> list = getList();
  long startTime = System.nanoTime();
  for (int i = list.size() - 1; i >= 0; i--) {

  }
  long  endTime = System.nanoTime();
  System.out.println("优化optimizeThree耗时："+(endTime - startTime));
}
```

测试结果：

未优化list耗时：25800
优化optimizeThree耗时：4800

### jdk1.5新写法

```java
private static void optimizeFour() {
  List<String> list = getList();
  long startTime = System.nanoTime();
  for (String s : list) {

  }
  long  endTime = System.nanoTime();
  System.out.println("优化optimizeFour耗时："+(endTime - startTime));
}
```

此种写法不是优化，只是写法更简洁了。在实际测试过程中发现，发现反而耗时更长了。

测试结果：

未优化list耗时：26000
优化optimizeFour耗时：292700

### 循环嵌套外大内小原则

```java
private static void outBig() {
  long startTime = System.nanoTime();
  for (int i = 0; i < 10000000; i++) {
    for (int j = 0; j < 10; j++) {}
  }
  long endTime = System.nanoTime();
  System.out.println("外大内小耗时：" + (endTime - startTime));
}
```

```java
private static void innerBig() {
  long startTime = System.nanoTime();
  for (int i = 0; i < 10; i++) {
    for (int j = 0; j < 10000000; j++) {}
  }
  long endTime = System.nanoTime();
  System.out.println("外小内大耗时：" + (endTime - startTime));
}
```

虽然网上有些文章写的是外小内大耗时时间短，但是我实际上测试过程中，是外大内小耗时短。

测试结果：

外大内小耗时：1400300
外小内大耗时：3749400

### 异常捕获

#### 内部捕获异常

```java
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
```

#### 外部捕获异常

```java
private static void catchOuter() {
  long stratTime = System.nanoTime();
  try {
    for (int i = 0; i < 10000000; i++) {}
  } catch (Exception e) {

  }
  long endTime = System.nanoTime();
  System.out.println("在外部捕获异常耗时：" + (endTime - stratTime));
}
```

测试结果：

在内部捕获异常耗时：1317550

在外部捕获异常耗时：1319250

虽然网上都说外部捕获异常耗时短，但是实际测试的时候，不一定，有时长有时短。

### 提取与循环无关的表达式

#### 没有提取无关的表达式

```java
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
```

#### 提取了无关的表达式

```java
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
```

测试结果：

未提取耗时：800

已提取耗时：500

## 测试工具

以上版本都是在IntelliJ IDEA 2021.2.1(UlItimate Edition)。
