package com.simbirsoft.igorverbkin.mycards.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.mvp.model.Card;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

class MyCardsViewHolder extends RecyclerView.ViewHolder {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM '' yy", new Locale("ru"));

    private Date dateThreshold;

    @BindColor(R.color.grey) int colorGrey;
    @BindColor(R.color.blue) int colorBlue;
    @BindColor(R.color.darkGrey) int colorDarkGrey;

    @BindView(R.id.end_date) TextView endDate;
    @BindView(R.id.name_region_my_card) TextView nameRegion;
    @BindViews({R.id.end_date, R.id.name_region_my_card}) List<TextView> nameViews;

    MyCardsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        dateThreshold = calendar.getTime();
    }

    private static ButterKnife.Setter<TextView, Integer> FOREGROUND = (view, value, index) -> view.setTextColor(value);

    void bindView(Card card) {
        if (new Date(card.getEndDate()).after(dateThreshold)) {
            endDate.setText(dateFormat.format(card.getEndDate()));
            ButterKnife.apply(nameViews, FOREGROUND, colorDarkGrey);
        } else {
            nameRegion.setTextColor(colorGrey);
            endDate.setText(R.string.renewal);
            endDate.setText(endDate.getText().toString().toUpperCase());
            endDate.setTextColor(colorBlue);
            endDate.setOnClickListener(view -> {
                endDate.setText(dateFormat.format(new Date()));
                ButterKnife.apply(nameViews, FOREGROUND, colorDarkGrey);
                card.setEndDate(new Date().getTime());
            });
        }
        nameRegion.setText(card.getCardName());
    }
}
