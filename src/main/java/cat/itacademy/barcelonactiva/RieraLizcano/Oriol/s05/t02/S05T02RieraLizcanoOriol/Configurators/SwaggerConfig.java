package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Configurators;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info()
                .title("Documentación del Ejercicio Sprint5.Tarea2")
                .version("1.0")
                .description("Documentación Swagger de la API Partida de Dados")
                .termsOfService("http://swagger.io/terms")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi apiGroup (){
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch(Constante.mainUrl, Constante.mainPage+"**")
                .build();
    }
    @Bean
    public GroupedOpenApi authGroup (){
        return GroupedOpenApi.builder()
                .group("authorize")
                .pathsToMatch(Constante.login, Constante.registro, Constante.logout)
                .build();
    }
    @Bean
    public GroupedOpenApi adminGroup (){
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch(Constante.admin+Constante.deleteJugador)
                .build();
    }

}
