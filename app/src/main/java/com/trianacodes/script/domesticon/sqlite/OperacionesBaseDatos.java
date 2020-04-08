package com.trianacodes.script.domesticon.sqlite;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.trianacodes.script.domesticon.Entidades.ClaseBancos;
import com.trianacodes.script.domesticon.Entidades.ClaseEstados;
import com.trianacodes.script.domesticon.Entidades.ClaseGastos;
import com.trianacodes.script.domesticon.Entidades.ClaseIngresos;



/* Creo un algoritmo con el patrón Singleton. Esto significa que creo un constructor principal como
 *  privado, defino un método estático de la clase y un método estático que permita la obtención del
 *  único miembro. */

public final class OperacionesBaseDatos {

    private OperacionesBaseDatos() {
    }

    private static DbHelper baseDatos;
    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    /* Esto es un ejemplo más complicado que el de la línea de arriba en la que se indican varias
    tablas unidas por JOINs */
    /*private static final String CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO = "cabecera_pedido " +
            "INNER JOIN cliente " +
            "ON cabecera_pedido.id_cliente = cliente.id " +
            "INNER JOIN forma_pago " +
            "ON cabecera_pedido.id_forma_pago = forma_pago.id";*/

    /* Este variable de tipo array se usa dentro de la instrucción builder.query, que es el que
       obliga a que dicha variable sea un array. En ella se establece el contenido del SELECT de
       la consulta, es decir los campos que se quieren mostrar en una consulta.*/
    private final String[] consultaBancos = new String[]{
            DatosTablas.BANCOS + "." + DatosTablas.ColumnasBancos.ID,
            DatosTablas.ColumnasBancos.DESCRIPCION, DatosTablas.ColumnasBancos.SALDO};
    private final String[] consultaEstados = new String[]{
            DatosTablas.ESTADOS + "." + DatosTablas.ColumnasEstados.ID,
            DatosTablas.ColumnasEstados.DESCRIPCION};
    private final String[] consultaGastosFijos = new String[]{
            DatosTablas.GASTOS_FIJOS + "." + DatosTablas.ColumnasGastosFijos.ID,
            DatosTablas.ColumnasGastosFijos.DESCRIPCION, DatosTablas.ColumnasGastosFijos.ESTADO,
            DatosTablas.ColumnasGastosFijos.IMPORTE};
    private final String[] consultaGastosPeriodicos = new String[]{
            DatosTablas.GASTOS_PERIODICOS + "." + DatosTablas.ColumnasGastosPeriodicos.ID,
            DatosTablas.ColumnasGastosPeriodicos.DESCRIPCION,
            DatosTablas.ColumnasGastosPeriodicos.ESTADO,
            DatosTablas.ColumnasGastosPeriodicos.IMPORTE,
            DatosTablas.ColumnasGastosPeriodicos.MESES};
    private final String[] consultaIngresosFijos = new String[]{
            DatosTablas.INGRESOS_FIJOS + "." + DatosTablas.ColumnasIngresosFijos.ID,
            DatosTablas.ColumnasIngresosFijos.DESCRIPCION,
            DatosTablas.ColumnasIngresosFijos.ESTADO,
            DatosTablas.ColumnasIngresosFijos.IMPORTE};
    private final String[] consultaIngresosExtras = new String[]{
            DatosTablas.INGRESOS_EXTRAS + "." + DatosTablas.ColumnasIngresosExtras.ID,
            DatosTablas.ColumnasIngresosExtras.DESCRIPCION,
            DatosTablas.ColumnasIngresosExtras.ESTADO,
            DatosTablas.ColumnasIngresosExtras.IMPORTE,
            DatosTablas.ColumnasIngresosExtras.MESES};


    public static OperacionesBaseDatos obtenerInstancia(Context contexto){
        if(baseDatos == null){

            baseDatos = new DbHelper(contexto);

        }

        return instancia;
    }

