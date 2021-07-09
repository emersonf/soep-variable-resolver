package internal.svs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@SpringBootApplication
@Configuration
class BatchApplication {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate =
        builder.build()
}

fun main(args: Array<String>) {
    SpringApplication.run(BatchApplication::class.java, *args)
}
