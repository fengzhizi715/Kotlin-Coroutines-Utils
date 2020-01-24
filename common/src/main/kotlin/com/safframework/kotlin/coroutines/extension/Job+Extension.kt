package com.safframework.kotlin.coroutines.extension

import kotlinx.coroutines.Job

/**
 *
 * @FileName:
 *          com.safframework.kotlin.coroutines.extension.`Job+Extension`
 * @author: Tony Shen
 * @date: 2020-01-07 14:59
 * @version: V1.0 <描述当前版本功能>
 */
fun Job.safeCancel() {
    if (isActive) {
        cancel()
    }
}