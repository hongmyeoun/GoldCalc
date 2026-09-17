package com.hongmyeoun.goldcalc.model.profile

import com.google.gson.JsonObject
import com.hongmyeoun.goldcalc.model.constants.TooltipStrings

object Common {
    fun currentElementKey(index: Int) : String {
        return "Element_${String.format("%03d", index)}"
    }

    fun indentStringGroup(element: JsonObject?): Boolean {
        return element?.get(TooltipStrings.MemberName.TYPE)?.asString == TooltipStrings.MemberName.INDENT_GROUP
    }

    fun itemPartBox(element: JsonObject?): Boolean {
        return element?.get(TooltipStrings.MemberName.TYPE)?.asString == TooltipStrings.MemberName.ITEM_PART_BOX
    }

    fun singleTextBox(element: JsonObject?): Boolean {
        return element?.get(TooltipStrings.MemberName.TYPE)?.asString == TooltipStrings.MemberName.SINGLE_TEXT_BOX
    }

    fun notSingleTextBox(element: JsonObject?): Boolean {
        return element?.get(TooltipStrings.MemberName.TYPE)?.asString != TooltipStrings.MemberName.SINGLE_TEXT_BOX
    }

    fun has000(value: JsonObject?): Boolean {
        return value?.has(TooltipStrings.MemberName.ELEMENT_000) == true
    }

    fun safeGetAsJsonObject(parent: JsonObject?, key: String): JsonObject? {
        return if (parent != null && parent.has(key) && parent.get(key).isJsonObject) {
            parent.getAsJsonObject(key)
        } else {
            null
        }
    }

    fun safeGetAsString(parent: JsonObject?, key: String): String? {
        return if (parent != null && parent.has(key) && parent.get(key).isJsonPrimitive) {
            parent.get(key).asString
        } else {
            null
        }
    }
}