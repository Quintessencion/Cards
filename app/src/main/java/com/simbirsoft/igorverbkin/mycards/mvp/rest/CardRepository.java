package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardRepository {

    private static final CardRepository INSTANCE = new CardRepository();
    private CardRestService cardRestService = RestServiceCardProvider.newInstance().getRestService();

    private CardRepository() {
    }

    public static CardRepository newInstance() {
        return INSTANCE;
    }

    public Flowable<List<Card>> loadData() {
        return cardRestService.getCardList()
                .map(ResponseApiModel::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Card> updateData(Card card) {
        return cardRestService.updateCard(card, card.getId())
                .map(ResponseApiModel::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
