package com.example.cardmanagedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.widget.CardLinearLayout;

public class MainActivity extends AppCompatActivity {

    private CardLinearLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardLayout=findViewById(R.id.cardLayout);
        initCardLayout();
    }

    private void initCardLayout(){
        int width=690;
        int height=357;

        int[] res = {R.drawable.bg_card1, R.drawable.bg_card2, R.drawable.bg_card3, R.drawable.bg_card4};
        String[] title = {"普通会员", "高级会员", "会员黑卡", "至尊VIP"};
        int size = 4;
        for (int i = 0; i < size; i++) {
            View itemCard = View.inflate(this, R.layout.item_card_item, null);
            if (i == 0) {
                ImageView imageViewMainImage = itemCard.findViewById(R.id.imageViewMainImage);
                RelativeLayout.LayoutParams lpp = (RelativeLayout.LayoutParams) imageViewMainImage.getLayoutParams();
                imageViewMainImage.setLayoutParams(lpp);
            }

            ImageView imageViewMainImage = itemCard.findViewById(R.id.imageViewMainImage);
            imageViewMainImage.setImageResource(res[i]);
            ((TextView) itemCard.findViewById(R.id.textViewCardName)).setText(title[i]);
            //默认都展开
            itemCard.findViewById(R.id.imageViewTopCover).setVisibility(View.INVISIBLE);
            itemCard.findViewById(R.id.imageViewShadow).setVisibility(View.INVISIBLE);

            if (i == (size - 1)) {
                itemCard.findViewById(R.id.viewWalletContainer).setVisibility(View.VISIBLE);
            } else {
                itemCard.findViewById(R.id.viewWalletContainer).setVisibility(View.INVISIBLE);
            }
            itemCard.setTag(String.valueOf(i));
            itemCard.setOnClickListener(v -> {
                cardLayout.expandItem(Integer.parseInt((String) v.getTag()));
            });

            LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardLayout.addView(itemCard, lpp);
        }
    }
}