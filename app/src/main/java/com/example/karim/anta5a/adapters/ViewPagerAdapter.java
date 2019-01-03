package com.example.karim.anta5a.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.karim.anta5a.APIs.RetrofitSystem;
import com.example.karim.anta5a.Activities.Service2Activity;
import com.example.karim.anta5a.R;
import com.example.karim.anta5a.models.MainServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    // arrays for images
    public int[] slideImages =
            {
                    R.drawable.carpent,
                    R.drawable.elect,
                    R.drawable.helthy
            };


    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout, container, false);
        CardView cardView = view.findViewById(R.id.card_view_slide);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Service2Activity.class));
            }
        });
        ImageView imageView = cardView.findViewById(R.id.image_view_slide);
        Call<List<MainServices>> call = RetrofitSystem.getInstance().getApi().getMainServices();
        call.enqueue(new Callback<List<MainServices>>() {
            @Override
            public void onResponse(Call<List<MainServices>> call, Response<List<MainServices>> response) {
                List<MainServices> res = response.body();
                res.get(position);
                // TODO complete main services
            }

            @Override
            public void onFailure(Call<List<MainServices>> call, Throwable t) {

            }
        });
        imageView.setImageResource(slideImages[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);
    }
}
