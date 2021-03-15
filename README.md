# find-most-frequent-words-webpage

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)

> REST API to find the most frequent words from a website using Spring Boot.


Overview
---
Find most frequent words from website page definitions on a Spring Boot project with RESTful endpoints. 
Receives as input a list of URLs of Web pages and returns the list of most frequent words in those pages,
By the length of the word.
For example, suppose the program's input is:
```
http://he.wikipedia.org
http://ynet.co.il
http://www.talniri.co.il
```
So the output of the program should be something like:
```
length 2: את
length 3: ואת
length 4: היום
.
.
.
```

### Each word element schema contains: 
```
{
    String word;
    int count;
    int length;
}
```

Endpoints
---

### /

- Methods: **GET**
- Description: returns the list of most frequent words from this url(he.wikipedia.org) page, By the length of the word.


### /{url}

- Methods: **GET**
- Description: Receives as input a URL of the Web page and returns the list of most frequent words from URL page, By the length of the word.

### /list/{urls}

- Methods: **GET**
- Description: Receives as input a list of URLs of Web pages and returns the list of most frequent words in those pages,
By the length of the word.



Running the Project with IntelliJ IDEA Ultimate Edition for development purpose.
---
1. Clone the repository using the git integration of Intellij From the main menu, choose **VCS | Checkout from Version Control | Git** and then click **Clone**.
2. IntelliJ will ask you to create an IntelliJ IDEA project from the sources you have checked out, just click **Yes**.
3. Choose **Import project from external model** and select **Maven**.
4. Modify the needed parameters for your configuration and click **Finish**.
5. Using the **Maven Projects** tool window, run the maven build using **package** command.
6. Click now the **Run application** button to start Spring Boot application. ( IntelliJ already added Spring boot config )
7. Port **8080**.


