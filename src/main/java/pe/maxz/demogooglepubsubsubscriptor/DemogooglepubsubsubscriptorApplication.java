package pe.maxz.demogooglepubsubsubscriptor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

@SpringBootApplication
public class DemogooglepubsubsubscriptorApplication implements CommandLineRunner {
	private static Logger LOG = LoggerFactory.getLogger(DemogooglepubsubsubscriptorApplication.class);
	@Value("${gcp-project-id}")
	String projectId;
	@Value("${gcp-pubsub-subscription-name}")
	String subscriptionId;

	public static void main(String[] args) {
		SpringApplication.run(DemogooglepubsubsubscriptorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("Start");

		while (true) {
			subscribeAsyncExample(projectId, subscriptionId);	
		}
	}

	public static void subscribeAsyncExample(String projectId, String subscriptionId) {
		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

		// Instantiate an asynchronous message receiver.
		MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
			// Handle incoming message, then ack the received message.
			System.out.println("Id: " + message.getMessageId());
			System.out.println("Data: " + message.getData().toStringUtf8());
			consumer.ack();
		};

		Subscriber subscriber = null;
		try {
			subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
			// Start the subscriber.
			subscriber.startAsync().awaitRunning();
			//System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
			// Allow the subscriber to run for 30s unless an unrecoverable error occurs.
			subscriber.awaitTerminated(1, TimeUnit.SECONDS);
		} catch (TimeoutException timeoutException) {
			// Shut down the subscriber after 30s. Stop receiving messages.
			subscriber.stopAsync();
		}
	}
}
