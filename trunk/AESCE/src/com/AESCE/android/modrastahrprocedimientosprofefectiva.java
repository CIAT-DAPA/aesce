package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class modrastahrprocedimientosprofefectiva extends Activity {

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Elementos del formulario
	EditText edtPreg1;
	EditText edtPreg2;
	EditText edtPreg3;
	EditText edtPreg4;
	EditText edtPreg5;
	EditText edtPreg6;
	EditText edtPreg7;
	EditText edtPreg8;
	EditText edtPreg9;
	EditText edtPreg10;
	EditText edtPreg11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahrprocedimientosprofefectiva);

		// -Instancia elementos del formulario-//
		edtPreg1 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg1);
		edtPreg2 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg2);
		edtPreg3 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg3);
		edtPreg4 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg4);
		edtPreg5 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg5);
		edtPreg6 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg6);
		edtPreg7 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg7);
		edtPreg8 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg8);
		edtPreg9 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg9);
		edtPreg10 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg10);
		edtPreg11 = (EditText) findViewById(R.id.IdModRastaHrProcedimientosProfEfectivaEdtPreg11);

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");
	}

	/********************************************************************************************
	 ************************************ METODOS DE LOS BOTONES**********************************
	 ********************************************************************************************/

	// --Metodo para el boton regresar--//
	public void OnModRastaHrProcedimientosProfEfectivaBtnRegresar_Click(
			View button) {
		finish();
	}

	// --Metodo para el boto regresar--//
	public void OnModRastaHrProcedimientosProfEfectivaBtnRegistrar_Click(
			View button) {
		registrarProfEfectiva();
	}

	/********************************************************************************************
	 ************************************ METODOS DE LA CLASE*************************************
	 ********************************************************************************************/
	// -- METODO PARA IMPRIMIR MENSAJES--//
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

	// --Metodo para registrar la profundidad efectiva--//
	public void registrarProfEfectiva() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int PRF_RASID = Integer.parseInt(this.RAS_ID);
			int PRF_RASUMAID = Integer.parseInt(this.RAS_UMAID);
			int PRF_ID = 2;

			int PRF_IDPREG = 1;
			float PRF_PROF = Float.parseFloat(edtPreg1.getText().toString());

			ContentValues cv = new ContentValues();

			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 2;
			PRF_PROF = Float.parseFloat(edtPreg2.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 3;
			PRF_PROF = Float.parseFloat(edtPreg3.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 4;
			PRF_PROF = Float.parseFloat(edtPreg4.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 5;
			PRF_PROF = Float.parseFloat(edtPreg5.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 6;
			PRF_PROF = Float.parseFloat(edtPreg6.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			finish();

			PRF_IDPREG = 7;
			PRF_PROF = Float.parseFloat(edtPreg7.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 8;
			PRF_PROF = Float.parseFloat(edtPreg8.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 9;
			PRF_PROF = Float.parseFloat(edtPreg9.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 10;
			PRF_PROF = Float.parseFloat(edtPreg10.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

			PRF_IDPREG = 11;
			PRF_PROF = Float.parseFloat(edtPreg11.getText().toString());

			cv.clear();
			cv.put("PRF_RASID", PRF_RASID);
			cv.put("PRF_RASUMAID", PRF_RASUMAID);
			cv.put("PRF_ID", PRF_ID);
			cv.put("PRF_IDPREG", PRF_IDPREG);
			cv.put("PRF_PROF", PRF_PROF);

			myDatabase.insert("PROCEDIMIENTOSPROF", null, cv);

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

}
