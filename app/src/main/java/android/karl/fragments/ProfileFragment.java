package android.karl.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.karl.fyp.MainActivity;
import android.karl.fyp.MySQLiteHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.karl.fyp.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    // Spinners
    Spinner gender;

    // EditText
    EditText user_name;
    EditText user_height;
    EditText user_weight;

    MySQLiteHelper db;

    View rootView;

    String name_user;
    String gender_user;
    String height_user;
    String weight_user;

    Button save_changes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set the title
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile_fragment_title));

        db = new MySQLiteHelper((MainActivity) getActivity());

        getInformationFromDatabase();

        setUpViews();

        insertDataIntoViews();

        hideSaveButton();

        return rootView;
    }

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

    public void getInformationFromViews() {
        String user_gender = gender.getSelectedItem().toString();
        String name = user_name.getText().toString();
        String height = user_height.getText().toString();
        String weight = user_weight.getText().toString();

        db.updateUser(name, user_gender, weight, height);

        ((MainActivity) getActivity()).setNameHeader();

        hideSaveButton();

        Toast.makeText(this.getActivity(), getString(R.string.profile_fragment_saved), Toast.LENGTH_SHORT).show();

    }

    public void updateUser() {
        getInformationFromViews();
    }

    public void getInformationFromDatabase() {
        Cursor res = db.getUser();

        while(res.moveToNext()) {
            name_user = res.getString(1);
            gender_user = res.getString(2);
            height_user = res.getString(4);
            weight_user = res.getString(3);
        }
    }

    public void insertDataIntoViews() {
        user_name.setText(name_user);
        user_height.setText(height_user);
        user_weight.setText(weight_user);
        ArrayAdapter adapter = (ArrayAdapter) gender.getAdapter();
        gender.setSelection(adapter.getPosition(gender_user));
    }

    public void showSaveButton() {
        save_changes.setVisibility(View.VISIBLE);
    }

    public void hideSaveButton() {
        save_changes.setVisibility(View.GONE);
    }
}
