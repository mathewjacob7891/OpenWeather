package com.mathew.openweather.util

import java.lang.reflect.Field

object RefUtil {
    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun setFieldValue(`object`: Any, fieldName: String, valueTobeSet: Any?): Field {
        val field = getField(`object`.javaClass, fieldName)
        field.isAccessible = true
        field[`object`] = valueTobeSet
        return field
    }

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun getPrivateFieldValue(`object`: Any, fieldName: String): Any {
        val field = getField(`object`.javaClass, fieldName)
        field.isAccessible = true
        return field[`object`]
    }

    @Throws(NoSuchFieldException::class)
    private fun getField(mClass: Class<*>, fieldName: String): Field {
        return try {
            mClass.getDeclaredField(fieldName)
        } catch (e: NoSuchFieldException) {
            val superClass = mClass.superclass
            if (superClass == null) {
                throw e
            } else {
                getField(superClass, fieldName)
            }
        }
    }


    fun <T : Any> T.getPrivateProperty(variableName: String): Any? {
        return javaClass.getDeclaredField(variableName).let { field ->
            field.isAccessible = true
            return@let field.get(this)
        }
    }

    fun <T : Any> T.setAndReturnPrivateProperty(variableName: String, data: Any): Any? {
        return javaClass.getDeclaredField(variableName).let { field ->
            field.isAccessible = true
            field.set(this, data)
            return@let field.get(this)
        }
    }
}