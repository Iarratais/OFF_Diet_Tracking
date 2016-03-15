package com.karl.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karl.fyp.MainActivity;
import com.karl.fyp.MySQLiteHelper;
import com.karl.fyp.R;

import java.text.DecimalFormat;

/**
 * Copyright Karl jones 2016.
 *
 * This class handles the creation of a new user profile.
 */

public class ProfileFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileFragment";

    // Spinners
    Spinner genderSpinner;

    // EditText
    EditText userNameEditText;
    EditText userHeightEditText;
    EditText userWeightEditText;

    MySQLiteHelper db;

    View rootView;

    Button saveChangesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set the title of the activity.
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile_fragment_title));

        db = new MySQLiteHelper(getActivity());

        initialiseViews();

        getInformationFromDatabase();

        hideSaveButton();

        return rootView;
    }

    /**
     * Set up the views for the fragment.
     */
    public void initialiseViews() {
        saveChangesButton = (Button) rootView.findViewById(R.id.save_changes);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        genderSpinner = (Spinner) rootView.findViewById(R.id.userGender);

        setUpSpinnerViews();

        userNameEditText = (EditText) rootView.findViewById(R.id.userName);
        userNameEditText.addTextChangedListener(userNameEditTextListener);

        userHeightEditText = (EditText) rootView.findViewById(R.id.userHeight);
        userHeightEditText.addTextChangedListener(userHeightEditTextListener);

        userWeightEditText = (EditText) rootView.findViewById(R.id.userWeight);
        userWeightEditText.addTextChangedListener(userWeightEditTextListener);

        hideSaveButton();
    }

    private final TextWatcher userNameEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            showSaveButton();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final TextWatcher userHeightEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            showSaveButton();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final TextWatcher userWeightEditTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            showSaveButton();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    /**
     * Set up the spinners in the fragment.
     */
    public void setUpSpinnerViews() {
        String[] genders = getResources().getStringArray(R.array.gender);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genders);
        genderSpinner.setAdapter(adapter);
        genderSpinner.setSelection(adapter.getPosition("Unspecified"));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUserProfile();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Get the information from the views and update the users profile.
     */
    public void updateUserFromViewInformation() {
        db.updateUser(userNameEditText.getText().toString(), genderSpinner.getSelectedItem().toString(), userWeightEditText.getText().toString(), userHeightEditText.getText().toString());

        ((MainActivity) getActivity()).setNameNavigationHeader();

        hideSaveButton();

        Toast.makeText(this.getActivity(), getString(R.string.profile_fragment_saved), Toast.LENGTH_SHORT).show();
    }

    /**
     * This saves the changes to the user profile and updates the users BMI display.
     */
    public void updateUserProfile() {
        updateUserFromViewInformation();
        calculateUserBMI();
        ((MainActivity)getActivity()).setNameNavigationHeader();
    }

    /**
     * Get information from the database regarding the user.
     */
    public void getInformationFromDatabase() {
        Cursor userInformation = db.getUser();

        if(userInformation.getCount() == 0){
            Log.d(TAG, "No users exist");
        } else {
            while (userInformation.moveToNext()) {
                userNameEditText.setText(userInformation.getString(1));
                ArrayAdapter adapter = (ArrayAdapter) genderSpinner.getAdapter();
                genderSpinner.setSelection(adapter.getPosition(userInformation.getString(2)));
                userHeightEditText.setText(userInformation.getString(4));
                userWeightEditText.setText(userInformation.getString(3));
            }
        }
    }

    /**
     * Show ths save changes button.
     */
    public void showSaveButton() {
        saveChangesButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the save changes button.
     */
    public void hideSaveButton() {
        saveChangesButton.setVisibility(View.GONE);
    }

    /**
     * Calculate the users BMI and set the information for it.
     * @return users BMI.
     */
    public String calculateUserBMI() {
        Double bodyMassIndex = Double.parseDouble(userWeightEditText.getText().toString());

        bodyMassIndex = bodyMassIndex / (Double.parseDouble(userHeightEditText.getText().toString()) * Double.parseDouble(userHeightEditText.getText().toString()));

        DecimalFormat df = new DecimalFormat("#.00");

        TextView BMIDisplayTextView = (TextView) rootView.findViewById(R.id.profile_bmi_display);
        BMIDisplayTextView.setText(getString(R.string.profile_fragment_your_BMI_is, String.valueOf(df.format(bodyMassIndex))));

        TextView BMIDisplayInformationTextView = (TextView) rootView.findViewById(R.id
                .profile_bmi_display_information);
        if(bodyMassIndex <= 18.4){
            BMIDisplayInformationTextView.setText(getString(R.string.profile_fragment_BMI_underweight));
        } else if (bodyMassIndex >= 18.5 && bodyMassIndex <= 24.9){
            BMIDisplayInformationTextView.setText(getString(R.string.profile_fragment_BMI_normal));
        } else if (bodyMassIndex >= 25 && bodyMassIndex <= 29.9){
            BMIDisplayInformationTextView.setText(getString(R.string.profile_fragment_BMI_overweight));
        } else if (bodyMassIndex > 29.9) {
            BMIDisplayInformationTextView.setText(getString(R.string.profile_fragment_BMI_obese));
        } else {
            BMIDisplayInformationTextView.setVisibility(View.GONE);
        }
        return String.valueOf(df.format(bodyMassIndex));
    }
}
