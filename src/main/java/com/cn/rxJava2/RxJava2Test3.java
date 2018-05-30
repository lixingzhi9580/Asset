//package com.cn.rxJava2;
//
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.reactivex.Flowable;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
//public class RxJava2Test3 {
//	private static final Logger Log = LoggerFactory.getLogger(RxJava2Test3.class);
//
//	public static void main(String[] args) {
//		
//
//	private Disposable mDisposable;
//    @Override
//    protected void doSomething() {
//        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
//                .doOnNext(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        Log.e(TAG, "accept: doOnNext : "+aLong );
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        Log.e(TAG, "accept: 设置文本 ："+aLong );
//                        mRxOperatorsText.append("accept: 设置文本 ："+aLong +"\n");
//                    }
//                });
//    }
//
//    /**
//     * 销毁时停止心跳
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mDisposable != null){
//            mDisposable.dispose();
//        }
//    }
//	}
//}
