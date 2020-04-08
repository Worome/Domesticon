package com.trianacodes.script.domesticon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/*
Creo este activity para personalizar un cuadro de diálogo; si no es necesario, en las últimas
líneas del código indico cómo se mostrarían mensajes en un cuadro de diálogo standard.
*/

public class CuadrosDialogo extends DialogFragment {

    /* La siguiente definición de la interfaz es por si se quiere pasar algún parámetro desde el
    fragment a la actividad desde la que se le llama. */
    public interface devuelveDato{

        void FinalizaCuadroDialogo(String texto);

    }

    // El constructor que creo es obligatorio para los DialogFragment
    public CuadrosDialogo() {

    }

    /*En los fragments siempre es necesario sobreescribir el método onCreateView*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        /* Obtengo los datos del SharedPreference para montar el texto del cuadro de diálogo*/
        SharedPreferences preferencias = getActivity().getSharedPreferences("Dialogos", Context.MODE_PRIVATE);
        // Creo una vista en la que voy a inflar el xml del cuadro personalizado
        View view = inflater.inflate(R.layout.activity_cuadros_dialogo, container);
        TextView mensaje = view.findViewById(R.id.txtMensaje);
        Button boton = view.findViewById(R.id.btnAceptar);
        getDialog().setTitle(preferencias.getString("Titulo","Aviso"));
        mensaje.setText(preferencias.getString("Mensaje", "Mensaje sin definir"));
        // Elimino el contenido del archivo SharedPreferences
        preferencias.edit().clear().commit();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cierraDialogo();

            }

        });

        return view;

    }


    /* Estas líneas son para definir un cuadro de diálogo stándard de Android sin personalizar:
    @Override

    public Dialog onCReateDialog(Bundle savedInstanceState){

        AlertDialog.Builder cuadro = new AlertDialog.Builder(getActivity());
        cuadro.setTitle("Aviso")
                .setMessage("Mensaje de aviso")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return cuadro.create();

    }*/

    public void cierraDialogo(){

        // Cierro el cuadro de diálogo
        this.dismiss();

    }


}

