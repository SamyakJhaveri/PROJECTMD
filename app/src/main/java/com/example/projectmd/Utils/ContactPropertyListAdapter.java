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
    private static class ViewHolder {
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

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();


            //---------------------------Stuff to change--------------------------------------------
            holder.property = (TextView) convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = (ImageView) convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = (ImageView) convertView.findViewById(R.id.iconLeftCardView);
            //--------------------------------------------------------------------------------------

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //---------------------------Stuff to change--------------------------------------------
        final String property = getItem(position);
        holder.property.setText(property);


        if ((property.length() != 0)) {
            //Phone call
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_view", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: Opening form");
                    Toast.makeText(getContext(), "Opening Form", Toast.LENGTH_SHORT).show();
                }
            });

            holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_add", null, mContext.getPackageName()));
            holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open our sample document in the 'res/raw' resource folder
                    //Add switch case to this to give working options of multiple forms once they are converted fillable
                    //PDFs and added to the app.
                    switch (property) {
                        case "-None-":
                            Log.d(TAG, "onClick: Form Not Selected");
                            Toast.makeText(getContext(), "Form not Selected", Toast.LENGTH_SHORT).show();
                            break;

                        case "ABO Grouping/Crossmatching Request Form(Blood Bank)":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding ABO Grouping/Crossmatching Request Form(Blood Bank)", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.request_form_for_abo_grouping_crossmatching_blood_bank);
                            //finish();
                            break;
                        case "Clinical/Hemato/Gynac Lab Investigation Request Form":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding Clinical/Hemato/Gynac Lab Investigation Request Formk)", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.request_form_for_lab_investigations_clinical_hematology_gynac_lab);
                            //finish();
                            break;
                        case "Endocrine Test Request Form":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding Endocrine Test Request Form", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.request_for_endocrine_test);
                            //finish();
                            break;
                        case "FNAC Cytology Report":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding FNAC Cytology Report", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.report_of_fine_needle_aspiration_cytology);
                            //finish();
                            break;
                        case "FNAC History Data Sheet":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding FNAC History Data Sheet", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.fnac_history_data_sheet);
                            //finish();
                            break;
                        case "Histopathology Form Request Form":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding Histopathology Form Request Form", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.request_form_for_histopathology_test);
                            //finish();
                            break;
                        case "Histopathology Lab Investigation Request Form":
                            Log.d(TAG, "onClick: Adding a new form");
                            Toast.makeText(getContext(), "Adding Histopathology Lab Investigation Request Form", Toast.LENGTH_SHORT).show();
                            DocumentActivity.openDocument(getContext(), R.raw.request_form_for_lab_investigations_histopathology);
                            //finish();
                            break;
                        default:
                            Toast.makeText(getContext(), "Invalid Entry.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
        //--------------------------------------------------------------------------------------
        return convertView;
    }
}