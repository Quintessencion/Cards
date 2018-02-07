package com.simbirsoft.igorverbkin.mycards.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;
import com.simbirsoft.igorverbkin.mycards.mvp.rest.CardRepository;
import com.simbirsoft.igorverbkin.mycards.mvp.view.HunterCardsView;

import javax.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

@InjectViewState
public class MyCardsPresenter extends MvpPresenter<HunterCardsView> implements OrderedRealmCollectionChangeListener<RealmResults<Card>> {

    private static MyCardsPresenter presenter = new MyCardsPresenter();

    private static final String CARD_ID_FIELD = "id";
    private static final String CARD_PURCHASE_FIELD = "isPurchased";
    private static final boolean IS_PURCHASED = false;

    private CardRepository repository;
    private CompositeDisposable compositeDisposable;

    private Realm realm;
    private RealmResults<Card> realmResults;

    private MyCardsPresenter() {
        repository = CardRepository.newInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public static MyCardsPresenter newInstance() {
        return presenter;
    }

    @Override
    public void attachView(HunterCardsView view) {
        super.attachView(view);
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(Card.class).findAllAsync();
        realmResults.addChangeListener(this);
    }

    @Override
    public void onChange(@NonNull RealmResults<Card> realmResults, @Nullable OrderedCollectionChangeSet changeSet) {
        if (!realmResults.isEmpty()) {
            getViewState().updateCards(realm.where(Card.class).equalTo(CARD_PURCHASE_FIELD, true).findAll());
        }
    }

    public void submitCard(String id) {
        Card card = realm.where(Card.class).equalTo(CARD_ID_FIELD, id).findFirst();
        if (card == null) {
            return;
        }

        Card updatedCard = new Card(id, card.getCardName(), card.getPrice(), card.getEndDate(), IS_PURCHASED);
        Disposable cardDisposable = repository.updateData(updatedCard)
                .doOnSubscribe(subscription -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().hideLoading())
                .subscribe(c -> {
                    realm.executeTransactionAsync(realm -> realm.insertOrUpdate(c));
                }, getViewState()::showError);

        compositeDisposable.clear();
        compositeDisposable.add(cardDisposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        realmResults.removeAllChangeListeners();
        realm.close();
    }
}
