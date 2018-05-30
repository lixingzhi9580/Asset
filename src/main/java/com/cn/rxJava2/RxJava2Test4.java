package com.cn.rxJava2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxJava2Test4 {
	private static final Logger Log = LoggerFactory.getLogger(RxJava2Test4.class);

	@Test
	public void create1() {// 创建一个上游 Observable：
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
				emitter.onComplete();
			}
		});
		// 创建一个下游 Observer
		Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.info("subscribe");
			}

			@Override
			public void onNext(Integer value) {
				Log.info("" + value);
			}

			@Override
			public void onError(Throwable e) {
				Log.info("error");
			}

			@Override
			public void onComplete() {
				Log.info("complete");
			}
		};
		// 建立连接
		observable.subscribe(observer);
	}

	
	@Test
	public void create2() {// 创建一个上游 Observable：
		Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.info("subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.info("" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.info("error");
            }

            @Override
            public void onComplete() {
                Log.info("complete");
            }
        });
	}
	
	@Test
	public void create3() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.info("emit 1");
                emitter.onNext(1);
                Log.info("emit 2");
                emitter.onNext(2);
                Log.info("emit 3");
                emitter.onNext(3);
                Log.info("emit complete");
                emitter.onComplete();
                Log.info("emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.info("subscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.info("onNext: " + value);
                i++;
                if (i == 2) {
                    Log.info("dispose");
                    mDisposable.dispose();
                    Log.info("isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.info("error");
            }

            @Override
            public void onComplete() {
                Log.info("complete");
            }
        });
	}
	
	@Test
	public void create4() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.info("emit 1");
                emitter.onNext(1);
                Log.info("emit 2");
                emitter.onNext(2);
                Log.info("emit 3");
                emitter.onNext(3);
                Log.info("emit complete");
                emitter.onComplete();
                Log.info("emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.info("onNext: " + integer);
            }
        });
	}
}
