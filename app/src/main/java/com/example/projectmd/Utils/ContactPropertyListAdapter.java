package com.example.projectmd.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmd.MainActivity;
import com.example.projectmd.R;
import com.example.projectmd.StartHereActivity;
import com.pdftron.pdf.controls.DocumentActivity;

import java.io.File;
import java.util.List;

/**
 * Created by User on 6/12/2017.
 */




public class ContactPropertyListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "ContactPropertyListAdap";

    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;

    public ContactPropertyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> properties) {
        super(context, resource, properties);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        this.mProperties = properties;
    }

    //---------------------------Stuff to change--------------------------------------------
    private static class ViewHolder{
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;
    }
    //--------------------------------------------------------------------------------------

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
            holder.property = (TextView) convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = (ImageView) convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = (ImageView) convertView.findViewById(R.id.iconLeftCardView);
            //--------------------------------------------------------------------------------------

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //---------------------------Stuff to change--------------------------------------------
        final String property = getItem(position);
        holder.property.setText(property);

        //check if it's an email or a phone number
        //email
       if((property.length() != 0)){
            //Phone call
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_view", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Opening form");
                    Toast.makeText(getContext(), "Opening Form", Toast.LENGTH_SHORT).show();
               }
            });

            //setup the icon for sending text messages
            holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_add", null, mContext.getPackageName()));
            holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open our sample document in the 'res/raw' resource folder
                    //Add switch case to this to give working options of multiple forms once they are converted fillable
                    //PDFs and added to the app.
                    if (property.equalsIgnoreCase("Request Form for Lab Investigations(HISTOPATHOLOGY)")) {
                        Log.d(TAG, "onClick: Adding a new form");
                        Toast.makeText(getContext(), "Adding new form", Toast.LENGTH_SHORT).show();
                        DocumentActivity.openDocument(getContext(), R.raw.request_form_for_lab_investigations_histopathology);
                        //finish();
                    }
                    else{
                        Toast.makeText(getContext(), "Form not available yet :(", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        //--------------------------------------------------------------------------------------
        return convertView;
    }
}








