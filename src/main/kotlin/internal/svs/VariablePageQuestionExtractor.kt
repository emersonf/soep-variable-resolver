package internal.svs

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class VariablePageQuestionExtractor : ItemProcessor<VariablePageContent, VariableQuestion> {

    override fun process(variablePageContent: VariablePageContent): VariableQuestion? =
        VariableQuestion(
            variable = variablePageContent.variable,
            question = variablePageContent.questionList.firstOrNull()
        )
}
