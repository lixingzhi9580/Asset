package rxJava2;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.plugins.RxJavaPlugins;
public abstract class RxJava2Test<T> implements ObservableSource<T> {
	private static final Logger Log = LoggerFactory.getLogger(RxJava2Test.class);
	
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

		Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
			@Override
			public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
				Log.info("Observable emit 1");
				e.onNext(1);
				Log.info("Observable emit 2");
				e.onNext(2);
				Log.info("Observable emit 3");
				e.onNext(3);
				e.onComplete();
				Log.info("Observable emit 4");
				e.onNext(4);
			}
		}).subscribe(new Observer<Integer>() { // 第三步：订阅

			// 第二步：初始化Observer
			private int i = 3;
			private Disposable mDisposable;

			@Override
			public void onSubscribe(@NonNull Disposable d) {
				mDisposable = d;
			}

			@Override
			public void onNext(@NonNull Integer integer) {
				Log.info("onNext" + i);
				i++;
				if (i == 2) {
					// 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
					mDisposable.dispose();
				}
			}

			@Override
			public void onError(@NonNull Throwable e) {
				Log.info("onError : value : " + e.getMessage());
			}

			@Override
			public void onComplete() {
				Log.info("onComplete");
			}
		});
	
	}

}
