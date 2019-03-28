package com.example.projectmd;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmd.Utils.ContactListAdapter;
import com.example.projectmd.models.Contact;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ViewContactsFragment extends Fragment {
    private View navHeader;

    private static final String TAG = "ViewContactsFragment";
    /*private String godimage = "activechristianity.org/wp-content/uploads/2016/07/546-why-God-has-to-be-a-jealous-God-ingress.jpg";
    private String visionimage = "i2-prod.irishmirror.ie/incoming/article12422645.ece/ALTERNATES/s615b/PROD-Paul-BettanyJPG.jpg";
    private String nickimage = "shortlist.imgix.net/app/uploads/2018/10/23142114/does-this-multi-layered-avengers-theory-make-the-most-sense-yet-3-256x256.jpeg?w=256&h=256&fit=max&auto=format%2Ccompress";
    private String captainimage = "lrmonline.com/file/2017/10/captain-marvel.jpg";
    private String thorimage = "pbs.twimg.com/profile_images/1067302507410526209/NjVeO8vL_400x400.jpg";
    private String thanosimage = "amp.businessinsider.com/images/5ae762b77708e968bd4a8ece-750-562.jpg";
    private String lokiimage = "cdn3.movieweb.com/i/article/3UJDoNVTPy5I9buocsj7u57HQpUmCM/798:50/Thor-3-Ragnarok-Tom-Hiddleston-Loki-Last-Marvel.jpg";

*/

    public interface OnContactSelectedListener {
        public void OnContactSelected(Contact con);
    }

    OnContactSelectedListener mContactListener;

    public interface OnAddContactListener{
        public void onAddContact();
    }
    OnAddContactListener mOnAddContact;


    //variables and widgets
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;

    Toolbar toolbar2;
    private AppBarLayout viewContactsBar, searchBar;
    private ContactListAdapter adapter;
    private ListView contactsList;
    private EditText mSearchContacts;
    private TextView tp;//titlepatient

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);

        toolbar2 = (Toolbar)view.findViewById(R.id.toolbar2);

        String customFont = "GoogleSans-Regular.ttf";
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), customFont);

        viewContactsBar = (AppBarLayout) view.findViewById(R.id.viewContactsToolbar);
        searchBar = (AppBarLayout) view.findViewById(R.id.searchToolbar);
        contactsList = (ListView) view.findViewById(R.id.contactsList);
        mSearchContacts = (EditText) view.findViewById(R.id.etSearchContacts);
        mSearchContacts.setTypeface(typeface);
        tp = (TextView)view.findViewById(R.id.titlepatient);
        tp.setTypeface(typeface);

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar2);
        setHasOptionsMenu(true);



        setAppBarState(STANDARD_APPBAR);

        setupContactsList();

        // navigate to add contacts fragment
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked FAB.");
                mOnAddContact.onAddContact();
            }
        });

        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked Search Icon.");
                toggleToolBarState();
            }
        });

        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked Back Arrow.");
                toggleToolBarState();
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mContactListener = (OnContactSelectedListener) getActivity();
            mOnAddContact = (OnAddContactListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

    //
    private void setupContactsList(){
        final ArrayList<Contact> contacts = new ArrayList<>();
        /*contacts.add(new Contact("God", "8828793369", "Mobile","samyakjhaveri2799@gmail.com", godimage));
        contacts.add(new Contact("Lalyo", "9924110334", "Mind Stone","Vision@smartyrob.ot", visionimage));//vision
        contacts.add(new Contact("Kaniyo", "9924110335", "Pager","taribhes@nelaij.au", nickimage));//nick fury
        contacts.add(new Contact("Space Girl", "334 4563", "Pager","captain@marvel.se", captainimage));//captain marvel
        contacts.add(new Contact("Hathodi Wala", "867 5436", "Mobile","asgard@kahero.ty", thorimage));//Thor
        contacts.add(new Contact("Chutki", "667 1245", "Smart Gauntlet","Thanos@LikesToSn.ap", thanosimage));//Thanos
        contacts.add(new Contact("Lo-Key", "234 9742", "Mobile","IAm@Mischief!.com", lokiimage));//Loki*/

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Cursor cursor = databaseHelper.getAllContacts();
        //iterate through all the rows contained in the database
        if(!cursor.moveToNext()){
            Toast.makeText(getActivity(), "There are no Patients to show", Toast.LENGTH_SHORT).show();
        }
        while(cursor.moveToNext()){
            contacts.add(new Contact(
                    cursor.getString(1),//name
                    cursor.getString(2),//opdirnumber
                    cursor.getString(3),//form1
                    cursor.getString(4),//form2
                    cursor.getString(5),//form3
                    cursor.getString(6),//form4
                    cursor.getString(7)//opdwardnumber
            ));
        }

        //Log.d(TAG, "setupContactsList: image url: " + contacts.get(0).getProfileImage());

        //sort the arraylist based on the contact name
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        adapter = new ContactListAdapter(getActivity(), R.layout.layout_contactlistitem, contacts, "https://");


        mSearchContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = mSearchContacts.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactsList.setAdapter(adapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: navigating to " + getString(R.string.contact_fragment));

                //pass the contact to the interface and send it to StartHereActivity
                mContactListener.OnContactSelected(contacts.get(position));
            }
        });


    }

    /**
     * Initiates the appbar state toggle
     */
    private void toggleToolBarState() {
        Log.d(TAG, "toggleToolBarState: toggling AppBarState.");
        if(mAppBarState == STANDARD_APPBAR){
            setAppBarState(SEARCH_APPBAR);
        }else{
            setAppBarState(STANDARD_APPBAR);
        }
    }

    /**
     * Sets the appbar state for either the search 'mode' or 'standard' mode
     * @param state
     */
    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: changing app bar state to: " + state);

        mAppBarState = state;

        if(mAppBarState == STANDARD_APPBAR){
            searchBar.setVisibility(View.GONE);
            viewContactsBar.setVisibility(View.VISIBLE);

            //hide the keyboard
            View view = getView();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try{
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }catch (NullPointerException e){
                Log.d(TAG, "setAppBarState: NullPointerException: " + e.getMessage());
            }
        }

        else if(mAppBarState == SEARCH_APPBAR){
            viewContactsBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);

            //open the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}

