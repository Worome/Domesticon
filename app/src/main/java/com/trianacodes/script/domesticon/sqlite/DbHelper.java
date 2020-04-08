package com.trianacodes.script.domesticon.sqlite;

/* Creo esta clase para añadir el código necesario para crear la base de datos si ésta aún no
   existiese o para actualizarla*/

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DbHelper extends SQLiteOpenHelper {

    /* Cada vez que haya una modificación en cualquiera de las tablas, hay que subir la versión en
    la constante DATABASE_VERSION para poder hacer las actualizaciones correspondientes. */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Domesticon.sqlite";
    /*Al crear la siguiente variable da un error porque hay que inicializarla en el Constructor
     del DbHelper.*/
    private final Context contexto;

    public DbHelper(Context contextoActual) {
        super(contextoActual, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto = contextoActual;
    }

    @Override
    public void onOpen(SQLiteDatabase db){

        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){

            db.setForeignKeyConstraintsEnabled(true);

        } else {

            db.execSQL("PRAGMA foreing_keys=ON");

        }

    }

    // En este método es en donde se crean las tablas.
    @Override
    public void onCreate(SQLiteDatabase db) {

        /* TODO Aquí se crean todas las tablas. Todas ellas menos la CabeceraHistorico y LineasHistorico
            se han creado usando la interfaz; las dos últimas se han creado usando la clase que
            implementa su interfaz. Si ambas funcionan, es mejor usar el método de las clases.
         */
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_DOS_CAMPOS, DatosTablas.ESTADOS,
                DatosTablas.ColumnasEstados.ID, DatosTablas.ColumnasEstados.DESCRIPCION));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_TRES_CAMPOS, DatosTablas.BANCOS,
                DatosTablas.ColumnasBancos.ID, DatosTablas.ColumnasBancos.DESCRIPCION,
                DatosTablas.ColumnasBancos.SALDO));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_CUATRO_CAMPOS,
                DatosTablas.GASTOS_FIJOS, DatosTablas.ColumnasGastosFijos.ID,
                DatosTablas.ColumnasGastosFijos.DESCRIPCION, DatosTablas.ColumnasGastosFijos.IMPORTE,
                DatosTablas.ColumnasGastosFijos.ESTADO));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_CINCO_CAMPOS,
                DatosTablas.GASTOS_PERIODICOS, DatosTablas.ColumnasGastosPeriodicos.ID,
                DatosTablas.ColumnasGastosPeriodicos.DESCRIPCION,
                DatosTablas.ColumnasGastosPeriodicos.IMPORTE,
                DatosTablas.ColumnasGastosPeriodicos.MESES, DatosTablas.ColumnasGastosPeriodicos.ESTADO));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_CUATRO_CAMPOS,
                DatosTablas.INGRESOS_FIJOS, DatosTablas.ColumnasIngresosFijos.ID,
                DatosTablas.ColumnasIngresosFijos.DESCRIPCION, DatosTablas.ColumnasIngresosFijos.IMPORTE,
                DatosTablas.ColumnasIngresosFijos.ESTADO));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_CINCO_CAMPOS,
                DatosTablas.INGRESOS_EXTRAS, DatosTablas.ColumnasIngresosExtras.ID,
                DatosTablas.ColumnasIngresosExtras.DESCRIPCION,
                DatosTablas.ColumnasIngresosExtras.IMPORTE,
                DatosTablas.ColumnasIngresosExtras.MESES, DatosTablas.ColumnasIngresosExtras.ESTADO));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_CABECERAS, DatosTablas.CABECERA_HISTORICO,
                DatosTablas.CabeceraHistorico.ID, DatosTablas.CabeceraHistorico.ANO,
                DatosTablas.CabeceraHistorico.MES, DatosTablas.CabeceraHistorico.SALDO_BANCOS,
                DatosTablas.CabeceraHistorico.TOTAL_INGRESOS, DatosTablas.CabeceraHistorico.TOTAL_GASTOS));
        db.execSQL(String.format(DatosTablas.PLANTILLA_TABLAS_LINEAS,DatosTablas.LINEAS_HISTORICO,
                DatosTablas.LineaHistorico.ID_CABECERA, DatosTablas.CABECERA_HISTORICO,
                DatosTablas.CabeceraHistorico.ID, DatosTablas.LineaHistorico.ID,
                DatosTablas.LineaHistorico.CONCEPTO, DatosTablas.LineaHistorico.IMPORTE,
                DatosTablas.LineaHistorico.ID_CABECERA, DatosTablas.LineaHistorico.ID));

    }

    /* Aquí se encuentran las instrucciones para actualizar la base de datos*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionActual, int versionNueva) {

        /* Cuando hay una nueva versión de la base de datos, siempre hay que preguntar si la versión
         actual es menor que la versión nueva, si es así, se ejecuta la sentencia de la
         actualización. Por cada nueva versión hay que preguntar con nuevos if y ejecutar la
         correspondiente actualización; los if anteriores deben permanecer (no se pueden borrar)
         tal y como se muestra en el siguiente ejemplo:
         */
        /* if (versionActual < 2){
            db.execSQL(DatosTabla.ACTUALIZA_BASEDATOS_VERSION_2);
           }
           if (versionActual < 3){
            db.execSQL(DatosTabla.ACTUALIZA_BASEDATOS_VERSION_3);
           }

         */

    }

}
