package com.yang.testwificonnect;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * author: ybjaychou@gmail.com
 * data: 2015-10-13
 */
public class StaticHandlerFactory {
    private static final String TAG = "StaticHandlerFactory";

    public static StaticHandler create(IStaticHandler ref){
        return new StaticHandler(ref);
    }


    private static class StaticHandler extends Handler{

        WeakReference<IStaticHandler> weakRef;

        public StaticHandler(IStaticHandler ref ) {
//            super(looper);
            Log.d(TAG,"IStaticHandler="+ref);
            this.weakRef = new WeakReference<>(ref);
        }

        @Override
        public void handleMessage(Message msg) {
            if(weakRef.get() != null){
                weakRef.get().handleMessage(msg);
            }else{
                throw new RuntimeException("WeakReference<IStaticHandler> get null object!");
            }
        }


    }
}
