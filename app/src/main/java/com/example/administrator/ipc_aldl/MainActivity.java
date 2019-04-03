package com.example.administrator.ipc_aldl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    private boolean mBound = false;
    //暴露出来的是IMyAiDlTnterface;
    private IMyAidlInterface mBinder;
    private final String TAG = "MainActivity";
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"客户端连接服务器完成");
            mBinder = IMyAidlInterface.Stub.asInterface(service);
            mBound = true;
            if (mBinder !=null){
                try {
                    List<Book> books = mBinder.getBooks();
                    for (Book book: books) {
                        Log.e(TAG,"客户端"+book.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }else {
                Log.e(TAG,"binder is null");
            }
          }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"service is not connect");
            mBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_t);
        init();
    }

    private void init() {
        TextView btn_aa = findViewById(R.id.btn_aa);
        btn_aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBound){
                    attemptToBindService();
                    Toast.makeText(getApplicationContext(),"未与远程服务器建立连接请从新连接！",Toast.LENGTH_LONG).show();
                }
                if (mBinder !=null){
                    Book book = new Book();
                    book.setName("人生哲学");
                    book.setPrice(1000);
                    try {
                        mBinder.addBookIn(book);
                      Log.e(TAG,"书的数量"+mBinder.getBookCount());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    private void  attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.example.administrator.ipc_aldl");
        intent.setPackage("com.example.administrator.ipc_aldl");
        bindService(intent,mConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound){
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(mConnection);
            mBound =false;
        }
    }
}
