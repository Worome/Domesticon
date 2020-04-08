package com.trianacodes.script.domesticon.sqlite;

/* Esta es la primera clase que se crea para luego poder manejar Bases de Datos. En ella definimos
   la estructura general de la Base de Datos en e que se definen las columnas de cada tabla. Un
   enfoque práctico es crear una interfaz por cada tabla y se le añada un nombre estándard. A
   continuación, se implementan las interfaces en una clases auxiliares que ayuden a la
   administración de cada tabla. */

import java.net.PortUnreachableException;
import java.util.UUID;

public class DatosTablas {

    private DatosTablas() {
    }

    public static String ESTADOS = "estados";
    public static String BANCOS = "bancos";
    public static String GASTOS_FIJOS = "gastos_fijos";
    public static String GASTOS_PERIODICOS = "gastos_periodicos";
    public static String INGRESOS_FIJOS = "ingresos_fijos";
    public static String INGRESOS_EXTRAS = "ingresos_extras";
    public static String CABECERA_HISTORICO = "cabecera_historico";
    public static String LINEAS_HISTORICO = "lineas_historico";


    /* Las actualizaciones de bases de datos siempre deben ir como esta instrucción  de ejemplo de abajo: */
    /*public static final String ACTUALIZA_BASEDATOS_VERSION_2 = "ALTER TABLE " + TABLA_AROMA + " ADD COLUMN " +
            AROMA_OBSERVACIONES + " TEXT";
      public static final String ACTUALIZA_BASEDATOS_VERSION_3 = "ALTER TABLE " + TABLA_AROMA + " ADD COLUMN " +
            AROMA_HASTA_PORCENTAJE + INTEGER_TYPE + COMMA_SEP +
            AROMA_VALORACION + REAL_TYPE + COMMA_SEP + AROMA_IMAGEN + TEXT_TYPE;
     */

    /* Constantes que contienen las sentencias de creación de las tablas (Cada vez que añada, borre
     o modifique un campo de una tabla debo añadir aquí el cambio. Esto es para que si la base de
     datos se crea nueva, vaya con todas las modificaciones hechas)*/
    public static String PLANTILLA_TABLAS_DOS_CAMPOS = "CREATE TABLE %s (%s TEXT PRIMARY KEY, " +
            "%s TEXT NOT NULL)";
    public static String PLANTILLA_TABLAS_TRES_CAMPOS = "CREATE TABLE %s (%s TEXT PRIMARY KEY, " +
            "%s TEXT NOT NULL, %s REAL)";
    public static String PLANTILLA_TABLAS_CUATRO_CAMPOS = "CREATE TABLE %s (%s TEXT PRIMARY KEY, " +
            "%s TEXT NOT NULL, %s INTEGER, %s INTEGER)";
    public static String PLANTILLA_TABLAS_CINCO_CAMPOS = "CREATE TABLE %s (%s TEXT PRIMARY KEY, " +
            "%s TEXT NOT NULL, %s INTEGER, %s TEXT NOT NULL, %s INTEGER)";
    public static String PLANTILLA_TABLAS_CABECERAS = "CREATE TABLE %s (%s TEXT PRIMARY KEY, " +
            "%s INTEGER, %s INTEGER, %s REAL, %s REAL, %s REAL)";
    public static String PLANTILLA_TABLAS_LINEAS = "CREATE TABLE %s (%s TEXT NOT NULL REFERENCES %s (%s)" +
            " ON DELETE CASCADE, %s TEXT PRIMARY KEY, %s TEXT NOT" +
            " NULL, %s REAL NOT NULL, UNIQUE(%s,%s))";

    /*La última plantilla sustituyendo los %s debe quedar así:
        CREATE TABLE Lineas Historico (Id_Cabecera INTEGER NOT NULL REFERENCES
            Cabecera Historico (Id_Cabecera) ON DELETE CASCADE, Id INTEGER PRIMARY KEY
            AUTOINCREMENT, Concepto TEXT NOT NULL, Importe REAL NOT NULL, UNIQUE (Id_Cabecera, Id)
     */

    interface ColumnasEstados{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";

    }

    interface ColumnasBancos{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";
        String SALDO = "Saldo";

    }

    interface ColumnasGastosFijos{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";
        String IMPORTE = "Importe";
        String ESTADO = "Estado";

    }

    interface ColumnasGastosPeriodicos{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";
        String IMPORTE = "Importe";
        String MESES = "Meses";
        String ESTADO = "Estado";

    }


    interface ColumnasIngresosFijos{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";
        String IMPORTE = "Importe";
        String ESTADO = "Estado";

    }

    interface ColumnasIngresosExtras{

        String ID = "Id";
        String DESCRIPCION = "Descripcion";
        String IMPORTE = "Importe";
        String MESES = "Meses";
        String ESTADO = "Estado";

    }

    interface ColumnasCabeceraHistorico{

            String ID = "Id_Cabecera";
            String ANO = "Ano";
            String MES = "Mes";
            String SALDO_BANCOS = "Saldo_Bancos";
            String TOTAL_INGRESOS = "Total_Ingresos";
            String TOTAL_GASTOS = "Total_Gastos";

    }

    interface ColumnasLineasHistorico{

        String ID_CABECERA = "Id_Cabecera";
        String ID = "Id";
        String CONCEPTO = "Concepto";
        String IMPORTE = "Importe";

    }


    /* ahora vienen las clases que van a ayudar a la administración de las tablas */

    public static class Estados implements ColumnasEstados{

        public static String generarIdEstados(){

            return "ES-" + UUID.randomUUID().toString();

        }

    }

    public static class Bancos implements ColumnasBancos{

        public static String generarIdBancos(){

            return "BA-" + UUID.randomUUID().toString();

        }

    }

    public static class GastosFijos implements ColumnasGastosFijos{

        public static String generarIdGastosFijos(){

            return "GF-" + UUID.randomUUID().toString();

        }

    }

    public static class GastosPeriodicos implements ColumnasGastosPeriodicos{

        public static String generarIdGastosPeriodicos(){

            return "GP-" + UUID.randomUUID().toString();

        }

    }

    public static class IngresosFijos implements ColumnasIngresosFijos{

        public static String generarIdIngresosFijos(){

            return "IF-" + UUID.randomUUID().toString();

        }

    }

    public static class IngresosExtras implements ColumnasIngresosExtras{

        public static String generarIdIngresosExtras(){

            return "IE-" + UUID.randomUUID().toString();

        }

    }

    public static class CabeceraHistorico implements ColumnasCabeceraHistorico{

        public static String generarIdCabeceraHistorico(){

            return "CH-" + UUID.randomUUID().toString();

        }

    }

    public static class LineaHistorico implements ColumnasLineasHistorico{

        // Incluir métodos auxiliares
        public static String generarIdLineaHistorico(){

            return "LH-" + UUID.randomUUID().toString();

        }

    }

}
