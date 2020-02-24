package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterPicCenter {
	public static void main(String[] args) {
		SpringApplication.run(StarterPicCenter.class, args);
	}
}
