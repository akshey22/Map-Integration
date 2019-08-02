package org.unyde.mapintegrationlib.worker.helper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class LiveDataHelper {

    private MediatorLiveData<Integer> _percent = new MediatorLiveData<>();

    private LiveDataHelper() {
    }

    private static LiveDataHelper liveDataHelper;

    synchronized public static LiveDataHelper getInstance() {
        if (liveDataHelper == null)
            liveDataHelper = new LiveDataHelper();
        return liveDataHelper;
    }

    public void updatePercentage(int percentage) {
        _percent.postValue(percentage);
    }

   public LiveData<Integer> observePercentage() {
        return _percent;
    }
}
