package internal.svs

import org.jsoup.Jsoup
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Service
class VariablePageContentService(
    private val restTemplate: RestTemplate
) {
    fun getPageContent(variable: Variable): VariablePageContent {

        val response: ResponseEntity<String> = restTemplate.getForEntity(variable.referenceUrl.toURI())

        return VariablePageContent(
            variable = variable,
            pageContent = Jsoup.parse(response.body, "UTF-8")
        )
    }
}
