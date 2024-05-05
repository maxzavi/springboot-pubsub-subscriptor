# Java Spring Boot Sample pull messages from Google PubSub  using maven

Create Spring Boot project with maven, without dependencies

Implements **CommandLineRunner** and add **run** method (override)

```java
public class DemogooglepubsubsubscriptorApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
```

Add logging using **slombok**

```java
//Add Logger
@Log4j2
public class DemogooglepubsubsubscriptorApplication implements CommandLineRunner {
```

logging with:

```java
		log.info("Start");
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
export GOOGLE_APPLICATION_CREDENTIALS=/Users/maxzavaleta/repos/github.maxzavi/springboot-pubsub-subscriptor/keys/key-gcp.json 
```

Windows
```cmd
set GOOGLE_APPLICATION_CREDENTIALS=/Users/maxzavaleta/repos/github.maxzavi/springboot-pubsub-subscriptor/keys/key-gcp.json 
````

Get projectId and subscriptionId from Google PubSub, and replace in application.properties, usea @Value annotation for use

Read value in code, use Value annotation, ensure using import org.springframework.beans.factory.annotation.Value and set in class level:

```java
	@Value("${gcp.project-id}")
	String projectId;
	@Value("${gcp.subscription-name}")
	String subscriptionId;
```

Add in application.properties, exclude from version control for security!!

```yml
gcp:
  project-id: spsa-prd-on
  subscription-name: t-lpn-comex-sub
```

## Setup launch vscode

Config **launch.json** to use environment variable GOOGLE_APPLICATION_CREDENTIALS and args in **run** and **debug**:


In VSCode then **RUN>Open Configuration** OR **Add Configuration** and select **Java**, create file launch.json file into my .vscode folder:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "DemogooglepubsubsubscriptorApplication",
            "request": "launch",
            "mainClass": "pe.maxz.demogooglepubsubsubscriptor.DemogooglepubsubsubscriptorApplication",
            "projectName": "demogooglepubsubsubscriptor"
        }
    ]
}
```


In config, named **DemogooglepubsubsubscriptorApplication**, add **env** and **args**
```json
        {
            "type": "java",
            "name": "DemogooglepubsubsubscriptorApplication",
            "request": "launch",
            "mainClass": "pe.maxz.demogooglepubsubsubscriptor.DemogooglepubsubsubscriptorApplication",
            "projectName": "demogooglepubsubsubscriptor",
            "args": "",
            "env": {
                "GOOGLE_APPLICATION_CREDENTIALS": "/Users/maxzavaleta/repos/github.maxzavi/springboot-pubsub-subscriptor/keys/key-gcp.json"

            }

        }
```


