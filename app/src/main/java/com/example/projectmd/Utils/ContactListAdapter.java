package com.example.projectmd.Utils;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectmd.R;
import com.example.projectmd.models.Contact;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater mInflater;
    private List<Contact> mContacts = null;
    private ArrayList<Contact> arrayList; //used for the search bar
    private int layoutResource;
    private Context mContext;
    private String mAppend;
    String customFont = "GoogleSans-Regular.ttf";
    String customFont2 = "GoogleSans-Bold.ttf";
    Typeface typeface, typeface1;
    TextView opdir12;//opdirnumber123
    TextView opdward12;//opdwardnumber123


    public ContactListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contact> contacts, String append) {
        super(context, resource, contacts);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mAppend = append;
        this.mContacts = contacts;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(mContacts);

        typeface = Typeface.createFromAsset(context.getAssets(), customFont);
        typeface1 = Typeface.createFromAsset(context.getAssets(), customFont2);

    }

    private static class ViewHolder{
        TextView name;
        TextView opdirnumber;
        TextView opdwarnumber;
        ProgressBar mProgressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        /*
         ************ ViewHolder Build Pattern Start ************
         */
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            //---------------------------Stuff to change--------------------------------------------
            holder.name = (TextView) convertView.findViewById(R.id.contactName);
            holder.opdirnumber = (TextView)convertView.findViewById(R.id.opdirnumber);
            holder.opdwarnumber = (TextView)convertView.findViewById(R.id.opdwardnumber);
            //--------------------------------------------------------------------------------------

            opdir12 = (TextView)convertView.findViewById(R.id.opdirnumber123);
            opdward12 = (TextView)convertView.findViewById(R.id.opdwardnumber123);
            holder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.contactProgressBar);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }


        //---------------------------Stuff to change--------------------------------------------
        String name_ = getItem(position).getName();
        String opdirnumber_ = getItem(position).getOpdirnumber();
        String opdwarnumber_ = getItem(position).getOpdwardnumber();
        holder.name.setText(name_);
        holder.opdirnumber.setText(opdirnumber_);
        holder.opdwarnumber.setText(opdwarnumber_);
        holder.name.setTypeface(typeface);
        holder.opdirnumber.setTypeface(typeface);
        holder.opdwarnumber.setTypeface(typeface);
        opdir12.setTypeface(typeface1);
        opdward12.setTypeface(typeface1);
        //--------------------------------------------------------------------------------------

        return convertView;
    }

    //Filter class
    public void filter(String characterText){
        characterText = characterText.toLowerCase(Locale.getDefault());
        mContacts.clear();
        if( characterText.length() == 0){
            mContacts.addAll(arrayList);
        }
        else{
            mContacts.clear();
            for(Contact contact: arrayList){
                if(contact.getName().toLowerCase(Locale.getDefault()).contains(characterText)){
                    mContacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}

