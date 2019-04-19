package com.example.myapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyCustomPagerAdapter extends PagerAdapter {

    private Context context;
    private String text[];
    private LayoutInflater layoutInflater;
 
 
    public MyCustomPagerAdapter(Context context, String text[]) {
        this.context = context;
        this.text = text;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.custom_viewpager_item, container, false);
 
        TextView textView = (TextView) itemView.findViewById(R.id.textView);
        //imageView.setImageResource(images[position]);
//        Picasso.get().load(text[position]).into(imageView);
        textView.setText(text[position]);
        container.addView(itemView);
 
        //listening to image click
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You clicked text " + (position + 1), Toast.LENGTH_LONG).show();
                Toast toast= Toast.makeText(context, "Bunch of Aglas :D ", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
//                toast.setGravity(Gravity.NO_GRAVITY| Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}