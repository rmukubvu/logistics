package za.charurama.logistics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import za.charurama.logistics.services.CacheService;

@SpringBootApplication
public class LogisticsApplication implements CommandLineRunner {

	@Autowired
	private CacheService cacheService;

	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}

	@Override
	public void run(String... args) {
		cacheService.cacheLookupData();
	}
}
