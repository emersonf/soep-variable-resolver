package internal.svs

import java.net.URL

class Variable(
    val datasetId: String,
    val name: String
) {
    val referenceUrl: URL
        get() = URL("$PANEL_DATA_SITE_DOMAIN/soep-core/data/$datasetId/$name")

    override fun toString(): String =
        "$datasetId:$name"
}
