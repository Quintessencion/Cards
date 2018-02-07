package com.simbirsoft.igorverbkin.mycards.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class AllCardsAdapter extends RecyclerView.Adapter<AllCardsViewHolder> {

    private List<Card> cards = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public AllCardsAdapter(RecyclerViewClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    @Override
    public AllCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllCardsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_cards_list_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(final AllCardsViewHolder holder, final int position) {
        holder.bindView(cards.get(position), position);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
