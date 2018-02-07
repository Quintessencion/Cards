package com.simbirsoft.igorverbkin.mycards.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;
import com.simbirsoft.igorverbkin.mycards.mvp.presenter.MyCardsPresenter;
import com.simbirsoft.igorverbkin.mycards.mvp.view.HunterCardsView;
import com.simbirsoft.igorverbkin.mycards.ui.adapter.AdapterAction;
import com.simbirsoft.igorverbkin.mycards.ui.adapter.MyCardsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCardsFragment extends Fragment implements HunterCardsView, AdapterAction {

    private MyCardsPresenter presenter;
    private MyCardsAdapter adapter;

    @BindView(R.id.my_card_progress_bar) ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyCardsAdapter(this);
        presenter = MyCardsPresenter.newInstance();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cards_fragment, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_my_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter, getActivity().getResources()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void updateCards(List<Card> cards) {
        adapter.updateList(cards);
    }

    @Override
    public void submitCard(String id) {
        presenter.submitCard(id);
    }

    @Override
    public void showError(Throwable t) {

    }

    @Override
    public void notifyOfAction(String value) {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
