package com.safframework.kotlin.coroutines

/**
 *
 * @FileName:
 *          com.safframework.kotlin.coroutines.TypeAliases
 * @author: Tony Shen
 * @date: 2020-01-27 21:06
 * @version: V1.0 <描述当前版本功能>
 */
typealias action<T> = suspend () -> T

typealias mapper<T,R> = (T) -> R

typealias zipper<T1, T2, R> = (T1, T2) -> R

typealias coroutineErrorListener = (throwable: Throwable) -> Unit
