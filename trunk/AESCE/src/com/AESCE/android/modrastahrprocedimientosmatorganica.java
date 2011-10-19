package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class modrastahrprocedimientosmatorganica extends Activity implements
		RadioGroup.OnCheckedChangeListener {

	// Elementos del formulario
	RadioGroup rdgPreg1;
	RadioGroup rdgPreg2;
	RadioGroup rdgPreg3;
	RadioGroup rdgPreg4;

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Variables para almacenar la seleccion de los radioGroup
	int PRC_RES1 = 1;
	int PRC_RES2 = 1;
	int PRC_RES3 = 1;
	int PRC_RES4 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahrprocedimientosmatorganica);

		// -Instanciar elementos del formulario-//
		rdgPreg1 = (RadioGroup) findViewById(R.id.IdModRastaHrProcedimientosMatOrganicaRdgPreg1);
		rdgPreg2 = (RadioGroup) findViewById(R.id.IdModRastaHrProcedimientosMatOrganicaRdgPreg2);
		rdgPreg3 = (RadioGroup) findViewById(R.id.IdModRastaHrProcedimientosMatOrganicaRdgPreg3);
		rdgPreg4 = (RadioGroup) findViewById(R.id.IdModRastaHrProcedimientosMatOrganicaRdgPreg4);

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

		// -Los checkedListener a los RadioGroup-//
		rdgPreg1.setOnCheckedChangeListener(this);
		rdgPreg2.setOnCheckedChangeListener(this);
		rdgPreg3.setOnCheckedChangeListener(this);
		rdgPreg4.setOnCheckedChangeListener(this);
	}

	/*********************************************************************************************
	 ******************************** METODOS DE LOS BOTONES***************************************
	 *********************************************************************************************/
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == rdgPreg1) {
			if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg1SI) {
				PRC_RES1 = 1;
			} else if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg1NO) {
				PRC_RES1 = 2;
			}
		}

		if (arg0 == rdgPreg2) {
			if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg2SI) {
				PRC_RES2 = 1;
			} else if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg2NO) {
				PRC_RES2 = 2;
			}
		}

		if (arg0 == rdgPreg3) {
			if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg3SI) {
				PRC_RES3 = 1;
			} else if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg3NO) {
				PRC_RES3 = 2;
			}
		}

		if (arg0 == rdgPreg4) {
			if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg4SI) {
				PRC_RES4 = 1;
			} else if (arg1 == R.id.IdModRastaHrProcedimientosMatOrganicaRdoPreg4NO) {
				PRC_RES4 = 2;
			}
		}
	}

	// --Metodo para el boton regresar--//
	public void OnModRastaHrProcedimientosMatOrganicaBtnRegresar_Click(
			View button) {
		finish();
	}

	// --Metodo para el boton registrar--//
	public void OnModRastaHrProcedimientosMatOrganicaBtnRegistrar_Click(
			View button) {
		regMatOrganica();
	}

	/*********************************************************************************************
	 ******************************** METODOS DE LA CLASE***************************************
	 *********************************************************************************************/
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

	// --Metodo para adicionar una capa--//
	public void regMatOrganica() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int PRC_RASID = Integer.parseInt(this.RAS_ID);
			int PRC_RASUMAID = Integer.parseInt(this.RAS_UMAID);
			int PRC_ID = 3;

			int PRC_IDPREG = 1;
			int PRC_RES = this.PRC_RES1;

			ContentValues cv = new ContentValues();

			cv.put("PRC_RASID", PRC_RASID);
			cv.put("PRC_RASUMAID", PRC_RASUMAID);
			cv.put("PRC_ID", PRC_ID);
			cv.put("PRC_IDPREG", PRC_IDPREG);
			cv.put("PRC_RES", PRC_RES);

			myDatabase.insert("PROCEDIMIENTOS", null, cv);

			PRC_IDPREG = 2;
			PRC_RES = this.PRC_RES2;

			cv.clear();
			cv.put("PRC_RASID", PRC_RASID);
			cv.put("PRC_RASUMAID", PRC_RASUMAID);
			cv.put("PRC_ID", PRC_ID);
			cv.put("PRC_IDPREG", PRC_IDPREG);
			cv.put("PRC_RES", PRC_RES);

			myDatabase.insert("PROCEDIMIENTOS", null, cv);

			PRC_IDPREG = 3;
			PRC_RES = this.PRC_RES3;

			cv.clear();
			cv.put("PRC_RASID", PRC_RASID);
			cv.put("PRC_RASUMAID", PRC_RASUMAID);
			cv.put("PRC_ID", PRC_ID);
			cv.put("PRC_IDPREG", PRC_IDPREG);
			cv.put("PRC_RES", PRC_RES);

			myDatabase.insert("PROCEDIMIENTOS", null, cv);

			PRC_IDPREG = 4;
			PRC_RES = this.PRC_RES4;

			cv.clear();
			cv.put("PRC_RASID", PRC_RASID);
			cv.put("PRC_RASUMAID", PRC_RASUMAID);
			cv.put("PRC_ID", PRC_ID);
			cv.put("PRC_IDPREG", PRC_IDPREG);
			cv.put("PRC_RES", PRC_RES);

			myDatabase.insert("PROCEDIMIENTOS", null, cv);

			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

}
