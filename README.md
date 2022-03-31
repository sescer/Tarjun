
<img src="docs/tarjun.png" alt="drawing" align="right" style="width:300px;"/>

# Tarjun

Mock-framework for Java

## Features
* Mock and spy objects with annotations
* Stubbing
* Argument matcher
* thenThrow method
* Call Real Methods

## Tools
* Java
* [ByteBuddy](https://github.com/raphw/byte-buddy)
* [Objenesis](https://github.com/easymock/objenesis)

## Usage
### Mock creation
* with `mock()` method:
```java
Test test = Tarjun.mock(Test.class);
```
* with `@Mock` annotation:
```java
@Mock
Test test;
```

### Spy creation
* with `spy()` method:
```java
List<String> testList = new ArrayList<>();
List<String> testListSpied = Tarjun.spy(testList)
```
* with `@Spy` annotation:
```java
@Spy
List<String> testListSpied = new ArrayList<>();
```
