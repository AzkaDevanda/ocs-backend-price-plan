package com.ocs.portal.auth.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  /* ====== Konstanta ====== */
  private static final String SECURITY_SCHEME_NAME = "bearerAuth";

  /**
   * Seluruh endpoint module “price‑plan” — tambah / ubah di sini saja
   */
  private static final String[] PRICE_PLAN_PATHS = {
          "/api/priceplan/**", "/api/price/**", "/api/event/**",
          "/api/pricePlan-package/**", "/api/price-version/**", "/api/rankprice/**",
          "/api/mapping/**", "/api/rate-plan/**",
          "/api/script-templet/**", "/api/validator/**",
          "/api/acct/**", "/api/unitType/**",
          "/api/acct-balance/**", "/api/trigger/**", "/api/time-unit/**", "/api/discount/**"
  };


  /* ====== Grouped API ====== */

  @Bean
  public GroupedOpenApi pricePlanApi () {
    return GroupedOpenApi.builder()
            .group("price-plan")
            .pathsToMatch(PRICE_PLAN_PATHS)
            .build();
  }


  

  /* ====== Global OpenAPI ====== */

  @Bean
  public OpenAPI openApi () {
    return new OpenAPI()
            .info(new Info()
                    .title("Online Charging System")
                    .version("1.0")
                    .description("Documentation API Online Charging System"))
            .components(new Components()
                    .addSecuritySchemes(SECURITY_SCHEME_NAME,
                            new SecurityScheme()
                                    .name(SECURITY_SCHEME_NAME)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
  }
}
