package com.cityconnect.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cityconnect.R;
import com.cityconnect.adapter.ViewPagerAdapter;
import com.cityconnect.interfaces.PermissionInterface;
import com.cityconnect.utility.AppConstants;
import com.cityconnect.utility.AppPreferences;
import com.cityconnect.utility.LocationManager;
import com.cityconnect.utility.MarshMallowPermission;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment implements PermissionInterface {

    private OnFragmentInteractionListener mListener;
    Context mContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    MarshMallowPermission marshMallowPermission;
    EditText currentLocation;
    private String latitude = "", longitude = "";

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        marshMallowPermission = new MarshMallowPermission(mContext,getActivity());
        currentLocation = (EditText)rootView.findViewById(R.id.current_location_edt);

        getLocationPermission();
        return rootView;
    }

    private void getLocationPermission() {
        if (marshMallowPermission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,mContext,getActivity())) {
            accessCurrentLocation();
        }
        else
        {
            marshMallowPermission.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,marshMallowPermission.PERMISSION_REQUEST_CODE_LOCATION,mContext,getActivity());
        }
    }



    private void accessCurrentLocation(){

        Log.d("Now You can Access Location","");

        LocationManager locationManager = new LocationManager(mContext,
                new LocationManager.LocationCallbackListener() {

                    @Override
                    public void onLocationReceived(Location location) {

                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location
                                    .getLongitude());
                            latitude = "" + location.getLatitude();
                            longitude = "" + location.getLongitude();
                            AppPreferences appPreferences = AppPreferences.getInstance(mContext);
                            appPreferences.putString(AppConstants.LOC_LATITUDE, latitude);
                            appPreferences.putString(AppConstants.LOC_LONGITUDE, longitude);
                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(mContext, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            //String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();
                           // Log.d("address  "+address,"City "+city);
                                Log.d("state  "+state,"country "+country);
                                Log.d("postalCode  "+postalCode,"knownName "+knownName);
                                appPreferences.putString(AppConstants.CURRENT_LOCATION, city);
                                currentLocation.setHint("Your current location is "+city);
                            } catch (IOException e) {
                                e.printStackTrace();
                                currentLocation.setHint("Unable to get location");
                            }

                        } else {

                        }
                    }
                });

        locationManager.fetchLocation();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(),false);
        adapter.addFragment(new PolititionsLocationFragment(), "Politicians");
        adapter.addFragment(new IssuesLocationsFragment(), "Issues");
        viewPager.setAdapter(adapter);
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRecievedLocationPermission(boolean isGranted) {
        if(isGranted)
            Log.d("Granted Location Access","");
        else
            Log.d("Location Access Not Granted","");

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}


