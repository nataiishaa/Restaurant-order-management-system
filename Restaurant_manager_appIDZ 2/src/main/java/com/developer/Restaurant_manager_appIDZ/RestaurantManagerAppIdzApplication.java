package com.developer.Restaurant_manager_appIDZ;

import com.developer.Restaurant_manager_appIDZ.services.CookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantManagerAppIdzApplication {

	public static void main(String[] args) {
		CookingService cookingService = new CookingService();
		cookingService.startCooking();
		SpringApplication.run(RestaurantManagerAppIdzApplication.class, args);
	}

}
