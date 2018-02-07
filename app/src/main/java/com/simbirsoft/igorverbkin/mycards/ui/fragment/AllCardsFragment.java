package com.simbirsoft.igorverbkin.mycards.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;
import com.simbirsoft.igorverbkin.mycards.mvp.presenter.AllCardsPresenter;
import com.simbirsoft.igorverbkin.mycards.mvp.view.HunterCardsView;
import com.simbirsoft.igorverbkin.mycards.mvp.view.LoadingView;
import com.simbirsoft.igorverbkin.mycards.ui.adapter.AllCardsAdapter;
import com.simbirsoft.igorverbkin.mycards.ui.adapter.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class AllCardsFragment extends Fragment implements RecyclerViewClickListener, HunterCardsView, LoadingView {

    private int selectedItem = NO_POSITION;
    private int position;
    private String id;
    private String price;

    private AllCardsPresenter presenter;
    private AllCardsAdapter adapter;

    @BindView(R.id.btn_buy) Button btnBuy;
    @BindView(R.id.all_card_progress_bar) ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new AllCardsAdapter(this);
        presenter = AllCardsPresenter.newInstance();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        View view = inflater.inflate(R.layout.all_cards_fragment, container, false);
        ButterKnife.bind(this, view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (id != null && price != null && selectedItem != NO_POSITION) {
            activateButton();
        }
    }

    private void activateButton() {
        btnBuy.setEnabled(true);
        btnBuy.setText(String.format(getActivity().getResources().getString(R.string.buy_for), price));
    }

    @Override
    public void enableButton(String id, String price, int position) {
        this.position = position;
        this.id = id;
        this.price = price;
        activateButton();
    }

    @Override
    public void disableButton() {
        btnBuy.setText("");
        btnBuy.setEnabled(false);
        adapter.notifyItemChanged(position);
    }

    @OnClick(R.id.btn_buy)
    public void clickBuy() {
        presenter.submitCard(id);
        disableButton();
        selectedItem = NO_POSITION;
    }

    @Override
    public int getNumbSelectedItem() {
        return selectedItem;
    }

    @Override
    public void setNumbSelectedItem(int value) {
        selectedItem = value;
    }

    @Override
    public void updateCards(List<Card> data) {
        adapter.updateList(data);
    }

    @Override
    public void notifyOfAction(String price) {
        Toast.makeText(getActivity(), String.format(getString(R.string.card_bought_n_rubles),
                price), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable t) {
        Toast.makeText(getActivity(), R.string.unsuccessful_download, Toast.LENGTH_SHORT).show();
        Log.e("HunterCards", t.getMessage());
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
