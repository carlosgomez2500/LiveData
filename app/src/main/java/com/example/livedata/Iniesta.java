package com.example.livedata;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Iniesta {

    interface iniestaListener {
        void cuandoCambieAccion(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> marcando;

    void iniciarEntrenamiento(final iniestaListener iniestaListener) {
        if (marcando == null || marcando.isCancelled()) {
            marcando = scheduler.scheduleAtFixedRate(new Runnable() {
                int cosa;

                @Override
                public void run() {
                    cosa = random.nextInt(4);    // 0 1 2 3

                    if(cosa == 0){
                        iniestaListener.cuandoCambieAccion("GOL");
                    } else if(cosa == 1){
                        iniestaListener.cuandoCambieAccion("CELEBRACION");
                    } else if(cosa == 2){
                        iniestaListener.cuandoCambieAccion("REGATE");
                    } else if(cosa == 3){
                        iniestaListener.cuandoCambieAccion("HABLAR");
                    }
                }
            }, 0, 4, SECONDS);
        }
    }

    void pararEntrenamiento() {
        if (marcando != null) {
            marcando.cancel(true);
        }
    }




    LiveData<String> accionLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEntrenamiento(new iniestaListener() {
                @Override
                public void cuandoCambieAccion(String accion) {
                    postValue(accion);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEntrenamiento();
        }
    };
}
