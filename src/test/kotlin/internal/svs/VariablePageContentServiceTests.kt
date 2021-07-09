package internal.svs

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowableOfType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestClientResponseException

@SpringBootTest
class VariablePageContentServiceTests(
    @Autowired private val service: VariablePageContentService
) {
    private val plj0014v1 = Variable("pl", "plj0014_v1")

    @Test
    fun `should throw exception if variable page isn't found`() {
        val unknownVariable = Variable("nope", "nope")

        val exception =
            catchThrowableOfType({ service.getPageContent(unknownVariable) }, RestClientResponseException::class.java)

        assertThat(exception).isNotNull
        assertThat(exception.rawStatusCode).isNotEqualTo(200)
    }

    @Test
    fun `should retrieve content if variable page is found`() {

        val pageContent = service.getPageContent(plj0014v1)

        assertThat(pageContent.variable).isEqualTo(plj0014v1)
    }
}
