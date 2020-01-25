package com.safframework.kotlin.coroutines.extension

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withTimeout

/**
 *
 * @FileName:
 *          com.aihuishou.recyclemachine.extensions.`Deferred+Extension`
 * @author: Tony Shen
 * @date: 2019-10-11 10:23
 * @version: V1.0 <描述当前版本功能>
 */
suspend fun <T> Deferred<T>.awaitOrNull(timeout: Long = 0L) = try {

    if (timeout > 0) {

        withTimeout(timeout) {

            this@awaitOrNull.await()
        }

    } else {

        this.await()
    }
} catch (e: Exception) {

    System.err.println(e.message)
    null
}