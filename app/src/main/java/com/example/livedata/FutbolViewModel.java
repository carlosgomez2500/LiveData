package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class FutbolViewModel extends AndroidViewModel {
    Iniesta iniesta;

    LiveData<Integer> futbolLiveData;
    LiveData<String> repeticionLiveData;

    public FutbolViewModel(@NonNull Application application) {
        super(application);

        iniesta = new Iniesta();

        futbolLiveData = Transformations.switchMap(iniesta.accionLiveData, new Function<String, LiveData<Integer>>() {

            @Override
            public LiveData<Integer> apply(String accion) {

                    int imagen;
                    switch (accion) {
                        case "GOL":
                        default:
                            imagen = R.drawable.gol;
                            break;
                        case "CELEBRACION":
                            imagen = R.drawable.celebracion;
                            break;
                        case "REGATE":
                            imagen = R.drawable.regate;
                            break;
                        case "HABLAR":
                            imagen = R.drawable.hablar;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
            }
        });


    }
}