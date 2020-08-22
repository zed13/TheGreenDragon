package server

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*

class KlaxonConverter(
    private val parser: Parser = Parser.default(),
    private val prettyPrinting: Boolean = true
) : ContentConverter {

    constructor(builder: KlaxonBuilder) : this(
        parser = builder.parser,
        prettyPrinting = builder.prettyPrinting
    )

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val reader = channel.toInputStream().reader(context.call.request.contentCharset() ?: Charsets.UTF_8)
        val type = request.type

        if (type.java != JsonArray::class.java || type.java != JsonObject::class.java) {
            error("Unsupported type for communication ${type}. Your could only use JsonArray and JsonObject")
        }

        return parser.parse(reader)
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        val text: String = if (value is JsonObject) {
            value.toJsonString(prettyPrinting)
        } else if (value is JsonArray<*>) {
            value.toJsonString(prettyPrinting)
        } else {
            error("Unsupported type for communication ${value::class.java}. Your could only use JsonArray and JsonObject")
        }
        return TextContent(text, contentType.withCharset(context.call.suitableCharset()))
    }
}

fun ContentNegotiation.Configuration.klaxon(
    contentType: ContentType = ContentType.Application.Json,
    block: KlaxonBuilder.() -> Unit = {}
) {
    val builder = KlaxonBuilder()
    builder.apply(block)
    val converter = KlaxonConverter(builder)
    register(contentType, converter)
}

class KlaxonBuilder {
    var parser = Parser.default()
    var prettyPrinting: Boolean = true
}