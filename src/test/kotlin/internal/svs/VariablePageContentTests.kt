package internal.svs

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource


class VariablePageContentTests {

    private fun asVariablePageContent(path: String): VariablePageContent {
        val classPathResource = ClassPathResource(path)

        return VariablePageContent(
            variable = Variable("dummy", path),
            pageContent = Jsoup.parse(classPathResource.file, "UTF-8")
        )
    }

    @Test
    fun `should construct page content correctly from page with questions`() {
        val variablePageContent = asVariablePageContent("page-with-questions-plj0014_v1.html")

        assertThat(variablePageContent.questionList).hasSize(44)

        with (variablePageContent.questionList[0]) {
            assertThat(period).isEqualTo("2019")
            assertThat(instrument).isEqualTo("Individual (CAPI) 2019")
            assertThat(text).isEqualTo("What is your country of citizenship?")
            assertThat(referenceUrl.toString()).isEqualTo("$PANEL_DATA_SITE_DOMAIN/soep-core/inst/soep-core-2019-pe-lgb/178")
        }
    }

    @Test
    fun `should construct page content correctly despite missing question section`() {
        val variablePageContent = asVariablePageContent("page-missing-question-section-plj0001.html")

        assertThat(variablePageContent.questionList).hasSize(0)
    }
}
