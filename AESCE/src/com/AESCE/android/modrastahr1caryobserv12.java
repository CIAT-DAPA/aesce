package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class modrastahr1caryobserv12 extends Activity implements
		AdapterView.OnItemSelectedListener {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Componentes que estan en el formulario
	Spinner spnTerrenoCircundante;
	Spinner spnPefil;
	EditText edtPendiente;

	// Variables para el spinner del perfil
	int[] arrayPosPerfilId;
	String[] arrayPosPerfilDesc;
	int POS_ID;

	// Variables para el spinner del terrenoCirundante
	int[] arrayTerCircundanteId;
	String[] arrayTerCircundanteDesc;
	int TER_ID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1caryobserv12);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -instanciar los elementos del formulario-//
		edtPendiente = (EditText) findViewById(R.id.Idmodrastahr1caryobserv12EdtPendiente);
		spnTerrenoCircundante = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv12SpnTerrenoCircundate);
		spnPefil = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv12SpnPerfil);

		// -crear la base de datos-//
		CrearBBDD();

		// -Llenar y adaptadores para el spinner POSPERFIL-//
		llenarSpnPerfil();
		spnPefil.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorPerfil = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, arrayPosPerfilDesc);

		adaptadorPerfil
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPefil.setAdapter(adaptadorPerfil);

		// -Llenar y adaptadores para el spinner TerrrenoCircundante-//
		llenarSPinnerTerCircundante();

		spnTerrenoCircundante.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorTerCircundante = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, arrayTerCircundanteDesc);

		adaptadorTerCircundante
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnTerrenoCircundante.setAdapter(adaptadorTerCircundante);
	}

	/**********************************************************************
	 ************************** METODOS DE LOS BOTONES**********************
	 **********************************************************************/
	// -- Metodo para el boton regresar--//
	public void OOnModRastaHr1CarYObservBtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para el boton registrar--//
	public void OnModRastaHr1CarYObservBtnRegistrar_Click(View button) {
		RegistrarRasta();
	}

	// --Metodo para los spinner--//
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnPefil) {
			POS_ID = arrayPosPerfilId[arg2];
		}

		if (arg0 == spnTerrenoCircundante) {
			TER_ID = arrayTerCircundanteId[arg2];
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**********************************************************************
	 *************************** METODOS DE LA CLASE***********************
	 **********************************************************************/

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

	// --Metodo para llenar el spinner perfil--//
	public void llenarSpnPerfil() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("POSPERFIL", new String[] { "POS_ID",
					"POS_DESC" }, "", null, null, null, "POS_DESC ASC");

			int row = c.getCount();
			arrayPosPerfilId = new int[row];
			arrayPosPerfilDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayPosPerfilId[i] = c.getInt(0);
				arrayPosPerfilDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para llenar el spinner terreno circundante--//
	public void llenarSPinnerTerCircundante() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("TERRENO", new String[] { "TER_ID",
					"TER_DESC" }, "", null, null, null, "TER_DESC ASC");

			int row = c.getCount();
			arrayTerCircundanteId = new int[row];
			arrayTerCircundanteDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayTerCircundanteId[i] = c.getInt(0);
				arrayTerCircundanteDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para llenar la TABLA RASTA--//
	public void RegistrarRasta() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = Integer.parseInt(this.RAS_ID);
			int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
			float RAS_PENDIENTE = Float.parseFloat(edtPendiente.getText()
					.toString());
			int RAS_TERID = this.TER_ID;
			int RAS_POSID = this.POS_ID;

			ContentValues cv = new ContentValues();
			cv.put("RAS_PENDIENTE", RAS_PENDIENTE);
			cv.put("RAS_TERID", RAS_TERID);
			cv.put("RAS_POSID", RAS_POSID);

			String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
					+ RAS_UMAID + "";

			myDatabase.update("RASTA", cv, selected, null);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error 2", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los elementos--//
	public void limpiar() {
		edtPendiente.setText("");
	}
}
