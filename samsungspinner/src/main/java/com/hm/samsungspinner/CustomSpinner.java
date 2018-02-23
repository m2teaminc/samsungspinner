package com.hm.samsungspinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Min
 * @since 2/22/2018.
 */

public class CustomSpinner extends RelativeLayout {

    private TextView tvTitle;
    private ImageView ivExpand;
    private RecyclerViewAdapter mAdapter;
    private PopupWindow popupWindow;
    private int mSelectedPosition = -1;
    private String titleText;
    private int normalColor;
    private int selectedColor;
    private boolean isDarkTheme;
    private DropdownItemCallback mItemCallback;
    private OnClickListener onPopupClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.showAsDropDown(tvTitle, 0, -1 * tvTitle.getHeight());
            if (mSelectedPosition >= 0) {
                mAdapter.setSelected(mSelectedPosition);
            }
        }
    };

    public CustomSpinner(Context context) {
        this(context, null);
    }

    public CustomSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner, 0, 0);
        titleText = array.getString(R.styleable.CustomSpinner_text);
        normalColor = array.getColor(R.styleable.CustomSpinner_normalColor, ContextCompat.getColor(context, R.color.black));
        selectedColor = array.getColor(R.styleable.CustomSpinner_selectedColor, ContextCompat.getColor(context, R.color.red));
        isDarkTheme = array.getBoolean(R.styleable.CustomSpinner_isDark, false);
        init(context);
        array.recycle();
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
        if (getContext() != null)
            initTheme(getContext());
    }

    public void setItemCallback(DropdownItemCallback mItemCallback) {
        this.mItemCallback = mItemCallback;
    }

    private void init(final Context context) {
        inflate(context, R.layout.custom_spinner, this);
        tvTitle = findViewById(R.id.tv_title);
        ivExpand = findViewById(R.id.iv_expand);

        tvTitle.setText(titleText);
        tvTitle.setTextColor(normalColor);
        tvTitle.setOnClickListener(onPopupClickListener);

        initPopup(context);

        ivExpand.setOnClickListener(onPopupClickListener);
        setOnClickListener(onPopupClickListener);
        // Init theme in the last step when all components are initialized
        initTheme(context);
    }

    private void initTheme(Context context) {
        if (isDarkTheme) {
            ivExpand.setImageResource(R.drawable.ic_expand_more_white_24dp);
        } else {
            ivExpand.setImageResource(R.drawable.ic_expand_more_black_24dp);
        }
        mAdapter.setDarkTheme(isDarkTheme);
        if (isDarkTheme) {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_recycler_view_dark));
        } else {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_recycler_view));
        }
    }

    private void initPopup(Context context) {
        popupWindow = new PopupWindow(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dropdown, null);

        mAdapter = new RecyclerViewAdapter(context);
        mAdapter.setSelectedColor(selectedColor);
        mAdapter.setNormalColor(normalColor);

        mAdapter.setCallback(new RecyclerViewAdapter.ItemCallback() {
            @Override
            public void onItemClicked(int position, String value) {
                if (popupWindow.isShowing()) popupWindow.dismiss();
                tvTitle.setText(value);
                mSelectedPosition = position;
                if (mItemCallback != null) {
                    mItemCallback.onItemSelected(position, value);
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(16f);
        }

        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        tvTitle.setText(titleText);
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        mAdapter.setNormalColor(normalColor);
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
        mAdapter.setSelectedColor(selectedColor);
    }

    public void setData(List<String> data) {
        mAdapter.setDropdownData(data);
    }

    public interface DropdownItemCallback {
        void onItemSelected(int position, String value);
    }

}
