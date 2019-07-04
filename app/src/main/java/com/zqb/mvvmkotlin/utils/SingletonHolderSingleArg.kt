package com.zqb.mvvmkotlin.utils

/**
 * Used to allow Singleton with arguments in Kotlin while keeping the code efficient and safe.
 *
 * See https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
open class SingletonHolderSingleArg<out T, in A, in B>(private val creator: (A, B) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A, arg2: B): T =
            instance ?: synchronized(this) {
                instance ?: creator(arg, arg2).apply {
                    instance = this
                }
            }
}