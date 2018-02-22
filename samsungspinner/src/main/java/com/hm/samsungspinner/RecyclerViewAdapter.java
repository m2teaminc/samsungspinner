package com.hm.samsungspinner;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Min
 * @since 2/22/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PopupViewHolder> {

    private Context mContext;
    private List<String> mItems;
    private ItemCallback mCallback;
    private int selected = -1;
    private int selectedColor;
    private int normalColor;
    private float itemHeight;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        this.mItems = new ArrayList<>();
    }

    public void setCallback(ItemCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void setDropdownData(List<String> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public PopupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopupViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_popup, parent, false));
    }

    private float convert2Px(float dp) {
        Resources r = mContext.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private  float pxFromDp(final float dp) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    private float convertDpToPixel(float dp){
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public void onBindViewHolder(final PopupViewHolder holder, int position) {
        final String content = mItems.get(position);
        holder.tvContent.setText(content);
        holder.tvContent.setHeight((int) pxFromDp(itemHeight));
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null)
                    mCallback.onItemClicked(holder.getAdapterPosition(), content);
            }
        });
        if (selected == position) {
            holder.tvContent.setTextColor(selectedColor);
        } else {
            holder.tvContent.setTextColor(normalColor);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

    interface ItemCallback {
        void onItemClicked(int position, String value);
    }

    class PopupViewHolder extends RecyclerView.ViewHolder {

        private TextView tvContent;

        PopupViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
