# Kotlin-Coroutines-Utils

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/coroutines-utils/images/download.svg) ](https://bintray.com/fengzhizi715/maven/coroutines-utils/_latestVersion)

## 下载安装

Gradle:

```groovy
implementation 'com.safframework.kotlin.coroutines:coroutines-utils:1.1.2'
```

## Feature

### 1. SafeCoroutineScope

SafeCoroutineScope 的 CoroutineContext 使用了 SupervisorJob 和 CoroutineExceptionHandler，因此是安全的 CoroutineScope。

基于 SafeCoroutineScope 封装，提供了以下函数：

* runOnUI() : 运行在主线程，支持异常处理、无返回结果
* runInBackground(): 运行在后台线程，支持异常处理、无返回结果
* asyncOnUI(): 运行在主线程，支持异常处理、有返回结果
* asyncInBackground(): 运行在后台线程，支持异常处理、有返回结果
* withUI(): 使用 Dispatchers.Main 切换线程
* withIO(): 使用 Dispatchers.IO 切换线程
* withDefault(): 使用 Dispatchers.Default 切换线程
* withUnconfined(): 使用 Dispatchers.Unconfined 切换线程

### 2. Extension

为 Job、Deferred、Flow 提供一系列的扩展函数

#### 2.1 Job

* safeCancel(): 安全地取消 job
* then()

#### 2.2 Deferred

* then()
* thenAsync()
* awaitOrNull(): Deferred 返回的值以及对超时的处理，超时会返回 null
* map()
* flatMap()
* concatMap()
* zipWith()

#### 2.3 Flow

* toSuspend()
* onCompleted()
* awaitFirst()
* resumeOnError()
