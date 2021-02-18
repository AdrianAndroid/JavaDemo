package com.flannery.javademo.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.flannery.javademo.bean.Person;
import com.flannery.javademo.common.IPersonManager;
import com.flannery.javademo.common.Stub;
import com.flannery.javademo.server.RemoteService;

import java.util.List;

class ClientActivity extends Activity {


    private IPersonManager iPersonManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, RemoteService.class);
        intent.setAction("com.enjoy.binder");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        findViewById(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("leo", "------------onClick:" + Thread.currentThread());
                    iPersonManager.addPerson(new Person("leo", 3));
                    List<Person> persons = iPersonManager.getPersonList();
                    Log.e("leo", persons.toString() + "," + Thread.currentThread());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("leo", "onServiceConnected: success");
            iPersonManager = Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("leo", "onServiceDisconnected: success");
            iPersonManager = null;
        }
    };
}