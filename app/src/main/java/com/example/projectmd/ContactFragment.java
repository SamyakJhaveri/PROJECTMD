package com.example.projectmd;


import android.content.Context;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmd.Utils.ContactPropertyListAdapter;
import com.example.projectmd.models.Contact;

import java.util.ArrayList;



public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";

    public interface OnEditContactListener{
        public void onEditcontactSelected(Contact contact);
    }

    OnEditContactListener mOnEditContactListener;


    //This will evade the nullpointer exception whena adding to a new bundle from StartHereActivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private TextView mOPDIRNumber;
    private TextView mOPDWardNumber;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        String customFont = "GoogleSans-Regular.ttf";
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), customFont);

        toolbar = (Toolbar) view.findViewById(R.id.contactToolbar);

        mContactName = (TextView) view.findViewById(R.id.contactName);
        mContactName.setTypeface(typeface);
        mOPDIRNumber = (TextView) view.findViewById(R.id.opdirnumber);
        mOPDIRNumber.setTypeface(typeface);
        mOPDWardNumber = (TextView) view.findViewById(R.id.opdwardnumber);
        mOPDWardNumber.setTypeface(typeface);

        mListView = (ListView) view.findViewById(R.id.lvContactProperties);
        Log.d(TAG, "onCreateView: Started.");
        mContact = getContactFromBundle();

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

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

        // navigate to the edit contact fragment to edit the contact selected
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked the Edit Icon.");
                mOnEditContactListener.onEditcontactSelected(mContact);
            }
        });

        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        mOPDIRNumber.setText(mContact.getOpdirnumber());
        mOPDWardNumber.setText(mContact.getOpdwardnumber());


        ArrayList<String> properties = new ArrayList<>();

        properties.add(mContact.getForm1());
        properties.add(mContact.getForm2());
        properties.add(mContact.getForm3());
        properties.add(mContact.getForm4());
        ContactPropertyListAdapter adapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
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

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Cursor cursor  = databaseHelper.getContactID(mContact);

        int contactID = -1;
        while(cursor.moveToNext()){
            contactID = cursor.getInt(0);
        }
        if(contactID > -1){ // If the contact doesn't still exists then anvigate back by popping the stack
            init();
        }else{
            this.getArguments().clear(); //optional clear arguments but not necessary
            getActivity().getSupportFragmentManager().popBackStack();
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnEditContactListener = (OnEditContactListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
}