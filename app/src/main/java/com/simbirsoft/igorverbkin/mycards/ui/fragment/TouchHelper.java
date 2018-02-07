package com.simbirsoft.igorverbkin.mycards.ui.fragment;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.ui.adapter.MyCardsAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;

class TouchHelper extends ItemTouchHelper.Callback {

    private MyCardsAdapter adapter;
    private Resources resources;
    private Paint paint;

    public TouchHelper(MyCardsAdapter adapter, Resources resources) {
        this.adapter = adapter;
        this.resources = resources;
        paint = new Paint();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (LEFT == direction) {
            adapter.removeCard(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ACTION_STATE_SWIPE) {

            View itemView = viewHolder.itemView;
            float width = ((float) itemView.getBottom() - itemView.getTop()) / 3;

            if (dX < 0) {
                paint.setColor(resources.getColor(R.color.red));
                RectF background = new RectF((float) itemView.getRight() + dX,
                        (float) itemView.getTop(),
                        (float) itemView.getRight(),
                        (float) itemView.getBottom());
                c.drawRect(background, paint);
                RectF iconDest = new RectF((float) itemView.getRight() - 2 * width,
                        (float) itemView.getTop() + width,
                        (float) itemView.getRight() - width,
                        (float) itemView.getBottom() - width);
                c.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_del), null, iconDest, paint);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
