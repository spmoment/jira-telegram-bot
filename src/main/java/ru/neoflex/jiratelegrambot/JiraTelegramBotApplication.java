package ru.neoflex.jiratelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;

@EnableScheduling
@SpringBootApplication
public class JiraTelegramBotApplication {

	private final String MESSAGE = "УВАЖАЕМЫЕ+СИНЬЕРЫ,+ПОРА+СПИСАТЬСЯ+В+JIRA!!!";
	private final String URL = "https://api.telegram.org" +
			"/bot1881483631:AAFVTzoC0vATK7OLCnLLBwolAuRikmdR47Q" +
			"/sendMessage?chat_id=-527936555&text=" + MESSAGE;
	private final String SELF_URL = "https://jira-telega1.herokuapp.com/";

	public static void main(String[] args) {
		SpringApplication.run(JiraTelegramBotApplication.class);
	}

	@Scheduled(cron = "0 50 10,16 * * 5")
	public void sendingMessageOnFriday() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
				.build();

		HttpResponse<String> response = client.send(request,
				HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());
	}

	@Scheduled(cron = "0 50 10,16 28-31 * ?")
	public void sendingMessageOnLastDayOfMonth() throws IOException, InterruptedException {
		final Calendar c = Calendar.getInstance();
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE) ||
				c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)-1) {

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(URL))
					.build();

			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());

			System.out.println(response.body());

		}
	}

	@Scheduled(cron = "0 40 10 * * ?")
	public void ping() {
		try {
			final URLConnection connection = new URL(SELF_URL).openConnection();
			connection.connect();
			System.out.println("Service " + SELF_URL + " available, yeah!");
		} catch (final MalformedURLException e) {
			System.out.println("Bad URL: " + SELF_URL);
		} catch (final IOException e) {
			System.out.println("Service " + SELF_URL + " unavailable, oh no!");
		}
	}
}
