package com.austin.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link IngredientListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinkedHashMap<String, String> data;
    private IngredientListAdapter mAdapter;
    private View mView;

    private OnFragmentInteractionListener mListener;

    public IngredientListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientListFragment newInstance(String param1, String param2) {
        IngredientListFragment fragment = new IngredientListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("data", (Serializable) data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //probably orientation change
            data = (LinkedHashMap<String, String>) savedInstanceState.getSerializable("data");
        } else {
            if (data != null) {
                //returning from backstack, data is fine, do nothing
            } else {
                //newly created, compute data
                data = new LinkedHashMap<>();
                data.put("Ingredient One", "Date One");


                FloatingActionButton addIngredientButton = (FloatingActionButton) mView.findViewById(R.id.add_ingredient_button);
                addIngredientButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog();
                    }
                });
            }
        }

        mAdapter = new IngredientListAdapter(getContext(), data);

        ListView ingredientListView = (ListView) mView.findViewById(R.id.ingredient_list_view);
        ingredientListView.setAdapter(mAdapter);


    }

    private void showDialog(){
        AlertDialog.Builder ingredientDialog = new AlertDialog.Builder(getContext());
        ingredientDialog.setTitle("Enter Ingredient Name");
        final EditText ingredientInput = new EditText(getContext());
        ingredientDialog.setView(ingredientInput);

        ingredientDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder dateDialog = new AlertDialog.Builder(getContext());
                dateDialog.setTitle("Enter Expiration Date");
                final EditText dateInput = new EditText(getContext());
                dateDialog.setView(dateInput);

                dateDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.put(ingredientInput.getText().toString(), dateInput.getText().toString());
                        mAdapter.notifyDataSetChanged();
                    }
                });

                dateDialog.setNegativeButton("Back", null);

                dateDialog.show();
            }
        });

        ingredientDialog.setNegativeButton("Cancel", null);

        ingredientDialog.show();
    }

}
