package internal.svs

import org.apache.commons.text.StringEscapeUtils
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource


private const val CHUNK_SIZE = 100

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
    @Autowired private var jobBuilderFactory: JobBuilderFactory,
    @Autowired private var stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun questionDownloadingJob(questionDownloadingStep: Step): Job =
        jobBuilderFactory["questionDownloadingJob"]
            .incrementer(RunIdIncrementer())
            .flow(questionDownloadingStep)
            .end()
            .build()

    @Bean
    fun questionDownloadingStep(
        variableReader: ItemReader<Variable>,
        variablePageContentLoader: VariablePageContentLoader,
        variablePageQuestionExtractor: VariablePageQuestionExtractor,
        questionWriter: ItemWriter<VariableQuestion>
    ): Step =
        stepBuilderFactory["questionDownloadingStep"]
            .chunk<Variable, VariableQuestion>(CHUNK_SIZE)
            .reader(variableReader)
            .processor(
                CompositeItemProcessorBuilder<Variable, VariableQuestion>()
                    .delegates(
                        variablePageContentLoader,
                        variablePageQuestionExtractor
                    )
                    .build()
            )
            .writer(questionWriter)
            .build()

    @Bean
    fun variableReader(): ItemReader<Variable> =
        FlatFileItemReaderBuilder<Variable>()
            .name("variableReader")
            .resource(FileSystemResource("variables.csv"))
            .comments("#")
            .fieldSetMapper {
                Variable(
                    datasetId = it.readString("datasetId"),
                    name = it.readString("variableName")
                )
            }
            .delimited()
            .names("datasetId", "variableName")
            .build()

    @Bean
    fun questionWriter(): ItemWriter<VariableQuestion> =
        FlatFileItemWriterBuilder<VariableQuestion>()
            .name("questionWriter")
            .resource(FileSystemResource("output.csv"))
            .delimited()
            .fieldExtractor {
                listOf(
                    it.variable.datasetId,
                    it.variable.name,
                    it.variable.referenceUrl,
                    it.question?.period ?: "n/a",
                    it.question?.instrument ?: "n/a",
                    it.question?.text ?: "n/a",
                    it.question?.referenceUrl ?: "n/a",
                )
                    .map { value -> value.toString().escapeCsv() }
                    .toTypedArray()
            }
            .headerCallback {
                it.write(
                    listOf(
                        "variable_dataset_id",
                        "variable_name",
                        "variable_reference_url",
                        "question_period",
                        "question_instrument",
                        "question_text",
                        "question_reference_url"
                    ).joinToString()
                )
            }
            .build()

    private fun String.escapeCsv(): String = StringEscapeUtils.escapeCsv(this)
}
