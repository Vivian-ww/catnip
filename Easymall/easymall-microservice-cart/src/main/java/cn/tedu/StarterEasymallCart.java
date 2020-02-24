package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("cn.tedu.cart.mapper")
@EnableEurekaClient
public class StarterEasymallCart {
	public static void main(String[] args) {
		SpringApplication.run(StarterEasymallCart.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate initRestTemplateProd() {
		return new RestTemplate();
	}
}
