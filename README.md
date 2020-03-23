# Coronavirus
[![](https://jitpack.io/v/mew/Coronavirus.svg)](https://jitpack.io/#mew/Coronavirus)
[![License](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)  
Java API Wrapper for getting real-time Coronavirus (COVID-19, SARS-CoV-2) statistics via https://git.io/Jvoep.  

Live global stats (provided by [fight-covid19/bagdes](https://github.com/fight-covid19/bagdes))  
![Covid-19 Confirmed](https://covid19-badges.herokuapp.com/confirmed/latest)
![Covid-19 Recovered](https://covid19-badges.herokuapp.com/recovered/latest)
![Covid-19 Deaths](https://covid19-badges.herokuapp.com/deaths/latest)


## Example
```java
class Main {
    public static void main(String[] args){
        // Create an instance of the API.
        Coronavirus coronavirus = new Coronavirus();
        // Get latest data.
        System.out.println("Latest Coronavirus Data:");
        System.out.println("Confirmed Cases: " + latest.getConfirmed());
        System.out.println("Deaths: " + latest.getDeaths());
        System.out.println("Recovered: " + latest.getRecovered());
    }
}
```

## Installation
You can add the library to your project via [Jitpack](https://jitpack.io/#mew/coronavirus).
