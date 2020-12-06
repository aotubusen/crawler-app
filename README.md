# crawler-app


======================================================================================
How to build and run
- download source from https://github.com/aotubusen/crawler-app
- or get zip from https://github.com/aotubusen/crawler-app/archive/main.zip
- extract zip to your prefered location
- go to location {yourfolder}\crawler-app-main\demo.webcrawler
- run command 'gradlew run --args=http://wiprodigital.com'
- change url in above command as desired.
=====================================================================================


I am using JSoup (https://jsoup.org/), as convinient java HTML parser.
My implenetation fetches the links on each page concurently and recursively, to build the site map into an internal hashmap 

My implemetation crawls 288 pages in about 35 seconds.

Given more time 
- I would have investigated an implementation that avoids opening a new connection for ever new page fetch.
- provided better URL validation
- implemented my solution in a way that the Crawler app is not coupled to the Jsoup library
- provided more test coverage.
