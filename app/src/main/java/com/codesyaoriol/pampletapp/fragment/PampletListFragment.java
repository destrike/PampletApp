package com.codesyaoriol.pampletapp.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



import com.codesyaoriol.pampletapp.Singleton.Singleton;
import com.codesyaoriol.pampletapp.activity.Pdfrun2;
import com.codesyaoriol.pampletapp.activity.Pdftorun;
import com.codesyaoriol.pampletapp.activity.Second;
import com.codesyaoriol.pampletapp.core.BaseActivity;
import com.codesyaoriol.pampletapp.R;


import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PampletListFragment extends Fragment {

    public static PampletListFragment INSTANCE = null;
    public Context context;
    public int isEdit = 0;
    private ListView mListView;
    private EditText mSearchEditText;
    private ImageView mSearchIcon;
    private ImageView mFilter;
    private ImageView mDefaultSearch;
    private TextView mTextTitle;
    private PampletAdapter pampletAdapter;
    private ArrayList<String> arrayToDelete = new ArrayList<>();
    private ArrayList<String> mArrayFileNames = new ArrayList<>();
    private String mOpenFile;



    public PampletListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pamplet_list, container, false);

        INSTANCE = this;


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        try {
            ((BaseActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle("Sample");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final TextView mTextEdit = (TextView) view.findViewById(R.id.txtedit);
        final TextView mTextCancel = (TextView) view.findViewById(R.id.txtcancel);
        final ImageView mTrashImage = (ImageView) view.findViewById(R.id.trashicon);

        mTrashImage.setVisibility(View.GONE);

        mTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextCancel.setVisibility(View.VISIBLE);
                mTextEdit.setVisibility(View.GONE);
                mTrashImage.setVisibility(View.VISIBLE);

                isEdit = 1;

                pampletAdapter = new PampletAdapter(getActivity(), R.layout.custom_row_pamplet, mArrayFileNames);
                pampletAdapter.notifyDataSetChanged();
                mListView.setAdapter(pampletAdapter);
            }
        });


        mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextEdit.setVisibility(View.VISIBLE);
                mTextCancel.setVisibility(View.GONE);
                mTrashImage.setVisibility(View.GONE);

                isEdit = 0;

                pampletAdapter = new PampletAdapter(getActivity(), R.layout.custom_row_pamplet, mArrayFileNames);
                pampletAdapter.notifyDataSetChanged();
                mListView.setAdapter(pampletAdapter);

            }
        });


        mListView = (ListView) view.findViewById(R.id.lists);

        if (mArrayFileNames.size() != 0) {
            mArrayFileNames.clear();
        }


        /* open folder from directory */
        openFolder();


        pampletAdapter = new PampletAdapter(getActivity(), R.layout.custom_row_pamplet, mArrayFileNames);
        pampletAdapter.notifyDataSetChanged();
        mListView.setAdapter(pampletAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (isEdit == 1) {
                    if (pampletAdapter != null) {
                        pampletAdapter.toggleChecked(i);
                    } else {
                        pampletAdapter = null;
                    }
                } else {
                    mOpenFile = pampletAdapter.getItem(i);
                    Singleton.setContact(pampletAdapter.getItem(i));
                    Intent intent = new Intent(getActivity(), Pdftorun.class);
                    startActivity(intent);

//                    viewFile();
                }

            }
        });

        mTrashImage.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                deleteFile();


            }
        });


        return view;
    }

    public void openFolder() {
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "IFIN-PDF");
        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();

                mArrayFileNames.add(name);

            }
        }
    }

    /* view pdf file */
    public void viewFile() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/IFIN-PDF/",
                mOpenFile);
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(getActivity(), Pdftorun.class);


        Log.i("path", String.valueOf(path));

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    /* delete pdf from directory */
    public void deleteFile() {

        if (pampletAdapter.getCheckedItems().size() != 0) {

            arrayToDelete = pampletAdapter.getCheckedItems();

            for (int i = 0; i < arrayToDelete.size(); i++) {
                mArrayFileNames.remove(arrayToDelete.get(i));

                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/IFIN-PDF/",
                        arrayToDelete.get(i));
                Uri path = Uri.fromFile(file);

                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("file Deleted :" + path.getPath());
                    } else {
                        System.out.println("file not Deleted :" + path.getPath());
                    }
                }

            }

            pampletAdapter = new PampletAdapter(getActivity(), R.layout.custom_row_pamplet, mArrayFileNames);
            pampletAdapter.notifyDataSetChanged();
            mListView.setAdapter(pampletAdapter);

        }

    }

    public class PampletAdapter extends ArrayAdapter<String> {

        ArrayList<String> mData = new ArrayList<>();
        boolean[] mItemChecked;
        Context mContext;
        int mResId;
        private LayoutInflater mInflater;


        public PampletAdapter(Context context, int resource, ArrayList<String> data) {
            super(context, resource, data);
            //mInflater = LayoutInflater.from(context);
            this.mContext = context;
            this.mResId = resource;
            this.mData = data;
            this.mItemChecked = new boolean[data.size()];

            //Inflate layout
            mInflater = ((Activity) mContext).getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mData.size();
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public void toggleChecked(int position) {
            if (mItemChecked[position]) {
                mItemChecked[position] = false;
            } else {
                mItemChecked[position] = true;
            }
            notifyDataSetChanged();
        }

        public void toggleAll(boolean selectAll) {
            for (int i = 0; i < mItemChecked.length; i++) {
                mItemChecked[i] = selectAll;
            }
            notifyDataSetChanged();
        }

        public ArrayList<String> getCheckedItems() {

            ArrayList<String> checkedItems = new ArrayList<>();

            for (int i = 0; i < mItemChecked.length; i++) {
                if (mItemChecked[i]) {
                    checkedItems.add(mArrayFileNames.get(i));
                }
            }
            return checkedItems;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.custom_row_pamplet, parent, false);

                holder.checkedTextView = (CheckedTextView) convertView.findViewById(R.id.custom_row_friends_selection_checkedtextview);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (mItemChecked.length > 0) {
                holder.checkedTextView.setChecked(mItemChecked[position]);
            }

            if (isEdit == 0) {
                holder.checkedTextView.setCheckMarkDrawable(null);
            }


            try {
                holder.checkedTextView.setText(mData.get(position));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }


        class HeaderViewHolder {
            TextView text;
        }

        class ViewHolder {
            CheckedTextView checkedTextView;
        }

    }


}
