package internal.svs

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URL

data class VariablePageContent(
    val variable: Variable,
    val pageContent: Document
) {
    val questionList: List<Question> by lazy {
        pageContent.getElementById("relatedQuestionsModal")
            ?.select("table tr:gt(0)")
            ?.map { element -> element.asQuestion() }
            ?: emptyList()
    }

    private fun Element.asQuestion() =
        Question(
            variable = variable,
            period = select("td:eq(0)").text(),
            instrument = select("td:eq(1)").text(),
            text = select("td:eq(2)").text(),
            referenceUrl = URL(PANEL_DATA_SITE_DOMAIN + select("td:eq(2) a").attr("href"))
        )
}
