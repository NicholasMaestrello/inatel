package com.example.gamemanager;

import com.example.gamemanager.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GameManageApplication implements CommandLineRunner {

	private final RestTemplate restTemplate = new RestTemplate();
	@Value("${publisher-manager.url}")
	private String publisherManagerUrl;
	@Value("${server.port}")
	private int serverPort;

	public static void main(String[] args) {
		SpringApplication.run(GameManageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String url = publisherManagerUrl + "/notification";
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setHost("localhost");
		notificationDto.setPort(serverPort);
		restTemplate.postForEntity(url,notificationDto,Void.class);

	}
}
