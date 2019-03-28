package com.example.projectmd;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmd.models.Contact;

public class AddContactFragment extends Fragment {

    private static final String TAG = "AddContactFragment";

    //private Contact mContact;
    private EditText mOPDIRNumber, mName, mOPDWardNumber;
    //private CircleImageView mContactImage;
    private Spinner mSelectForm1;
    private Spinner mSelectForm2;
    private Spinner mSelectForm3;
    private Spinner mSelectForm4;
    private Toolbar toolbar;
    private int mPreviousKeyStroke;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String customFont = "GoogleSans-Regular.ttf";
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), customFont);


        View view = inflater.inflate(R.layout.fragment_addcontact, container, false);
        mOPDIRNumber = (EditText) view.findViewById(R.id.etPatientOPDIRNumber);
        mName = (EditText) view.findViewById(R.id.etContactName);
        mOPDWardNumber = (EditText) view.findViewById(R.id.etPatientOPDWardNumber);
        mSelectForm1 = (Spinner) view.findViewById(R.id.selectForm1);
        mSelectForm2 = (Spinner) view.findViewById(R.id.selectForm2);
        mSelectForm3 = (Spinner) view.findViewById(R.id.selectForm3);
        mSelectForm4 = (Spinner) view.findViewById(R.id.selectForm4);

        mOPDIRNumber.setTypeface(typeface);
        mName.setTypeface(typeface);
        mOPDWardNumber.setTypeface(typeface);


        toolbar = (Toolbar) view.findViewById(R.id.editContactToolbar);
        Log.d(TAG, "onCreateView: Started.");


        //set the heading the for the toolbar
        TextView heading = (TextView) view.findViewById(R.id.textContactToolbar);
        heading.setTypeface(typeface);
        heading.setText(getString(R.string.add_patient));

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        //navigation for the backarrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                //remove previous fragment from the backstack (therefore navigating back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        // save the new contact
        ImageView ivCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Saving the contact.");
                //execute the save method for the database

            }
        });


        //set onClickListener to the 'Checkmark' Icon for saving the contact
        ImageView confirmNewContact = (ImageView)view.findViewById(R.id.ivCheckMark);
        confirmNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Attempting to save a new contact.");
                if(checkStringIfNull(mName.getText().toString()))
                {
                    Log.d(TAG, "onClick: Saving contact:" + mName.getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    Contact contact = new Contact(
                            mName.getText().toString(),
                            mOPDIRNumber.getText().toString(),
                            mSelectForm1.getSelectedItem().toString(),
                            mSelectForm2.getSelectedItem().toString(),
                            mSelectForm3.getSelectedItem().toString(),
                            mSelectForm4.getSelectedItem().toString(),
                            mOPDWardNumber.getText().toString());
                    if(databaseHelper.addContact(contact))
                    {
                        Toast.makeText(getActivity(), "Patient Information Saved", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Error Saving", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });
        //initOnTextChangeListener();

        return view;
    }

    private  boolean checkStringIfNull(String string)
    {
        if(string.equals(""))
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: Deleting Patient.");
        }
        return super.onOptionsItemSelected(item);
    }

}