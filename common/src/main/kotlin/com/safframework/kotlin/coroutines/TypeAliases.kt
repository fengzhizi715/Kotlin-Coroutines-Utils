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