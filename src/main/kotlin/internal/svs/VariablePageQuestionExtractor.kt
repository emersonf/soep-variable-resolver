package internal.svs

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class VariablePageQuestionExtractor : ItemProcessor<VariablePageContent, Question> {

    override fun process(variablePageContent: VariablePageContent): Question? =
        variablePageContent.questionList.firstOrNull()
}
