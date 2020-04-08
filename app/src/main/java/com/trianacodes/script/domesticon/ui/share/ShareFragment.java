package com.trianacodes.script.domesticon.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.trianacodes.script.domesticon.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private EditText aaa;
    private String bbb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        /*final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        aaa = root.findViewById(R.id.etDescripcion);
        //aaa.setText("Pruebas");
        bbb = aaa.getText().toString();
        Toast.makeText(getContext(),bbb,Toast.LENGTH_LONG).show();
        return root;
    }
}