package internal.svs

import java.net.URL

data class Question(
    val variable: Variable,
    val period: String,
    val instrument: String,
    val text: String,
    val referenceUrl: URL
)

