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

/**
 * Created by suhai on 7/21/2017.
 */

public class PromotionFragment extends Fragment {

    private PromotionFragment.OnFragmentInteractionListener mListener;
    private final String ipAddress = "http://192.168.1.217/";
    private ImageView ivPromotion;
    private String promotionImage;
    public PromotionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            Bundle bundlePromotion = getArguments();
            if (bundlePromotion != null)
                promotionImage = getArguments().getString("promotion");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        ivPromotion = (ImageView)view.findViewById(R.id.ivPromotion);

        Glide.with(this)
                .load(ipAddress + promotionImage)
                .into(ivPromotion);

        final ImagePopup imagePopup = new ImagePopup(getContext());
        imagePopup.setWindowHeight(650);
        imagePopup.setWindowWidth(450);
        imagePopup.setBackgroundColor(Color.argb(50,255,255,255));
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

        ivPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.initiatePopup(ivPromotion.getDrawable());
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
        if (context instanceof PromotionFragment.OnFragmentInteractionListener) {
            mListener = (PromotionFragment.OnFragmentInteractionListener) context;
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
