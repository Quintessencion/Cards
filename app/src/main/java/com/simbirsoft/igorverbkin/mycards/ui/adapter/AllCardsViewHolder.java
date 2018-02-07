package com.simbirsoft.igorverbkin.mycards.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

class AllCardsViewHolder extends RecyclerView.ViewHolder {

    private RecyclerViewClickListener listener;
    private ImageView imageCheckedCard;
    private TextView cardPrice, nameRegion, inscriptionSold;

    AllCardsViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.listener = listener;
        imageCheckedCard = itemView.findViewById(R.id.purchase_active);
        cardPrice = itemView.findViewById(R.id.cost_TV);
        nameRegion = itemView.findViewById(R.id.name_region_TV);
        inscriptionSold = itemView.findViewById(R.id.text_bought);
    }

    void bindView(final Card card, int position) {
        cardPrice.setText(String.format(itemView.getContext().getResources().getString(R.string.layout_for_price), card.getPrice()));
        nameRegion.setText(card.getCardName());
        inscriptionSold.setVisibility(View.GONE);
        imageCheckedCard.setVisibility(listener.getNumbSelectedItem() == position ? View.VISIBLE : View.GONE);

        if (card.getIsPurchased()) {
            inscriptionSold.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(null);
        } else {
            itemViewSetListener(card);
        }
    }

    private void itemViewSetListener(Card card) {
        itemView.setOnClickListener(v -> {
            if (listener.getNumbSelectedItem() == NO_POSITION) {
                selectItem(card);
            } else if (getAdapterPosition() != listener.getNumbSelectedItem()) {
                listener.disableButton();
                selectItem(card);
            } else if (listener.getNumbSelectedItem() == getAdapterPosition()) {
                listener.disableButton();
                listener.setNumbSelectedItem(NO_POSITION);
            }
        });
    }

    private void selectItem(Card card) {
        listener.setNumbSelectedItem(getAdapterPosition());
        imageCheckedCard.setVisibility(View.VISIBLE);
        listener.enableButton(card.getId(), card.getPrice(), getAdapterPosition());
    }
}
