package pe.edu.upc.castor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.castor.R;

public class RegisterFragment extends Fragment{

    private static final String INDICE_SECCION = "upc.edu.pe.castor.LoginFragmentTab.extra.INDICE_SECCION";
    private Button registerButton;

    public static RegisterFragment nuevaInstancia(int indiceSeccion) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        final List<String> list=new ArrayList<String>();
        list.add("DNI");
        list.add("CE");

        Spinner s = (Spinner) rootView.findViewById(R.id.documentTypeSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);

        registerButton = (Button)rootView.findViewById(R.id.RegisterButton);

        return rootView;
    }

}
