# Design Documentation

* **Author:** *Jingxuan Ai*
* **Director:** *Jingfeng*
* Mar/10/2019

## Problem

Want to have a web crawler crawl financial data from websites.
1. Should be automatic, with no user input
2. Only reads from `webcrawler.properties.websitename`
3. Crawl the company finance data from certain website
4. Crawl the logo from wiki

## Solution

I know I should write pseudo code, but not that good at that. Moreover, 
since this is such a small class, I didn't write too much explanation.

### WebCrawler Class

Only `processWebcrawler() and printHashMap` are public and no class variables needed

#### Class Structure

* processWebcrawler()
    * c
        * getCompanyLogo
        * readSymbol(String FullURL)
            * getData(String input, String anchor, String[] keys)
* printAllData(List<List<HashMap<String, String>>> listoflistofmap)

##### `public List<List<HashMap<String, String>>> processWebcrawler()`

1. get properties in form of `List<String[]>` from `getProperties()`
2. use data from properties to call `readSymbol` and `getData`
3. put all data in form of `Company List ( Symbol List ( Data for each Symbol in HashMap) )`

#### `getProperties()`

1. use the `com.riskval.app.ApplicationLog` and `com.riskval.app.LocalSettings` to read properties file

#### `readSymbol(String fullURL)`

1. use Jsoup-1.10.3.jar for Java 6. Newest Jsoup is for Java 7 and above.
2. It is reading the body tag from un protected website.
    1. Google gives an 403 for rejecting this crawler
    2. MorningStar's data is not in body tag. Haven't been able to find it yet.

#### `getData(String input, String anchor, String[] keys)`

1. just make use of `.indexof("String", start index)` and `.substring(s,e)`
2. some edge case need extra care