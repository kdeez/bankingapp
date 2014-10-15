Creating the Eclipse Project
1. Install Eclipse IDE
2. Install Maven Plugin if not already installed (Help->Eclipse Marketplace)
3. Install Git Plugin if not already installed
4. Select Window->Open Perspective->Git
5. Select Clone an existing repo and follow the steps (using https vs ssh is typically easier)
6. Highlight the imported repo and select File->Import->Maven-Existing Maven Projects
7. Now open the Java perspective
8. Right click on the project and select Maven->Update Project
9. Right click on the pom.xml file and select Run As->Maven build
10. Type "clean install" into the "Goals" field and press Run
11. Open the console and verify the build is "SUCCESS"

You will also need to install MySQL Server if you wish to run the web application on your local machine.
http://dev.mysql.com/downloads/mysql/

Installing the database via command-line(MySQLWorkbench can also be used)
1. start mysql by typing "mysql -uroot -ppassword" where root and password are your credentials for your mysql server
2. type "create database bytekonzz";
3. type "quit";
4. type "mysql -uroot -ppassord bytekonzz < /src/main/sql/bytekonzz-mysql.sql"  (note that you may need cd or change the path)
5. That's it!

Running the application from Eclipse:
1. Right click on project select Debug As->Java Application ("Run As" is also valid but won't invoke the debugger)
2. Select rest.server.main.JettyServer as the main class and then press Run
3. View the console in eclipse to make sure the application runs.

A couple of test pages called ajax.jsp has been configured in WEB-INF directory (our web root).  The web.xml in that directory is a configuration file for the web server and is not a web resource.

The test page is located at...
http://localhost:8081/ajax.jsp

Spring is a very powerful framework and will be used throughout our application.  Here is a reference in case you want to look up something you see in the code.
http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/