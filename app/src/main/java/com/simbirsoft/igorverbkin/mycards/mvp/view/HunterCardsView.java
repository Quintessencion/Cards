package com.simbirsoft.igorverbkin.mycards.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface HunterCardsView extends MvpView, LoadingView {

    void updateCards(List<Card> data);

    void showError(Throwable t);

    void notifyOfAction(String value);
}
