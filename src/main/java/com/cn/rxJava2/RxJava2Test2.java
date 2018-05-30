package com.cn.rxJava2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public abstract class RxJava2Test2<T> implements ObservableSource<T> {
	private static final Logger Log = LoggerFactory.getLogger(RxJava2Test2.class);

	/**
	 * subscribeOn 指定的就是发射事件的线程
	 * 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
	 * @param scheduler
	 * @return
	 */
	@SchedulerSupport(SchedulerSupport.CUSTOM)
    public final Observable<T> subscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new ObservableSubscribeOn<T>(this, scheduler));
    }

	/**
	 * observerOn 指定的就是订阅者接收事件的线程。
	 * 多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
	 * @param scheduler
	 * @param delayError
	 * @param bufferSize
	 * @return
	 */
	@SchedulerSupport(SchedulerSupport.CUSTOM)
    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError, int bufferSize) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return RxJavaPlugins.onAssembly(new ObservableObserveOn<T>(this, scheduler, delayError, bufferSize));
    }
	
	public static void main(String[] args) {
		Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.info("Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                    	Log.info("After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                    	Log.info("After observeOn(io)，Current thread is " + Thread.currentThread().getName());
                    }
                });
	}
}
