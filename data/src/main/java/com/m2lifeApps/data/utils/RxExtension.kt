package com.m2lifeApps.data.utils

import com.m2lifeApps.data.Resource
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T : Any> Single<T>.toObservable(
    emitter: ObservableEmitter<Resource<T>>
): Disposable {

    return this
        .subscribeOn(Schedulers.io())
        .subscribe(
            {
                emitter.onNext(
                    Resource.success(it)
                )
                emitter.onComplete()
            },
            {
                emitter.onNext(
                    Resource.error(it)
                )

                emitter.onComplete()
            }
        )
}
