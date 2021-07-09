package internal.svs

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class VariablePageContentLoader(
    private val variablePageContentService: VariablePageContentService
) : ItemProcessor<Variable, VariablePageContent> {

    override fun process(variable: Variable): VariablePageContent? =
        variablePageContentService.getPageContent(variable)
}
