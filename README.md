# Coronavirus
Java API Wrapper for getting real-time Coronavirus (2019-nCoV) statistics via https://git.io/Jvoep.

## Example
```java
public static void main(String[] args) throws IOException {
    // Instance of the API.
    Coronavirus coronavirus = new Coronavirus();
    // Get latest data.
    LatestData latest = coronavirus.getLatestData();

    System.out.println("Latest Coronavirus Data:");
    System.out.println("Confirmed Cases: " + latest.getConfirmed());
    System.out.println("Deaths: " + latest.getDeaths());
    System.out.println("Recovered: " + latest.getRecovered());
}
```

## Installation
You can add the library to your project via [Jitpack](https://jitpack.io/#mew/coronavirus).
