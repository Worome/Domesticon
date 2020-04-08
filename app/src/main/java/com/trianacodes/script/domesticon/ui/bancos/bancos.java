package com.trianacodes.script.domesticon.ui.bancos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.trianacodes.script.domesticon.CuadrosDialogo;
import com.trianacodes.script.domesticon.Entidades.ClaseBancos;
import com.trianacodes.script.domesticon.R;
import com.trianacodes.script.domesticon.sqlite.DbHelper;
import com.trianacodes.script.domesticon.sqlite.OperacionesBaseDatos;

public class bancos extends Fragment {

    /* TODO: Controlar cuál es el ID de banco que se guarda.*/

    private TextView identificador;
    private EditText descripcion, saldo;
    private Button guardar;
    // Para poder crear un objeto de una clase, hay que inicializarlo a la vez que se crea
    private ClaseBancos objetoBanco = new ClaseBancos();
    private OperacionesBaseDatos datos;
    //private String id;
    public  String descripcion_bancos;

    public bancos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bancos, container, false);
        //Creo una instancia
        datos = OperacionesBaseDatos.obtenerInstancia(getContext());
        /* Tengo que llamar a este método para poder trabajar con los componentes del fragment y
        hacer que los botones funcionen*/
        controlInterfaz(root);

        // Inflate the layout for this fragment
        return root;
    }

    private void controlInterfaz(View root){

        identificador = root.findViewById(R.id.txtIdBancos);
        descripcion = root.findViewById(R.id.etNombreBanco);
        saldo = root.findViewById(R.id.etSaldoBanco);
        guardar = root.findViewById(R.id.btnAnadir);
        objetoBanco.setDescripcion("");
        objetoBanco.setSaldo(0.0);
        objetoBanco.setIdentificador("");

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                descripcion_bancos = descripcion.getText().toString();
                objetoBanco.setDescripcion(descripcion.getText().toString());
                if (!saldo.getText().toString().isEmpty()){

                    objetoBanco.setSaldo(Double.parseDouble(saldo.getText().toString()));

                    //Ejecuto un hilo en background que he definido más abajo.
                    try{

                        new TareasDatos().execute();
                        //identificador.setText(id);
                        descripcion.setText("");
                        saldo.setText("");
                        descripcion.requestFocus();
                    /*    objetoBanco.setDescripcion("");
                        objetoBanco.setSaldo(0.0);
                        objetoBanco.setIdentificador("");*/

                    } catch (Exception e){

//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                         /* Guardo den el SharedPreferences los datos necesarios que hay que mostrar
                         en el cuadro de diálogo. Parece que como estoy dentro de un Fragment hay
                         que anteponer al getSharedpreferences un objeto de tipo Context (en este
                         caso lo he llamado getContext aunque también puede ser this.getActivity())*/
                        SharedPreferences preferencias =
                                getContext().getSharedPreferences("Dialogos",Context.MODE_PRIVATE);
                        SharedPreferences.Editor datosEnviados = preferencias.edit();
                        datosEnviados.putString("Titulo",getString(R.string.Errores));
                        datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n" +
                                e.getMessage());
                        datosEnviados.apply();
                        //Creo un objeto de la clase en la que defino el cuadro de diálogo
                        CuadrosDialogo dialogoPersonalizado = new CuadrosDialogo();
                        /*Muestro el cuadro de diálogo pasándo como parámetros el manejador de
                        fragmentos y una etiqueta que se va a usar para localizar el cuadro de
                        diálogo para hacer tareas con el cuadro de diálogo. He tenido que sustituir
                        el getSupportFragmentManager por getFragmentManager ya que estoy llamando a
                        un Fragment desde otro Fragment.*/
                        dialogoPersonalizado.show(getFragmentManager(), "personalizado");
                        // Creo un objeto de tipo Fragment para almacenar en él el cuadro de diálogo
                        Fragment fragmento = getFragmentManager().findFragmentByTag("personalizado");

                        // Borro el cuadro de diálogo si no se está mostrando
                        if (fragmento != null){

                            getFragmentManager().beginTransaction().remove(fragmento).commit();

                        }
                    }

                } else {

                  /*  Toast.makeText(getContext(),"El campo Saldo no puede estar vacío",
                            Toast.LENGTH_LONG).show();*/

                    /* Guardo den el SharedPreferences los datos necesarios que hay que mostrar
                         en el cuadro de diálogo. Parece que como estoy dentro de un Fragment hay
                         que anteponer al getSharedpreferences un objeto de tipo Context (en este
                         caso lo he llamado getContext aunque también puede ser this.getActivity())*/
                    SharedPreferences preferencias =
                            getContext().getSharedPreferences("Dialogos",Context.MODE_PRIVATE);
                    SharedPreferences.Editor datosEnviados = preferencias.edit();
                    datosEnviados.putString("Titulo",getString(R.string.Errores));
                    datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n" +
                            "El campo Saldo no puede estar vacío");
                    datosEnviados.apply();
                    //Creo un objeto de la clase en la que defino el cuadro de diálogo
                    CuadrosDialogo dialogoPersonalizado = new CuadrosDialogo();
                        /*Muestro el cuadro de diálogo pasándo como parámetros el manejador de
                        fragmentos y una etiqueta que se va a usar para localizar el cuadro de
                        diálogo para hacer tareas con el cuadro de diálogo. He tenido que sustituir
                        el getSupportFragmentManager por getFragmentManager ya que estoy llamando a
                        un Fragment desde otro Fragment.*/
                    dialogoPersonalizado.show(getFragmentManager(), "personalizado");
                    // Creo un objeto de tipo Fragment para almacenar en él el cuadro de diálogo
                    Fragment fragmento = getFragmentManager().findFragmentByTag("personalizado");

                    // Borro el cuadro de diálogo si no se está mostrando
                    if (fragmento != null){

                        getFragmentManager().beginTransaction().remove(fragmento).commit();

                    }

                    saldo.requestFocus();

                }

            }

        });

    }

    /*Al crear este método va a dar un error; hay que pinchar en la bombilla roja de la izquierda y
    elegir que se implemente el doInBackground.*/
    public class TareasDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try{

                datos.getDb().beginTransaction();
                //id = datos.insertarBanco(objetoBanco);

                if(datos.obtenerUnBanco(descripcion_bancos).getCount() > 0){


                    SharedPreferences preferencias =
                            getContext().getSharedPreferences("Dialogos",Context.MODE_PRIVATE);
                    SharedPreferences.Editor datosEnviados = preferencias.edit();
                    datosEnviados.putString("Titulo",getString(R.string.Errores));
                    datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n" +
                            "El banco " + descripcion_bancos + " ya existe.");
                    datosEnviados.apply();
                    //Creo un objeto de la clase en la que defino el cuadro de diálogo
                    CuadrosDialogo dialogoPersonalizado = new CuadrosDialogo();
                        /*Muestro el cuadro de diálogo pasándo como parámetros el manejador de
                        fragmentos y una etiqueta que se va a usar para localizar el cuadro de
                        diálogo para hacer tareas con el cuadro de diálogo. He tenido que sustituir
                        el getSupportFragmentManager por getFragmentManager ya que estoy llamando a
                        un Fragment desde otro Fragment.*/
                    dialogoPersonalizado.show(getFragmentManager(), "personalizado");
                    // Creo un objeto de tipo Fragment para almacenar en él el cuadro de diálogo
                    Fragment fragmento = getFragmentManager().findFragmentByTag("personalizado");

                    // Borro el cuadro de diálogo si no se está mostrando
                    if (fragmento != null){

                        getFragmentManager().beginTransaction().remove(fragmento).commit();

                    }

                } else {

                    datos.insertarBanco(objetoBanco);

                }

                /*Aquí irán llamadas a otros métodos de operaciones con Base de Datos por ejemplo:
                Actualizaciones o eliminación.*/
                datos.getDb().setTransactionSuccessful();

            } finally {

                datos.getDb().endTransaction();

            }

            return null;

        }

    }

}
