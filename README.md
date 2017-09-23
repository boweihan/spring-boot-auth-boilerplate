<h2>Convenience.</h2>
<i>“The state of convenience lies in the hands of proper planning.
When you know this, you will become a good planner;
and when you become a good planner, you save your life from stress!”</i>
- <b>Israelmore Ayivor</b>
<br/>
<h1>basic-spring-boot-server</h1>
<b>basic-spring-boot-server</b> presents an opioninated implementation of
a generic Java backend. The purpose is to provide a groundwork on which real ideas
and applications can be prototyped at break-neck speed. Think of <b>basic-spring-boot-server</b>
as a base infrastructure on which you can add YOUR custom business logic.
<br/>
<br/>
<h2>What's Included?</h2>
<ul>
    <li>Spring Framework REST implementation and functionality</li>
    <li>Jetty as our HTTP server</li>
    <li>Spring Boot Actuator for production metrics and endpoints</li>
    <li>User/Role basic authentication and authorization</li>
    <li>Secure field encryption (Bcrypt)</li>
    <li>Spring Data with Hibernate ORM for data access management</li>
    <li>Unit tests that run on build</li>
    <li>DB Seeding</li>
    <li>Custom exceptions and exception handling</li>
    <li>Postgres as a database</li>
</ul>
<br/>
<h2>How do I use it?</h2>
<b>basic-spring-boot-server</b> uses gradle as a build tool. First, start by creating a gradle wrapper:<br/>
<br/><b><i>Wrapper: gradle wrapper --gradle-version 2.13</i></b>
<br/><br/>
Next, build the project with gradle:
<br/><br/>
<b><i>./gradlew build</i></b>
<br/><br/>
Lastly, start up your server!
<br/><br/>
<b><i>java -jar build/libs/gs-spring-boot-0.1.0.jar</i></b>
<br/><br/>
Or execute both commands at once :)
<br/><br/>
<b><i>./gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar</i></b>
<br/><br/>
If this didn't work right away, you might need to configure your local postgres database (next section).
<br/><br/>
<h2>Configuring PostgreSQL to work with basic-spring-boot-server</h2>
Local DB configuration can be found in application.yml. Replace the existing properties with your local
PostgreSQL config. For example:<br/><br/>
url=jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME<br/>
username=YOUR_USERNAME<br/>
password=YOUR_PASSWORD
<br/><br/>
<h2>Deployment</h2>
<b>basic-spring-boot-server</b> is configured to work with Heroku out of the box.
To deploy the application to your Heroku account simply follow the instructions here:
<br/><br/>
https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku
<br/><br/>
Base production configurations are set up in application.yml.
<br/></br/>
<h2>Notes</h2>
- Server is configured to run on port 8080<br/>
- API security management is configured in WebSecurityConfig.java<br/>
- On startup, your database will be seeded with an admin user ("admin"/"admin@bsbs.com")</br>