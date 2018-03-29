package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;


import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Shelters. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShelterDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ShelterListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private final SimpleItemRecyclerViewAdapter mAdapter = new SimpleItemRecyclerViewAdapter(Model.getInstance().getShelters());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        SearchView searchView = (SearchView) findViewById(R.id.searchview1);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.shelter_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.shelter_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> implements Filterable {

        private final List<Shelter> mValues;
        private List<Shelter> mValuesFiltered;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shelter item = (Shelter) view.getTag();
                Log.d("ACK", Integer.toString(item.getId())); //debug #todo delete this and its import
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ShelterDetailFragment.ARG_ITEM_ID, item.getName()); //Pass the name to the detail fragment
                    ShelterDetailFragment fragment = new ShelterDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.shelter_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ShelterDetailActivity.class);
                    intent.putExtra(ShelterDetailFragment.ARG_ITEM_ID, item.getName());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(List<Shelter> items) {
            mValues = items;
            mValuesFiltered = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shelter_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValuesFiltered.get(position).getName());
            //holder.mContentView.setText(mValuesFiltered.get(position).getPhoneNumber()); //#TODO: I think more info in our list goes here

            holder.itemView.setTag(mValuesFiltered.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValuesFiltered.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mValuesFiltered = mValues;
                    } else {
                        List<Shelter> filteredList = new ArrayList<>();
                        for (Shelter row : mValues) {

                            // match conditions
                            // here we are looking for name or phone number match
                            //#TODO: add better filtering options
                             if (row.getName().toLowerCase().contains(charString.toLowerCase()) ||
                                     row.getRestrictions().toLowerCase().contains(charString.toLowerCase())) {
                                 if (charString.toLowerCase().contentEquals("men")) {
                                     if(!row.getRestrictions().toLowerCase().contains("women") ||
                                             row.getRestrictions().toLowerCase().contains("anyone")) {
                                         filteredList.add(row); //#TODO need a better way, but this kinda handles weird 'men" characters in 'women' case
                                     }

                                 } else if (charString.toLowerCase().contentEquals("male")) {
                                     if(!row.getRestrictions().toLowerCase().contains("female") ||
                                             row.getRestrictions().toLowerCase().contains("anyone")) {
                                         filteredList.add(row);//#TODO need a better way, but this kinda handles weird 'men" characters in 'women' case
                                     }
                                 } else {
                                     filteredList.add(row);
                                 }
                            }

                        }

                        mValuesFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mValuesFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mValuesFiltered = (ArrayList<Shelter>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

    }
}
