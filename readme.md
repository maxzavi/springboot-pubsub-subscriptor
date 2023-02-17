# Java Spring Boot Sample pull messages from Google PubSub  using maven

Create Spring Boot project with maven, without dependencies

Implements **CommandLineRunner** and add **run** method

```java
public class DemogooglepubsubsubscriptorApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
```

Add logging using **slf4j**

```java
public class DemogooglepubsubsubscriptorApplication implements CommandLineRunner {
    //Add Logger
	private static Logger LOG = LoggerFactory.getLogger(DemogooglepubsubsubscriptorApplication.class);
```

## Add Google PubSub dependecies

Use documentation from https://cloud.google.com/pubsub/docs/publish-receive-messages-client-library#pubsub-client-libraries-java


In pom.xml:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.google.cloud</groupId>
      <artifactId>libraries-bom</artifactId>
      <version>26.1.5</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-pubsub</artifactId>
  </dependency>

</dependencies>
```

Create service account in GCP Project, profile "Google PubSub Subscriber", download json and set variable GOOGLE_APPLICATION_CREDENTIALS with full path from json file

Linux:
```cmd
export GOOGLE_APPLICATION_CREDENTIALS=/Users/myuser/.../myfile.json 
```

Windows
```cmd
set GOOGLE_APPLICATION_CREDENTIALS=/Users/myuser/.../myfile.json 
````

Get projectId and subscriptionId from Google PubSub, and replace in application.properties, usea @Value annotation for use

Read value in code, use Value annotation, ensure using import org.springframework.beans.factory.annotation.Value and set in class level:

```java
	@Value("${gcp-project-id}")
	String projectId;
	@Value("${gcp-pubsub-subscription-name}")
	String subscriptionId;
```

Add in application.properties, exclude from version control for security!!

```properties
gcp-pubsub-subscription-name=mysubcriptionname
gcp-project-id=myproject-id
```