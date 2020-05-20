package com.valhalla.lolchampviewer.net

import kotlinx.serialization.*
import kotlin.reflect.KClass

open class CommonEnumSerializer<E : Enum<E>>(
    private val kClass: KClass<E>,
    private val fallback: E
) : KSerializer<E> {
    override val descriptor: SerialDescriptor =
        PrimitiveDescriptor(kClass.enumClassName(), PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): E {
        val string = decoder.decodeString()
        var enumMemberBySerialName = kClass
            .enumMembers()
            .find {
                it.getEnumFieldAnnotation<SerialName>()?.value == string
            }
        if (enumMemberBySerialName == null) {
            enumMemberBySerialName = kClass
                .enumMembers()
                .find {
                    it.toString() == string
                }
        }
        return enumMemberBySerialName ?: fallback
    }

    override fun serialize(encoder: Encoder, value: E) {
        encoder.encodeString(
            value.getEnumFieldAnnotation<SerialName>()?.let {
                it.value
            } ?: value.toString()
        )
    }
}

inline fun <reified A : Annotation> Enum<*>.getEnumFieldAnnotation(): A? =
    javaClass.getDeclaredField(name).getAnnotation(A::class.java)