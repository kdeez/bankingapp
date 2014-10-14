Steps to create and run Maven project.

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
12. Now right click on project select Debug As->Java Application ("Run As" is also valid but won't invoke the debugger)
13. Select rest.server.main.JettyServer as the main class and then press Run


A couple of test pages have been configured in WEB-INF name index.html and index.jsp.  The web.xml is a configuration file for the web server and is not a web resource.

A simple HTML file with a Javascript is introduced here.  No server side scripting is available with .html files on our server.
http://localhost:8081/index.html

Java Server Pages can run Java as the server side scripting language.
http://localhost:8081/index.jsp