package com.derrick.park.criminalmind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * Created by park on 2017-06-01.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }


    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());  // return sCrimeLab;
        List<Crime> crimes = crimeLab.getCrimes(); // return mCrimes;
        mAdater = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdater);
    }
//------------------------
    // CrimeHolder:



    private class CrimeHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDateTextView;


        public CrimeHolder(View v) {
            super(v);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);

        }
// ADDING ONCLICK + TOAST MESSAGE:
        public void bind(final Crime crime) {
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Crime number: " + crime.getTitle(), Toast.LENGTH_SHORT).show();
                                  }
            }));
        }
    }

//-----------------------
    // Adapter


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }


        //MARK 2 DIFFERENT VIEWS WITH O AND 1:
        @Override
        public int getItemViewType (int index){

            if (mCrimes.get(index).ismRequiresPolice() == true) {
                return 0;
            } else {
                return 1;
            }
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // INFLATING ONE HOLDER WITH 2 DIFFERENT VIEWS 0 OR 1 FROM THE METHOD ABOVE:
            View view = null;
            if (viewType == 0) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_crime, parent, false);// OLD VIEW
            } else if (viewType == 1) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_serious_crime, parent, false); // NEW VIEW WITH BUTTONS
            }
                return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

}