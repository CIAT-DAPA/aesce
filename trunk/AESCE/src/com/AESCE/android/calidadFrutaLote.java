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

public class calidadFrutaLote extends Activity {

	// Variables para capturar los bundles
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String UMA_ID = "";

	// Elementos del formulario
	TextView txvCalidadFrutaLote1;
	EditText edtPorcentaje2008Lote1;
	EditText edtPorcentaje2009Lote1;
	EditText edtPorcentaje2010Lote1;
	TextView txvCalidadFrutaLote2;
	EditText edtPorcentaje2008Lote2;
	EditText edtPorcentaje2009Lote2;
	EditText edtPorcentaje2010Lote2;

	private String[] calidadDeFruta = new String[] { "Extra o exportación",
			"Primera", "Segunda", "Otras de menos calidad" };
	int cFruta1 = 0;
	int cFruta2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calidadfrutalote);

		// -Instanciar elementos del formulario-//
		txvCalidadFrutaLote1 = (TextView) findViewById(R.id.IdCalidadFrutaTxvCalidadFrutaLote1);
		edtPorcentaje2008Lote1 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2008Lote1);
		edtPorcentaje2009Lote1 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2009Lote1);
		edtPorcentaje2010Lote1 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2010Lote1);
		txvCalidadFrutaLote2 = (TextView) findViewById(R.id.IdCalidadFrutaTxvCalidadFrutaLote2);
		edtPorcentaje2008Lote2 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2008Lote2);
		edtPorcentaje2009Lote2 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2009Lote2);
		edtPorcentaje2010Lote2 = (EditText) findViewById(R.id.IdCalidadFrutaEdtPorcentaje2010Lote2);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		UMA_ID = "" + bundle.getString("UMA_ID");

		// -Crear la base de datos-//
		CrearBBDD();

		txvCalidadFrutaLote1.setText(calidadDeFruta[cFruta1]);
		txvCalidadFrutaLote2.setText(calidadDeFruta[cFruta2]);
	}

	/**********************************************************************************************
	 ******************************** METODOS DE LOS BOTONES****************************************
	 **********************************************************************************************/
	// --Boton regresar--//
	public void OnCalidadFrutaBtnRegresar_Click(View button) {
		finish();
	}

	// --Boton registrar lote1--//
	public void OnCalidadFrutaBtnRegistrarLote1_Click(View button) {
		if (cFruta1 <= 3) {
			Mensaje("calidad1",""+cFruta1);
			insertProduccion(1, cFruta1,
					Float.parseFloat(edtPorcentaje2008Lote1.getText()
							.toString()),
					Float.parseFloat(edtPorcentaje2009Lote1.getText()
							.toString()),
					Float.parseFloat(edtPorcentaje2010Lote1.getText()
							.toString()));
			
			findViewById(R.id.IdCalidadFrutaBtnRegistrarLote1).setEnabled(true);
			cFruta1++;
			if(cFruta1!=calidadDeFruta.length)
			txvCalidadFrutaLote1.setText(calidadDeFruta[cFruta1]);
		}
		else{
			cFruta1=0;
			findViewById(R.id.IdCalidadFrutaBtnRegistrarLote1).setEnabled(false);
		}
	}
	
	//--Boton registrar lote 2--//
	public void OnCalidadFrutaBtnRegistrarLote2_Click(View button){
		if (cFruta2 <= 3) {
			Mensaje("calidad2",""+cFruta2);
			insertProduccion(2, cFruta2,
					Float.parseFloat(edtPorcentaje2008Lote2.getText()
							.toString()),
					Float.parseFloat(edtPorcentaje2009Lote2.getText()
							.toString()),
					Float.parseFloat(edtPorcentaje2010Lote2.getText()
							.toString()));
			
			findViewById(R.id.IdCalidadFrutaBtnRegistrarLote2).setEnabled(true);
			cFruta2++;
			if(cFruta2!=calidadDeFruta.length)
			txvCalidadFrutaLote2.setText(calidadDeFruta[cFruta2]);
		}
		else{
			cFruta2=0;
			findViewById(R.id.IdCalidadFrutaBtnRegistrarLote2).setEnabled(false);
		}
	}

	/**********************************************************************************************
	 ******************************** METODOS DE LA CLASE******************************************
	 **********************************************************************************************/
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
	public void insertProduccion(int lote, int calFruta, float porcentaje2008,
			float porcentaje2009, float porcentaje2010) {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int CAL_UMAID = Integer.parseInt(this.UMA_ID);
			int CAL_TPCID = calFruta;
			int CAL_LOTE = lote;
			int CAL_ANIO = 2008;
			float CAL_PORCENTAJE = porcentaje2008;

			ContentValues cv = new ContentValues();
			cv.put("CAL_UMAID", CAL_UMAID);
			cv.put("CAL_TPCID", CAL_TPCID);
			cv.put("CAL_LOTE", CAL_LOTE);
			cv.put("CAL_ANIO", CAL_ANIO);
			cv.put("CAL_PORCENTAJE", CAL_PORCENTAJE);

			myDatabase.insert("CALIDAD", null, cv);

			cv.clear();

			CAL_ANIO = 2009;
			CAL_PORCENTAJE = porcentaje2009;

			cv.put("CAL_UMAID", CAL_UMAID);
			cv.put("CAL_TPCID", CAL_TPCID);
			cv.put("CAL_LOTE", CAL_LOTE);
			cv.put("CAL_ANIO", CAL_ANIO);
			cv.put("CAL_PORCENTAJE", CAL_PORCENTAJE);

			myDatabase.insert("CALIDAD", null, cv);

			cv.clear();

			CAL_ANIO = 2010;
			CAL_PORCENTAJE = porcentaje2010;

			cv.put("CAL_UMAID", CAL_UMAID);
			cv.put("CAL_TPCID", CAL_TPCID);
			cv.put("CAL_LOTE", CAL_LOTE);
			cv.put("CAL_ANIO", CAL_ANIO);
			cv.put("CAL_PORCENTAJE", CAL_PORCENTAJE);

			myDatabase.insert("CALIDAD", null, cv);

			limpiar(lote);

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los limpiar los elementos--//
	public void limpiar(int n) {
		if (n == 1) {
			edtPorcentaje2008Lote1.setText("");
			edtPorcentaje2009Lote1.setText("");
			edtPorcentaje2010Lote1.setText("");
		} else if (n == 2) {
			edtPorcentaje2008Lote2.setText("");
			edtPorcentaje2009Lote2.setText("");
			edtPorcentaje2010Lote2.setText("");
		}
	}
}
