package com.navishkadarshana.smileygame;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimpleGame.class);
	}

}
