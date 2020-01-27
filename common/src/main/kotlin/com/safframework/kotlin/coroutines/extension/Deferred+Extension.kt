package com.safframework.kotlin.coroutines.extension

import com.safframework.kotlin.coroutines.mapper
import com.safframework.kotlin.coroutines.zipper
import kotlinx.coroutines.*

/**
 *
 * @FileName:
 *          com.safframework.kotlin.coroutines.extension.`Deferred+Extension`
 * @author: Tony Shen
 * @date: 2019-10-11 10:23
 * @version: V1.0 <描述当前版本功能>
 */

suspend infix fun <T> Deferred<T>.then(block: (T) -> Unit) = coroutineScope {
    launch {
        block(this@then.await())
    }
}

suspend infix fun <T, R> Deferred<T>.thenAsync(block: mapper<T,R>)= coroutineScope {
    async {
        block(this@thenAsync.await())
    }
}

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

suspend fun <T, R> Deferred<T>.map(mapper: mapper<T,R>): Deferred<R> =
    coroutineScope {
        async {
            mapper(this@map.await())
        }
    }

suspend fun <K, T : Collection<K>, R> Deferred<T>.flatMap(coroutineStart: CoroutineStart = CoroutineStart.DEFAULT, mapper: mapper<K,R>): Deferred<Collection<R>> =
    coroutineScope {
        async(start = coroutineStart) {
            val result = mutableListOf<R>()
            val list = mutableListOf<Deferred<Boolean>>()
            await().forEach { list.add(async { result.add(mapper(it)) }) }
            list.forEach { it.await() }
            return@async result
        }
    }

suspend fun <K, T : Collection<K>, R> Deferred<T>.concatMap(coroutineStart: CoroutineStart = CoroutineStart.DEFAULT, mapper: mapper<K,R>): Deferred<Collection<R>> =
    coroutineScope {
        async(start = coroutineStart) {
            await().map { async { mapper(it) } }.map { it.await() }
        }
    }

suspend fun <T1, T2, R> zip(source1: Deferred<T1>, source2: Deferred<T2>, coroutineStart: CoroutineStart = CoroutineStart.DEFAULT, zipper: zipper<T1, T2, R>): Deferred<R> =
    coroutineScope {
        async(start = coroutineStart) {
            zipper(source1.await(), source2.await())
        }
    }

suspend fun <T1, T2, R> Deferred<T1>.zipWith(other: Deferred<T2>, coroutineStart: CoroutineStart = CoroutineStart.DEFAULT, zipper: zipper<T1, T2, R>): Deferred<R> {
    return zip(this, other, coroutineStart, zipper)
}