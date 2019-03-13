package com.example.projectmd;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmd.models.Contact;

/**
 * Created by User on 6/12/2017.
 */


public class EditContactFragment extends Fragment {
    private static final String TAG = "EditContactFragment";

    //This will evade the nullpointer exception when a adding to a new bundle from StartHereActivity
    public EditContactFragment(){
        super();
        setArguments(new Bundle());
    }
    public void onEditcontactSelected(Contact contact) {
        Log.d(TAG, "OnContactSelected: contact selected from "
                + getString(R.string.edit_contact_fragment)
                + " " + contact.getName());

        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.patient), contact);
        fragment.setArguments(args);
    }

    private Contact mContact;
    private EditText mOPDIRNumber, mName, mOPDWardNumber;
   // private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
   // private String mSelectedImagePath;
    private int mPreviousKeyStroke;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcontact, container, false);
        mOPDIRNumber = (EditText) view.findViewById(R.id.etPatientOPDIRNumber);
        mName = (EditText) view.findViewById(R.id.etContactName);
        mOPDWardNumber = (EditText) view.findViewById(R.id.etPatientOPDWardNumber);
        ///mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mSelectDevice = (Spinner) view.findViewById(R.id.selectDevice);
        toolbar = (Toolbar) view.findViewById(R.id.editContactToolbar);
        Log.d(TAG, "onCreateView: Started.");

        //mSelectedImagePath = null;

        //set the heading the for the toolbar
        TextView heading = (TextView) view.findViewById(R.id.textContactToolbar);
        heading.setText(getString(R.string.edit_patient));

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        //get the contact from the bundle
        mContact = getContactFromBundle();

        if(mContact  != null){
            init();
        }

        //navigation for the backarrow
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked Back Arrow.");
                //remove previous fragment from the backstack (therefore navigating back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // save changes to the contact
        ImageView ivCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Saving the Edited Patient.");
                //execute the save method for the database

                if(checkStringIfNull(mName.getText().toString())){
                    Log.d(TAG, "onClick: Saving changes to the patient: " + mName.getText().toString());

                    //get the database helper and save the contact
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    Cursor cursor = databaseHelper.getContactID(mContact);

                    int contactID = -1;
                    while(cursor.moveToNext()){
                        contactID = cursor.getInt(0);
                    }
                    if(contactID > -1){
                        /*if(mSelectedImagePath != null){
                            mContact.setProfileImage(mSelectedImagePath);
                        }*/
                        mContact.setName(mName.getText().toString());
                        mContact.setOpdirnumber(mOPDIRNumber.getText().toString());
                        mContact.setDevice(mSelectDevice.getSelectedItem().toString());
                        mContact.setOpdwardnumber(mOPDWardNumber.getText().toString());

                        databaseHelper.updateContact(mContact, contactID);
                        Toast.makeText(getActivity(), "Pateint Information Updated", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // initiate the dialog box for choosing an image
        /*ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Make sure all permissions have been verified before opening the dialog

                for( int i = 0; i < Init.PERMISSIONS.length; i++){
                    String[] permission = {Init.PERMISSIONS[i]};
                    if(((StartHereActivity)getActivity()).checkPermission(permission)){
                        if(i == Init.PERMISSIONS.length - 1){
                            Log.d(TAG, "onClick: opening the 'image selection dialog box'.");
                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(EditContactFragment.this, 0);
                        }
                    }else{
                        ((StartHereActivity)getActivity()).verifyPermissions(permission);
                    }
                }


            }
        });*/

        return view;
    }

    private boolean checkStringIfNull(String string){
        if(string.equals("")){
            return false;
        }else{
            return true;
        }
    }

    private void init(){
        mOPDIRNumber.setText(mContact.getOpdirnumber());
        mName.setText(mContact.getName());
        mOPDWardNumber.setText(mContact.getOpdwardnumber());
        //UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "");

        //Setting the selected device to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.device_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectDevice.setAdapter(adapter);
        int position = adapter.getPosition(mContact.getDevice());
        mSelectDevice.setSelection(position);
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
                Log.d(TAG, "onOptionsItemSelected: Deleting Patient Information");
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                Cursor cursor = databaseHelper.getContactID(mContact);

                int contactID = -1;
                while(cursor.moveToNext()){
                    contactID = cursor.getInt(0);
                }
                if(contactID > -1){
                    if(databaseHelper.deleteContact(contactID) > 0){
                        Toast.makeText(getActivity(), "Patient Information Deleted", Toast.LENGTH_SHORT).show();

                        //clear the arguments ont he current bundle since the contact is deleted
                        this.getArguments().clear();

                        //remove previous fragemnt from the backstack (therefore navigating back)
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else{
                        Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieves the selected contact from the bundle (coming from StartHereActivity)
     * @return
     */
    private Contact getContactFromBundle(){
        Log.d(TAG, "getContactFromBundle: Arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.patient));
        }else{
            return null;
        }
    }

    /**
     * Retrieves the selected image from the bundle (coming from ChangePhotoDialog)
     * @param bitmap
     */
    //@Override
    /*public void getBitmapImage(Bitmap bitmap) {
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
        Log.d(TAG, "getImagePath: got the image path: " + imagePath);

        if( !imagePath.equals("")){
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, mContactImage, null, "");
        }
    }*/

    /**
     * Initialize the onTextChangeListener for formatting the phonenumber
     */
    private void initOnTextChangeListener(){

        mOPDIRNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                mPreviousKeyStroke = keyCode;

                return false;
            }
        });

        mOPDIRNumber.addTextChangedListener(new TextWatcher() {
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
                    mOPDIRNumber.setText(number);
                    mOPDIRNumber.setSelection(number.length());
                }
                else if(number.length() == 5 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains(")")){
                    number = String.format("(%s) %s",
                            s.toString().substring(1,4),
                            s.toString().substring(4,5));
                    mOPDIRNumber.setText(number);
                    mOPDIRNumber.setSelection(number.length());
                }
                else if(number.length() ==10 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains("-")){
                    number = String.format("(%s) %s-%s",
                            s.toString().substring(1,4),
                            s.toString().substring(6,9),
                            s.toString().substring(9,10));
                    mOPDIRNumber.setText(number);
                    mOPDIRNumber.setSelection(number.length());

                }
            }
        });
    }
}
