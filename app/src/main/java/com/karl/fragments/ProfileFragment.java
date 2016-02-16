package com.karl.fragments;

import android.app.Fragment;
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

public class ProfileFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileFragment";

    // Spinners
    Spinner gender;

    // EditText
    EditText user_name;
    EditText user_height;
    EditText user_weight;

    MySQLiteHelper db;

    View rootView;

    Button save_changes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile_fragment_title));

        db = new MySQLiteHelper((MainActivity) getActivity());

        setUpViews();

        getInformationFromDatabase();

        System.out.println("BMI: " + calculateBMI());

        hideSaveButton();

        return rootView;
    }

    /**
     * Set up the views for the fragment.
     */
    public void setUpViews() {
        save_changes = (Button) rootView.findViewById(R.id.save_changes);
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        gender = (Spinner) rootView.findViewById(R.id.user_gender);

        setUpSpinners();

        user_name = (EditText) rootView.findViewById(R.id.user_name);
        user_name.addTextChangedListener(user_name_listener);

        user_height = (EditText) rootView.findViewById(R.id.user_height);
        user_height.addTextChangedListener(user_height_listener);

        user_weight = (EditText) rootView.findViewById(R.id.user_weight);
        user_weight.addTextChangedListener(user_weight_listener);

        hideSaveButton();
    }

    private final TextWatcher user_name_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            showSaveButton();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final TextWatcher user_height_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            showSaveButton();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final TextWatcher user_weight_listener = new TextWatcher() {
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
    public void setUpSpinners() {
        String[] genders = getResources().getStringArray(R.array.gender);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genders);
        gender.setAdapter(adapter);
        gender.setSelection(adapter.getPosition("Unspecified"));
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUser();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Get the information from the views.
     */
    public void getInformationFromViews() {
        db.updateUser(user_name.getText().toString(), gender.getSelectedItem().toString(), user_weight.getText().toString(), user_height.getText().toString());

        ((MainActivity) getActivity()).setNameHeader();

        hideSaveButton();

        Toast.makeText(this.getActivity(), getString(R.string.profile_fragment_saved), Toast.LENGTH_SHORT).show();

    }

    /**
     * This saves the changes to the user profile and updates the users BMI display.
     */
    public void updateUser() {
        getInformationFromViews();
        calculateBMI();
        ((MainActivity)getActivity()).setNameHeader();
    }

    /**
     * Get information from the database regarding the user.
     */
    public void getInformationFromDatabase() {
        Cursor res = db.getUser();

        System.out.println(res.getColumnCount());
        if(res.getCount() == 0){
            Log.d(TAG, "No users exist");
        } else {
            while (res.moveToNext()) {
                user_name.setText(res.getString(1));
                ArrayAdapter adapter = (ArrayAdapter) gender.getAdapter();
                gender.setSelection(adapter.getPosition(res.getString(2)));
                user_height.setText(res.getString(4));
                user_weight.setText(res.getString(3));
            }
        }
    }

    /**
     * Show ths save changes button.
     */
    public void showSaveButton() {
        save_changes.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the save changes button.
     */
    public void hideSaveButton() {
        save_changes.setVisibility(View.GONE);
    }

    /**
     * Calculate the users BMI and set the information for it.
     * @return users BMI.
     */
    public String calculateBMI() {
        Double bmi = Double.parseDouble(user_weight.getText().toString()) / (Double.parseDouble(user_height.getText().toString()) * Double.parseDouble(user_height.getText().toString()));

        DecimalFormat df = new DecimalFormat("#.00");

        TextView bmi_display = (TextView) rootView.findViewById(R.id.profile_bmi_display);
        bmi_display.setText(getString(R.string.profile_fragment_your_BMI_is, String.valueOf(df.format(bmi))));

        TextView bmi_display_information = (TextView) rootView.findViewById(R.id.profile_bmi_display_information);
        if(bmi <= 18.4){
            bmi_display_information.setText(getString(R.string.profile_fragment_BMI_underweight));
        } else if (bmi >= 18.5 && bmi <= 24.9){
            bmi_display_information.setText(getString(R.string.profile_fragment_BMI_normal));
        } else if (bmi >= 25 && bmi <= 29.9){
            bmi_display_information.setText(getString(R.string.profile_fragment_BMI_overweight));
        } else if (bmi > 29.9) {
            bmi_display_information.setText(getString(R.string.profile_fragment_BMI_obese));
        } else {
            bmi_display_information.setVisibility(View.GONE);
        }

        return String.valueOf(df.format(bmi));
    }
}
