package com.austin.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String API_URL_BASE = "http://food2fork.com/api/";
    private static final String API_KEY = "0df8e6e2d9c833d689e213ea3ddd7c96";
    private static final OkHttpClient client = new OkHttpClient();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<Ingredient> mIngredientList;
    private List<String> mRecipeList;
    private IngredientListAdapter mAdapter;
    private View mView;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
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
        mView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        return mView;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //probably orientation change

        } else {

            if (mIngredientList != null) {
                //returning from backstack, data is fine, do nothing
            } else {
                //newly created, compute data
                mIngredientList = new ArrayList<>();
                mRecipeList = new ArrayList<>();

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            JSONObject searchResult = search("Avacado");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

        ListView recipeListView = (ListView) mView.findViewById(R.id.recipeListView);
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

    /**
     * Performs an HTTP GET and parses the response body as JSON.
     */
    private static JSONObject run(String url) throws IOException, JSONException {
        final Request request = new Request.Builder().url(url).build();
        final Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }

    public static JSONObject search(String query) throws IOException, JSONException {
        final String url = API_URL_BASE + "search?key=" + API_KEY + "&q=" + URLEncoder.encode(query, "UTF-8");
        return run(url);
    }

    public static JSONObject getRecipe(String id) throws IOException, JSONException {
        final String url = API_URL_BASE + "get?key=" + API_KEY + "&rId=" + id;
        return run(url);
    }

    /**
     * Extracts recipe IDs from search results.
     */
    public static List<String> getRecipeIds(JSONObject result) throws IOException, JSONException {
        final ArrayList<String> recipeIds = new ArrayList();
        final JSONArray recipes = result.getJSONArray("recipes");
        for (int i = 0; i < recipes.length(); ++i) {
            final JSONObject recipe = recipes.getJSONObject(i);
            final String id = recipe.getString("recipe_id");
            recipeIds.add(id);
        }
        return recipeIds;
    }
}
