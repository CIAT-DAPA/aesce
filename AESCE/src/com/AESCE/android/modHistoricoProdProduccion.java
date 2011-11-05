package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class modHistoricoProdProduccion extends Activity {

	// Variables para capturar los bundles
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String UMA_ID = "";

	// Elementos del formulario
	TextView txvAnio1;
	EditText edtProduccion1;
	TextView txvAnio2;
	EditText edtProduccion2;

	int anio1 = 2003;
	int anio2 = 2003;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modhistoricoprodproduccion);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		UMA_ID = "" + bundle.getString("UMA_ID");

		// -Instanciar los elementos del formulario-//
		txvAnio1 = (TextView) findViewById(R.id.IdModHistoricoProdProduccionTxvAnio1);
		edtProduccion1 = (EditText) findViewById(R.id.IdModHistoricoProdProduccionEdtProduccion1);
		txvAnio2 = (TextView) findViewById(R.id.IdModHistoricoProdProduccionTxvAnio2);
		edtProduccion2 = (EditText) findViewById(R.id.IdModHistoricoProdProduccionEdtProduccion2);

		// -Crear la base de datos-//
		CrearBBDD();
		
		
		

	}

	/*********************************************************************************************
	 ******************************** METODOS DE LOS BOTONES**************************************
	 *********************************************************************************************/
	// --Boton regresar--//
	public void OnModHistoricoProdProduccionBtnRegresar_Click(View button) {
		finish();
	}

	// --Boton siguiente para el lote1--//
	public void OnModHistoricoProdProduccionBtnSiguiente1_Click(View button) {
		if (anio1 <= 2011) {
			
			insertProduccion(1, anio1);
			anio1++;
			if(anio1!=2012)
			txvAnio1.setText(String.valueOf(anio1));
		} else {
			findViewById(R.id.IdModHistoricoProdProduccionBtnSiguiente1)
					.setEnabled(false);
		}
	}

	// --Boton siguiente para el lote2--//
	public void OnModHistoricoProdProduccionBtnSiguiente2_Clcik(View button) {
		if (anio2 <= 2011) {
			insertProduccion(2, anio2);
			anio2++;
			if(anio2!=2012)
			txvAnio2.setText(String.valueOf(anio2));
		} else {
			findViewById(R.id.IdModHistoricoProdProduccionBtnSiguiente2)
					.setEnabled(false);
		}
	}

	/*********************************************************************************************
	 ********************************** METODOS DE LA CLASE***************************************
	 *********************************************************************************************/
	// METODO PARA IMPRIMIR MENSAJES
	public void Mensaje(String titulo, String Mensaje) {

		String squence = "" + Mensaje;
		String title = "" + titulo;
		String PositiveButton = "OK";

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(squence)
				.setTitle(title)
				.setIcon(R.drawable.icon)
				.setCancelable(false)
				.setPositiveButton(PositiveButton,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();

							}
						});

		AlertDialog alert = builder.create();
		alert.show();

	}

	// --Metodo para crear la base de datos--//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
		}
	}

	// Metodo para insertar en la tabla produccion
	public void insertProduccion(int lote, int anio) {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int PRC_LOTE = lote;
			int PRC_UMAID = Integer.parseInt(this.UMA_ID);
			int PRC_ANIO = anio;
			float PRC_PRODUCCION=0;
			if(lote==1){
			PRC_PRODUCCION= Float.parseFloat(edtProduccion1.getText()
					.toString());
			}
			else if(lote==2){
				PRC_PRODUCCION= Float.parseFloat(edtProduccion2.getText()
						.toString());
			}
			ContentValues cv = new ContentValues();
			cv.put("PRC_LOTE", PRC_LOTE);
			cv.put("PRC_UMAID", PRC_UMAID);
			cv.put("PRC_ANIO", PRC_ANIO);
			cv.put("PRC_PRODUCCION", PRC_PRODUCCION);

			myDatabase.insert("PRODUCCION", null, cv);
			cv.clear();
			
			limpiar(lote);

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los limpiar los elementos--//
	public void limpiar(int n) {
		if (n == 1)
			edtProduccion1.setText("");
		else if (n == 2)
			edtProduccion2.setText("");
	}

}
