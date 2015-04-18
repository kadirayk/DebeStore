package com.kadirayk.debestore.network;

import com.kadirayk.debestore.model.DebeDetailItem;
import com.kadirayk.debestore.model.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class NetworkController {

    public NetworkController() {

    }

    public interface OnDebeListResponseRecievedListener {
        public void OnDebeListResponseRecieved(ArrayList<DebeListItem> debeListItems);
    }

    public interface OnDebeDetailResponseRecievedListener {
        public void OnDebeDetailResponseRecieved(DebeDetailItem debeDetailItem);
    }
}
