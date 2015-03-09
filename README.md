# LinkChecker
Pings a URL and checks the HTTP status code. Based on the status code, prints the URL and status code in color (on CI). 

The project is based on Junit.This is how it works:

1. If the status code is less than 300, prints the provided URL in green color and adds the URL and status code to
   the report file.
   
2. If the status code is 301 or 302, prints the original/provided URL in blue color, adds the URL and status code 
   to the report file. Pings the redirected URL after that
   
3. If the status code is greater than 400, prints the original/provided URL in red color, adds the URL and status 
   code to the report file.

4. The project also has Mobile linkchecker. This will add a user agent to the request property and make sure that 
   mobile URLs are working as expected.

5. The connection timeout and readtimeout for each URL is 10 seconds. It means that, the same number URLs could 
   finish faster against one server and could be slower on another.

6. For execution, create a LinkChecker.csv file and add a header "URLS" in the first column. After that add as many 
   URLs as you want.
