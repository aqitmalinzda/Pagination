package perangkaikode.com.sampletemplatingclass.sample_adapter.model

import java.io.Serializable

data class SampleModelParrentChild(
    val items: List<Item>?,
    val type: String?,
    val next: Int?
) {
    data class Item(
        val authors: String?,
        val cover: String?
    ) : Serializable
}