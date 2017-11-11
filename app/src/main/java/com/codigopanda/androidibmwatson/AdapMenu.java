package com.codigopanda.androidibmwatson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by victor on 11-11-17.
 */

public class AdapMenu extends BaseAdapter {
    List<ItemMenu> lista;
    LayoutInflater inflater;
    Context cx;

    public AdapMenu(List<ItemMenu> lista,Context context){
        this.lista=lista;
        cx=context;
        inflater=(LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Holder{
        public ImageView image;
        public TextView title;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item = inflater.inflate(R.layout.item_menu,null);
        Holder h = new Holder();
        h.image = (ImageView) item.findViewById(R.id.imageicon);
        h.title = (TextView) item.findViewById(R.id.menutitle);
        h.image.setImageResource(lista.get(i).image);
        h.title.setText(lista.get(i).title);
        return item;
    }
}
