package com.kadirayk.debestore.network;

import com.kadirayk.debestore.models.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class NetworkController {

    public NetworkController() {

    }

    public interface OnDebeListResponseRecievedListener {
        public void OnYMLEResponseRecieved(ArrayList<DebeListItem> debeListItems);
    }

}
