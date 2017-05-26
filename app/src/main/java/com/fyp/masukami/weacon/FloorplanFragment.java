package com.fyp.masukami.weacon;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ceylonlabs.imageviewpopup.ImagePopup;

public class FloorplanFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String floor;
    private final String ipAddress = "http://192.168.1.176/";
    private ImageView ivFloorPlan;
    private String address;
    public FloorplanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            Bundle bundleAddress = getArguments();
            if (bundleAddress != null)
                address = getArguments().getString("address");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_floorplan, container, false);
        ivFloorPlan = (ImageView)view.findViewById(R.id.ivFloorPlan);
        floor = address.substring(7, 13);

        if(floor.equals("First ")){
            Glide.with(this)
                    .load(ipAddress + "plazawalk/advertisers/firstfloor.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .placeholder(R.drawable.ic_alert_box)
                    .error(R.drawable.ic_alert_box)
                    .into(ivFloorPlan);
        }else if (floor.equals("Ground")){
            Glide.with(this)
                    .load(ipAddress + "plazawalk/advertisers/groundfloor.png")
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                    .placeholder(R.drawable.ic_alert_box)
                    .error(R.drawable.ic_alert_box)
                    .into(ivFloorPlan);
        }

        final ImagePopup imagePopup = new ImagePopup(getContext());
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        ivFloorPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(ivFloorPlan.getDrawable());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
