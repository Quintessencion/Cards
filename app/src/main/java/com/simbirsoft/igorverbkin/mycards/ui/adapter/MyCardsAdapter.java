package com.simbirsoft.igorverbkin.mycards.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class MyCardsAdapter extends RecyclerView.Adapter<MyCardsViewHolder> {

    private List<Card> cards = new ArrayList<>();
    private AdapterAction action;

    public MyCardsAdapter(AdapterAction action) {
        this.action = action;
    }

    public void updateList(List<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    @Override
    public MyCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCardsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cards_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyCardsViewHolder holder, final int position) {
        holder.bindView(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void removeCard(int position) {
        action.submitCard(cards.get(position).getId());
    }
}