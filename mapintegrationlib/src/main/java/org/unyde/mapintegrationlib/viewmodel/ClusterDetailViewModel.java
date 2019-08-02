package org.unyde.mapintegrationlib.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.unyde.mapintegrationlib.model.GetClusterDataList;
import org.unyde.mapintegrationlib.repository.ClusterDetailRepository;

public class ClusterDetailViewModel extends ViewModel {


    private MutableLiveData<GetClusterDataList> data;
    private ClusterDetailRepository clusterDetailRepository;
    String token;

    public ClusterDetailViewModel() {

        clusterDetailRepository = new ClusterDetailRepository();
    }

    public void init(Context context,String token, String cluster_id) {
        this.data=null;

        if (this.data != null) {

            return;
        }

        token="$xnNUH!ui:1;m]SArDPgeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NjgsImNvbXBhbnlfaWQiOjEsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwMDEvbG9naW4iLCJuYW1lIjoic3VuaWxfdW55ZGUiLCJpYXQiOjE1NjAyNDUxMDR9.oI7860RlEFPNpVcVgyGPa7HdFpna3sdF726SzoNExlA";

        data = clusterDetailRepository.getClusterDetail(context,token,cluster_id);
    }

    public MutableLiveData<GetClusterDataList> getClusterDetails() {
        return this.data;
    }

}
