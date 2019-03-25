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
   //private String mSelectedImagePath;
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
        //mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mSelectForm1 = (Spinner) view.findViewById(R.id.selectForm1);
        mSelectForm2 = (Spinner) view.findViewById(R.id.selectForm2);
        mSelectForm3 = (Spinner) view.findViewById(R.id.selectForm3);
        mSelectForm4 = (Spinner) view.findViewById(R.id.selectForm4);

        mOPDIRNumber.setTypeface(typeface);
        mName.setTypeface(typeface);
        mOPDWardNumber.setTypeface(typeface);


        toolbar = (Toolbar) view.findViewById(R.id.editContactToolbar);
        Log.d(TAG, "onCreateView: Started.");

        //mSelectedImagePath = null;

        //load the default images by causing an error
        //UniversalImageLoader.setImage(null, mContactImage, null, "");

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

        // initiate the dialog box for choosing an image
        /*ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Make sure all permissions have been verified before opening the dialog

                for(int i = 0; i < Init.PERMISSIONS.length; i++){
                    String[] permission = {Init.PERMISSIONS[i]};
                    if(((StartHereActivity)getActivity()).checkPermission(permission)){
                        if(i == Init.PERMISSIONS.length - 1){
                            Log.d(TAG, "onClick: Opening the 'Image Selection Dialog Box'.");
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(AddContactFragment.this, 0);
                        }
                    }else{
                        ((StartHereActivity)getActivity()).verifyPermissions(permission);
                    }
                }


            }
        });*/

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

    /**
     * Initialize the onTextChangeListener for formatting the phonenumber
     */
    /*private void initOnTextChangeListener(){

        mPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                mPreviousKeyStroke = keyCode;

                return false;
            }
        });

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String number = s.toString();
                Log.d(TAG, "afterTextChanged:  " + number);

                if(number.length() == 3 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains("(")){
                    number = String.format("(%s", s.toString().substring(0,3));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());
                }
                else if(number.length() == 5 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains(")")){
                    number = String.format("(%s) %s",
                            s.toString().substring(1,4),
                            s.toString().substring(4,5));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());
                }
                else if(number.length() ==10 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains("-")){
                    number = String.format("(%s) %s-%s",
                            s.toString().substring(1,4),
                            s.toString().substring(6,9),
                            s.toString().substring(9,10));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());

                }
            }
        });
    }*/

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

    /**
     * Retrieves the selected image from the bundle (coming from ChangePhotoDialog)
     * @param bitmap
     */
   /* @Override
    public void getBitmapImage(Bitmap bitmap) {
        Log.d(TAG, "getBitmapImage: Got the Bitmap: " + bitmap);
        //get the bitmap from 'ChangePhotoDialog'
        if(bitmap != null) {
            //compress the image (if you like)
            ((StartHereActivity)getActivity()).compressBitmap(bitmap, 70);
            mContactImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void getImagePath(String imagePath) {
        Log.d(TAG, "getImagePath: Got the Image Path: " + imagePath);

        if( !imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, mContactImage, null, "");
        }
    }*/
}