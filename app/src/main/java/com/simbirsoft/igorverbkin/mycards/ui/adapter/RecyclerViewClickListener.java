package com.simbirsoft.igorverbkin.mycards.ui.adapter;

public interface RecyclerViewClickListener {

    void enableButton(String id, String price, int position);

    void disableButton();

    int getNumbSelectedItem();

    void setNumbSelectedItem(int value);
}
