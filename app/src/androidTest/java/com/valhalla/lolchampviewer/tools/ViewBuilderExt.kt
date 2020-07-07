package com.valhalla.lolchampviewer.tools

import com.agoda.kakao.common.builders.ViewBuilder

fun ViewBuilder.withChildText(text: String) {
    withDescendant {
        withText(text)
    }
}