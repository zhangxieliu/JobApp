package com.fosu.jobapp.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.activity.JobDetailActivity;
import com.fosu.jobapp.utils.DensityUtils;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class ZoomFragment extends Fragment {
    private FadingActionBarHelper mFadingHelper;
    private SliderLayout slide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.color.colorPrimary)
                .headerLayout(R.layout.fragment_zoom)
                .contentLayout(R.layout.listview);
        mFadingHelper.initActionBar(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = mFadingHelper.createView(inflater);
        slide = (SliderLayout) view.findViewById(R.id.slider);
//
//        HashMap<String, String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
//
//        for (String name : url_maps.keySet()) {
//            TextSliderView textSliderView = new TextSliderView(getActivity());
//            // initialize a SliderLayout
//            textSliderView
//                    .description(name)
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
//                    .setOnSliderClickListener(null);
//
//            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra", name);
//
//            slide.addSlider(textSliderView);
//        }
//        slide.setPresetTransformer(SliderLayout.Transformer.Fade);
//        slide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        slide.setCustomAnimation(new DescriptionAnimation());
//        slide.setDuration(4000);
        SwipeMenuListView listView = (SwipeMenuListView) view.findViewById(android.R.id.list);
        listView.setAdapter(new BaseAdapter() {
            List<Object> list = new ArrayList<Object>(10);

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = View.inflate(getActivity(), R.layout.layout_job_list_item, null);
                return view1;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), JobDetailActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                deleteItem.setWidth(DensityUtils.dp2px(getActivity(), 80));
                // set a icon
                deleteItem.setIcon(R.drawable.icon_not_interested);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        listView.setCloseInterpolator(new AccelerateDecelerateInterpolator());
        listView.setOpenInterpolator(new BounceInterpolator());
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        slide.stopAutoCycle();
        super.onStop();
    }
}
