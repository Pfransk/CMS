package com.immense.cms;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.util.Objects;

import static android.R.layout.simple_spinner_item;

public class AddContact extends AppCompatActivity {
    TextInputLayout til1, til2, til3, til4, til5, til6, til7, til8, til9, til10, til11;
    EditText companyname, fullname, designation, address, city, state, country, phonenumber, mobilenumber, emailaddress, website;
    Button addcontact;
    ArrayAdapter<CharSequence> arrayAdapter;
    MaterialBetterSpinner spinner;
    String salute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        til1 =  findViewById(R.id.til1);
        til2 =  findViewById(R.id.til2);
        til3 =  findViewById(R.id.til3);
        til4 =  findViewById(R.id.til4);
        til5 =  findViewById(R.id.til5);
        til6 =  findViewById(R.id.til6);
        til7 =  findViewById(R.id.til7);
        til8 =  findViewById(R.id.til8);
        til9 =  findViewById(R.id.til9);
        til10 =  findViewById(R.id.til10);
        til11 =  findViewById(R.id.til11);

        companyname =  findViewById(R.id.company_name);
        fullname =  findViewById(R.id.fullname);
        designation =  findViewById(R.id.designation);
        address =  findViewById(R.id.address);
        city = findViewById(R.id.city);
        state =  findViewById(R.id.state);
        country = findViewById(R.id.country);
        phonenumber =  findViewById(R.id.phone_number);
        mobilenumber =  findViewById(R.id.mobilenumber);
        emailaddress =  findViewById(R.id.emailaddress);
        website =  findViewById(R.id.website);
        addcontact =  findViewById(R.id.addcontact);

        companyname.addTextChangedListener(new MyTextWatcher(companyname));
        fullname.addTextChangedListener(new MyTextWatcher(fullname));
        designation.addTextChangedListener(new MyTextWatcher(designation));
        address.addTextChangedListener(new MyTextWatcher(address));
        city.addTextChangedListener(new MyTextWatcher(city));
        state.addTextChangedListener(new MyTextWatcher(state));
        country.addTextChangedListener(new MyTextWatcher(country));
        phonenumber.addTextChangedListener(new MyTextWatcher(phonenumber));
        mobilenumber.addTextChangedListener(new MyTextWatcher(mobilenumber));
        emailaddress.addTextChangedListener(new MyTextWatcher(emailaddress));
        website.addTextChangedListener(new MyTextWatcher(website));
        //spinner
        spinner =  findViewById(R.id.salutation);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.salutations, simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.addTextChangedListener(new MyTextWatcher(spinner));

      final Sqlitedatahandler sqlitedatahandler = new Sqlitedatahandler(getApplicationContext());

         addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean x = addToDb();
                if(x)
                {
                    sqlitedatahandler.addContactToDB(companyname.getText().toString(),spinner.getText().toString(),fullname.getText().toString(),
                    designation.getText().toString(),address.getText().toString(),city.getText().toString(),state.getText().toString(),
                            country.getText().toString(),phonenumber.getText().toString(),mobilenumber.getText().toString(),
                            emailaddress.getText().toString(),website.getText().toString());

                    Intent i = new Intent(getApplicationContext(),ListActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Contact not saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private boolean addToDb() {
        if (!validCompanyName() || !validFullName() || !validSalutation() ||!validDesignation() || !validAddress() || !validCity() ||
                !validState() || !validCountry() || !validPhoneNumber() || !validMobileNumber() ||
                !validEmailAddress() || !validWebsite()) {
             return false;
        }else{
            Toast.makeText(getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();

            return true;}
    }

    private boolean validSalutation() {
       if(spinner.getText().toString().equals(""))
       {   requestFocus(spinner);

           return false;
       }
         else {
           return true;
           }
    }

    private boolean validWebsite() {
        if (Objects.requireNonNull(til11.getEditText()).getText().toString().trim().isEmpty()) {
            til11.setError(getString(R.string.err_web));
            requestFocus(website);
            return false;
        } else {
            til11.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validEmailAddress() {
        String email = Objects.requireNonNull(til10.getEditText()).getText().toString().trim();
        if (!(!TextUtils.isEmpty(email) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            til10.setError(getString(R.string.err_email));
            requestFocus(emailaddress);
            return false;
        } else {
            til10.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validMobileNumber() {
        String phone = Objects.requireNonNull(til9.getEditText()).getText().toString().trim();
        if (!(!phone.isEmpty() && phone.matches("[6-9][0-9]{9}"))) {
            til9.setError(getString(R.string.err_mobile));
            requestFocus(mobilenumber);
            return false;
        } else {
            til9.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validPhoneNumber() {
        if (Objects.requireNonNull(til8.getEditText()).getText().toString().trim().isEmpty()) {
            til8.setError(getString(R.string.err_msg));
            requestFocus(phonenumber);
            return false;
        } else {
            til8.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validCountry() {
        if (Objects.requireNonNull(til7.getEditText()).getText().toString().trim().isEmpty()) {
            til7.setError(getString(R.string.err_msg));
            requestFocus(country);
            return false;
        } else {
            til7.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validCity() {
        if (Objects.requireNonNull(til5.getEditText()).getText().toString().trim().isEmpty()) {
            til5.setError(getString(R.string.err_msg));
            requestFocus(city);
            return false;
        } else {
            til5.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validAddress() {
        if (Objects.requireNonNull(til4.getEditText()).getText().toString().trim().isEmpty()) {
            til4.setError(getString(R.string.err_msg));
            requestFocus(address);
            return false;
        } else {
            til4.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validDesignation() {
        if (Objects.requireNonNull(til3.getEditText()).getText().toString().trim().isEmpty()) {
            til3.setError(getString(R.string.err_msg));
            requestFocus(designation);
            return false;
        } else {
            til3.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validState() {
        if (Objects.requireNonNull(til6.getEditText()).getText().toString().trim().isEmpty()) {
            til6.setError(getString(R.string.err_msg));
            requestFocus(state);
            return false;
        } else {
            til6.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validFullName() {
        if (Objects.requireNonNull(til2.getEditText()).getText().toString().trim().isEmpty()) {
            til2.setError(getString(R.string.err_msg));
            requestFocus(fullname);
            return false;
        } else {
            til2.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validCompanyName() {
        if (Objects.requireNonNull(til1.getEditText()).getText().toString().trim().isEmpty()) {
            til1.setError(getString(R.string.err_msg));
            requestFocus(companyname);
            return false;
        } else {
            til1.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private class MyTextWatcher implements TextWatcher {


        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.company_name:
                    validCompanyName();
                    break;
                case R.id.fullname:
                    validFullName();
                    break;
                case R.id.salutation:
                    validSalutation();
                    break;
                case R.id.designation:
                    validDesignation();
                    break;
                case R.id.address:
                    validAddress();
                    break;
                case R.id.city:
                    validCity();
                    break;
                case R.id.state:
                    validState();
                    break;
                case R.id.country:
                    validCountry();
                    break;
                case R.id.phone_number:
                    validPhoneNumber();
                    break;
                case R.id.mobilenumber:
                    validMobileNumber();
                    break;
                case R.id.emailaddress:
                    validEmailAddress();
                    break;
                case R.id.website:
                    validWebsite();
                    break;

            }
        }

    }
}

