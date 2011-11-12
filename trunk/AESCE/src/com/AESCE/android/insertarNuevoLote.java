package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class insertarNuevoLote extends Activity implements
		AdapterView.OnItemSelectedListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	// Campos que estan en el formulario
	Spinner spnNroLote;

	// Variables qeu se emplean para almacenar los valores del spinner Unidades
	String[] NroLote;
	int[] UmaId;
	int UMA_ID;
	int UMA_LOTENRO;
	int RAS_ID=1;

	// Bundle que se cargan del fomulario menuFinca
	String FIN_ID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertarnuevolote);

		spnNroLote = (Spinner) findViewById(R.id.IdInsertarNuevoLoteSpnNroLote);

		Bundle bundle = getIntent().getExtras();
		FIN_ID = "" + bundle.getString("FIN_ID");

		// -Crear la base de datos-//
		CrearBBDD();

		// -Llenar y adaptadores para las unidades-//
		llenarSpinnerNroLote();

		spnNroLote.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorNroLote = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, NroLote);

		adaptadorNroLote
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnNroLote.setAdapter(adaptadorNroLote);

		RAS_ID = ultimoRAS_ID();
	}

	/*************************************************************************************
	 ************************************* METODOS DE LOS BOTONES*************************
	 *************************************************************************************/

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnNroLote) {
			UMA_ID = UmaId[arg2];
			UMA_LOTENRO = Integer.parseInt(NroLote[arg2]);
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	// --Metodo para el boton insertar--//
	public void OnInsertarNuevoLoteBtnInsertar_Click(View button) {
		registarNroLote();
	}
	
	public void finalizar() {
		Intent iRasta = new Intent();
		iRasta.putExtra("returnRasta", "1");
		setResult(RESULT_OK, iRasta);
		super.finish();
	}

	/*************************************************************************************
	 ************************************* METODOS DE LA CLASE*****************************
	 *************************************************************************************/

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

	// --Metodo para llenar el spinner NroLote--//
	public void llenarSpinnerNroLote() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "UMA_FINID=" + Integer.parseInt(FIN_ID) + "";

			Cursor c = myDatabase.query("UMANEJO", new String[] { "UMA_ID",
					"UMA_LOTENRO" }, selected, null, null, null, "");

			int row = c.getCount();
			NroLote = new String[row];
			UmaId = new int[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {	
				UmaId[i] = c.getInt(0);
				NroLote[i] = "" + c.getInt(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para registrar el NroLote--//
	public void registarNroLote() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = this.RAS_ID;
			int RAS_UMAID = UMA_ID;

			ContentValues cv = new ContentValues();
			cv.put("RAS_ID", RAS_ID);
			cv.put("RAS_UMAID", RAS_UMAID);

			myDatabase.insert("Rasta", null, cv);

			finalizar();

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Recuparar el ultimo RAS_ID--//
	public int ultimoRAS_ID() {
		int RasId = 0;
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("RASTA",
					new String[] { "MAX(RAS_ID)+1" }, "", null, null, null,
					null);

			c.moveToFirst();

			if (c.isAfterLast() == false) {
				RasId = c.getInt(0);
			} else {
				RasId = 1;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
		return RasId;
	}
}
