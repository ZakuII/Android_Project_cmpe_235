package com.example.android_project_cmpe_235;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomItemizedOverlay extends ItemizedOverlay<OverlayItem>{

    private final ArrayList<OverlayItem> Overlay = new ArrayList<OverlayItem>();

    private Context context;

    public CustomItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
    }

    public CustomItemizedOverlay(Drawable draw, Context context) {
        this(draw);
        this.context = context;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return Overlay.get(i);
    }

    @Override
    public int size() {
        return Overlay.size();
    }

    public void insertOverlay(OverlayItem overlay) {
        Overlay.add(overlay);
        this.populate();
    }
}
