package com.trianacodes.script.domesticon.ui.gastos_periodicos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.trianacodes.script.domesticon.CuadrosDialogo;
import com.trianacodes.script.domesticon.Entidades.ClaseEstados;
import com.trianacodes.script.domesticon.Entidades.ClaseGastos;
import com.trianacodes.script.domesticon.R;
import com.trianacodes.script.domesticon.sqlite.DatosTablas;
import com.trianacodes.script.domesticon.sqlite.DbHelper;
import com.trianacodes.script.domesticon.sqlite.OperacionesBaseDatos;

import java.util.ArrayList;

public class gastos_periodicos extends Fragment {

    private TextView identificador;
    private EditText descripcion, importe, estado, meses_gasto;
    private Button guardar;
    // Para poder crear un objeto de una clase, hay que inicializarlo a la vez que se crea
    private ClaseGastos objetoGastos = new ClaseGastos();
    private OperacionesBaseDatos datos;
    //private String id;
    public  String descripcion_gasto;
    private Spinner desplegableEstados;

    ArrayList<String> estadosSpinner;
    ArrayList<ClaseEstados> estadosList;


    public gastos_periodicos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gastos_periodicos, container, false);

        //Creo una instancia
        datos = OperacionesBaseDatos.obtenerInstancia(getContext());

                /* Tengo que llamar a este método para poder trabajar con los componentes del fragment y
        hacer que los botones funcionen*/
        controlInterfaz(root);

        rellenaSpinner();
        controlDesplegable();
        ArrayAdapter<CharSequence> adaptadorSpinner = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, estadosSpinner);
        desplegableEstados.setAdapter(adaptadorSpinner);

        // Inflate the layout for this fragment
        return root;
    }

    private void rellenaSpinner(){

        // Creo un cursor para guardar en él los datos obtenidos de la consulra datos.obtenerEstados
        Cursor cursor = datos.obtenerEstados();

        ClaseEstados estadoUnico = null;
        estadosList = new ArrayList<ClaseEstados>();

        try{

            while (cursor.moveToNext()){

                estadoUnico = new ClaseEstados();
                estadoUnico.setDescripcion(cursor.getString(1));
                estadosList.add(estadoUnico);

            }


        } catch (Exception e){


            SharedPreferences preferencias =
                    getContext().getSharedPreferences("Dialogos", Context.MODE_PRIVATE);
            SharedPreferences.Editor datosEnviados = preferencias.edit();
            datosEnviados.putString("Titulo",getString(R.string.Errores));
            datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n" +
                    e.getMessage());
            datosEnviados.apply();
            //Creo un objeto de la clase en la que defino el cuadro de diálogo
            CuadrosDialogo dialogoPersonalizado = new CuadrosDialogo();
            dialogoPersonalizado.show(getFragmentManager(), "personalizado");
            // Creo un objeto de tipo Fragment para almacenar en él el cuadro de diálogo
            Fragment fragmento = getFragmentManager().findFragmentByTag("personalizado");

            // Borro el cuadro de diálogo si no se está mostrando
            if (fragmento != null){

                getFragmentManager().beginTransaction().remove(fragmento).commit();

            }


        }



        estadosSpinner = new ArrayList<String>();
        estadosSpinner.add("Selecciona estado");
        for (int i = 0; i < estadosList.size(); i++){

            estadosSpinner.add(estadosList.get(i).getDescripcion());

        }

    }

    private void controlInterfaz(View root){
//TODO: Voy modificando por aquí.
        desplegableEstados = root.findViewById(R.id.spEstado);
        identificador = root.findViewById(R.id.txtIdentificador);
        descripcion = root.findViewById(R.id.etDescripcion);
        importe = root.findViewById(R.id.etImporte);
        meses_gasto = root.findViewById(R.id.etMeses);
        desplegableEstados = root.findViewById(R.id.spEstado);
        guardar = root.findViewById(R.id.btnAnadir);
        objetoGastos.setDescripcion("");
        objetoGastos.setEstado("");
        objetoGastos.setId("");
        objetoGastos.setImporte(Double.valueOf(0));
        objetoGastos.setMeses("");

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //controlDesplegable();
                descripcion_gasto = descripcion.getText().toString();
                objetoGastos.setDescripcion(descripcion.getText().toString());
                objetoGastos.setImporte(Double.valueOf(importe.getText().toString()));
                objetoGastos.setMeses(meses_gasto.getText().toString());

                if (!descripcion.getText().toString().isEmpty()){

                    objetoGastos.setDescripcion(descripcion.getText().toString());
                    objetoGastos.setImporte(Double.valueOf(importe.getText().toString()));
                    objetoGastos.setMeses(meses_gasto.getText().toString());
                    controlDesplegable();
                    //Ejecuto un hilo en background que he definido más abajo.
                    try{

                        new  gastos_periodicos.TareasDatos().execute();
                        //identificador.setText(id);
                        descripcion.setText("");
                        importe.setText("");
                        meses_gasto.setText("");
                        // Inicializo al primer valor el desplegable
                        desplegableEstados.setSelection(0);
                        descripcion.requestFocus();

                    } catch (Exception e){

//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                         /* Guardo den el SharedPreferences los datos necesarios que hay que mostrar
                         en el cuadro de diálogo. Parece que como estoy dentro de un Fragment hay
                         que anteponer al getSharedpreferences un objeto de tipo Context (en este
                         caso lo he llamado getContext aunque también puede ser this.getActivity())*/
                        SharedPreferences preferencias =
                                getContext().getSharedPreferences("Dialogos", Context.MODE_PRIVATE);
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
                            "El campo Descripción no puede estar vacío");
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

                    descripcion.requestFocus();

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

                if(datos.obtenerUnGastoPeriodico(descripcion_gasto).getCount() > 0){


                    SharedPreferences preferencias =
                            getContext().getSharedPreferences("Dialogos",Context.MODE_PRIVATE);
                    SharedPreferences.Editor datosEnviados = preferencias.edit();
                    datosEnviados.putString("Titulo",getString(R.string.Errores));
                    datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n" +
                            "El gasto " + descripcion_gasto + " ya existe.");
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

                    datos.insertarGastosPeriodicos(objetoGastos);

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

    private void controlDesplegable(){

        try {

            desplegableEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    //Toast.makeText(getContext(), "****" + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                    if (adapterView.getItemAtPosition(i).toString().equals("Selecciona")){

                        objetoGastos.setEstado("No asignado");

                    } else{

                        objetoGastos.setEstado(adapterView.getItemAtPosition(i).toString());

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });

        } catch (Exception e) {

            /* Guardo den el SharedPreferences los datos necesarios que hay que mostrar en el
            cuadro de diálogo*/
            SharedPreferences preferencias = this.getActivity().getSharedPreferences("Dialogos",Context.MODE_PRIVATE);
            SharedPreferences.Editor datosEnviados = preferencias.edit();
            datosEnviados.putString("Titulo",getString(R.string.Errores));
            datosEnviados.putString("Mensaje", getString(R.string.mensaje_error) + " \n " +
                    e.getMessage());
            datosEnviados.apply();
            //Creo un objeto de la clase en la que defino el cuadro de diálogo
            CuadrosDialogo dialogoPersonalizado = new CuadrosDialogo();
            /*Muestro el cuadro de diálogo pasándo como parámetros el manejador de fragmentos y una
             etiqueta que se va a suar para locarlizar el cuadro de diálogo para hacer tareas con el
             cuadro de diálogo.*/
            dialogoPersonalizado.show(getFragmentManager(), "personalizado");
            // Creo un objeto de tipo Fragment para almacenar en él el cuadro de diálogo
            Fragment fragmento = getFragmentManager().findFragmentByTag("personalizado");

            // Borro el cuadro de diálogo si no se está mostrando
            if (fragmento != null){

                getFragmentManager().beginTransaction().remove(fragmento).commit();

            }

        }

    }

}