    // Este método obtiene todas los registros de la tablas
    public Cursor obtenerBancos(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.BANCOS);
        return builder.query(db, consultaBancos, null, null, null,
                null, null);
    }

    public Cursor obtenerEstados(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.ESTADOS);
        return builder.query(db, consultaEstados, null, null, null,
                null, null);
    }

    public Cursor obtenerGastosFijos(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.GASTOS_FIJOS);
        return builder.query(db, consultaGastosFijos, null, null, null,
                null, null);
    }

    public Cursor obtenerGastosPeriodicos(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.GASTOS_PERIODICOS);
        return builder.query(db, consultaGastosPeriodicos, null, null, null,
                null, null);
    }

    public Cursor obtenerIngresosFijos(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.INGRESOS_FIJOS);
        return builder.query(db, consultaIngresosFijos, null, null, null,
                null, null);
    }

    public Cursor obtenerIngresosExtras(){
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        /* Se usa la clase SQLiteQueryBuilder para hacer consultas complejas que puedan implicar
           joins. Si la consulta afecta a una sola tabla se puede usar el método rawQuery().*/
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // El método setTables establece sobre qué tabla se va a realizar la consulta.
        builder.setTables(DatosTablas.INGRESOS_EXTRAS);
        return builder.query(db, consultaIngresosExtras, null, null, null,
                null, null);
    }


    /* Si se quiere obtener sólo un registro dependiendo del valor de alguno de sus campo
       (SELECT... WHERE) se haría de la siguiente forma: */
    public Cursor obtenerUnBanco(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasBancos.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.BANCOS);
        return builder.query(db, consultaBancos, where, argWhere, null, null,
                null);

    }

    public Cursor obtenerUnEstado(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasEstados.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.ESTADOS);
        return builder.query(db, consultaEstados, where, argWhere, null, null,
                null);

    }

    public Cursor obtenerUnGastoFijo(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasGastosFijos.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.GASTOS_FIJOS);
        return builder.query(db, consultaGastosFijos, where, argWhere, null, null,
                null);

    }

    public Cursor obtenerUnGastoPeriodico(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasGastosPeriodicos.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.GASTOS_PERIODICOS);
        return builder.query(db, consultaGastosPeriodicos, where, argWhere, null,
                null, null);

    }

    public Cursor obtenerUnIngresoFijo(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasIngresosFijos.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.INGRESOS_FIJOS);
        return builder.query(db, consultaIngresosFijos, where, argWhere, null, null,
                null);

    }

    public Cursor obtenerUnIngresoExtra(String id){

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String where = String.format("%s=?", DatosTablas.ColumnasIngresosExtras.DESCRIPCION);
        String[] argWhere = {id};
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(DatosTablas.INGRESOS_EXTRAS);
        return builder.query(db, consultaIngresosExtras, where, argWhere, null,
                null, null);

    }


    /* Para insertar registros en una tabla se haría como viene abajo. El parámetro que recibe el
       método insertarBanco es un objeto (Banco) de la clase Bancos*/

    public String insertarBanco(ClaseBancos banco){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idBanco = DatosTablas.Bancos.generarIdBancos();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasBancos.ID, idBanco);
        valores.put(DatosTablas.ColumnasBancos.DESCRIPCION, banco.getDescripcion());
        valores.put(DatosTablas.ColumnasBancos.SALDO, banco.getSaldo());
        db.insertOrThrow(DatosTablas.BANCOS, null, valores);
        return idBanco;

    }

    public String insertarEstados(ClaseEstados estado){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idEstado = DatosTablas.Estados.generarIdEstados();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasEstados.ID, idEstado);
        valores.put(DatosTablas.ColumnasEstados.DESCRIPCION, estado.getDescripcion());
        db.insertOrThrow(DatosTablas.ESTADOS, null, valores);
        return idEstado;

    }

    public String insertarGastosFijos(ClaseGastos gasto){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idGastos = DatosTablas.GastosFijos.generarIdGastosFijos();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasGastosFijos.ID, idGastos);
        valores.put(DatosTablas.ColumnasGastosFijos.DESCRIPCION, gasto.getDescripcion());
        valores.put(DatosTablas.ColumnasGastosFijos.IMPORTE, gasto.getImporte());
        valores.put(DatosTablas.ColumnasGastosFijos.ESTADO, gasto.getEstado());
        db.insertOrThrow(DatosTablas.GASTOS_FIJOS, null, valores);
        return idGastos;

    }

    public String insertarGastosPeriodicos(ClaseGastos gasto){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idGastos = DatosTablas.GastosPeriodicos.generarIdGastosPeriodicos();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasGastosPeriodicos.ID, idGastos);
        valores.put(DatosTablas.ColumnasGastosPeriodicos.DESCRIPCION, gasto.getDescripcion());
        valores.put(DatosTablas.ColumnasGastosPeriodicos.IMPORTE, gasto.getImporte());
        valores.put(DatosTablas.ColumnasGastosPeriodicos.ESTADO, gasto.getEstado());
        valores.put(DatosTablas.ColumnasGastosPeriodicos.MESES, gasto.getMeses());
        db.insertOrThrow(DatosTablas.GASTOS_PERIODICOS, null, valores);
        return idGastos;

    }

    public String insertarIngresosFijos(ClaseIngresos ingreso){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idIngresos = DatosTablas.IngresosFijos.generarIdIngresosFijos();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasIngresosFijos.ID, idIngresos);
        valores.put(DatosTablas.ColumnasIngresosFijos.DESCRIPCION, ingreso.getDescripcion());
        valores.put(DatosTablas.ColumnasIngresosFijos.IMPORTE, ingreso.getImporte());
        valores.put(DatosTablas.ColumnasIngresosFijos.ESTADO, ingreso.getEstado());
        db.insertOrThrow(DatosTablas.INGRESOS_FIJOS, null, valores);
        return idIngresos;

    }

    public String insertarIngresosExtras(ClaseIngresos ingreso){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Genero el id del banco, no sé si hace falta esta instrucción.
        String idIngresos = DatosTablas.IngresosExtras.generarIdIngresosExtras();
        /* Creo el contenedor donde se almacenarán los valores de cada campo del regitro*/
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasIngresosExtras.ID, idIngresos);
        valores.put(DatosTablas.ColumnasIngresosExtras.DESCRIPCION, ingreso.getDescripcion());
        valores.put(DatosTablas.ColumnasIngresosExtras.IMPORTE, ingreso.getImporte());
        valores.put(DatosTablas.ColumnasIngresosExtras.ESTADO, ingreso.getEstado());
        valores.put(DatosTablas.ColumnasIngresosExtras.MESES, ingreso.getMeses());
        db.insertOrThrow(DatosTablas.INGRESOS_EXTRAS, null, valores);
        return idIngresos;

    }

    public Boolean actualizarBanco(ClaseBancos banco){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasBancos.DESCRIPCION, banco.getDescripcion());
        valores.put(DatosTablas.ColumnasBancos.SALDO, banco.getSaldo());
        String where = String.format("%s=?", DatosTablas.ColumnasBancos.ID);
        String[] argWhere = {banco.getIdentificador()};
        int resultado = db.update(DatosTablas.BANCOS,valores, where, argWhere);
        return resultado > 0;

    }

    public Boolean actualizarEstado(ClaseEstados estado){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatosTablas.ColumnasEstados.DESCRIPCION, estado.getDescripcion());
        String where = String.format("%s=?", DatosTablas.ColumnasBancos.ID);
        String[] argWhere = {estado.getIdentificador()};
        int resultado = db.update(DatosTablas.BANCOS,valores, where, argWhere);
        return resultado > 0;

    }


    public Boolean eliminarBanco(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasBancos.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.BANCOS, where, argWhere);
        return resultado > 0;

    }

    public Boolean eliminarEstado(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasEstados.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.ESTADOS, where, argWhere);
        return resultado > 0;

    }

/*
    public Boolean eliminarGastoFijo(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasEstados.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.ESTADOS, where, argWhere);
        return resultado > 0;

    }public Boolean eliminarGastoPeriodico(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasEstados.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.ESTADOS, where, argWhere);
        return resultado > 0;

    }

    public Boolean eliminarIngresoFijo(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasEstados.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.ESTADOS, where, argWhere);
        return resultado > 0;

    }public Boolean eliminarIngresoExtra(String id){

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        String where = DatosTablas.ColumnasEstados.ID + " =? ";
        String[] argWhere = {id};
        int resultado = db.delete(DatosTablas.ESTADOS, where, argWhere);
        return resultado > 0;

    }

*/


    public SQLiteDatabase getDb(){

        return baseDatos.getWritableDatabase();

    }

}
