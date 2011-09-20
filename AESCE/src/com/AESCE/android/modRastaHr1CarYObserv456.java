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
import android.widget.RadioGroup;
import android.widget.Spinner;

public class modRastaHr1CarYObserv456 extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Elementos que estan en el formulario
	EditText edtPH;
	Spinner spnCarbonatos;
	EditText edtProfundidad;
	Spinner spnPedSup;
	Spinner spnPedPerfil;
	RadioGroup rdgHorPedRoc;
	EditText edtHorPedRocProfundidad;
	EditText edtHorPedRocEspesor;
	EditText edtProfPriRocPied;

	// Variables para llenar los spinner de pedregosdiad
	int[] arrayPededregosidadId;
	String[] arrayPedegresosidadDesc;

	// Variables para el spinner de pedregosidad superficial
	int PED_IDSUP=1;

	// Variables para el spinner pedregosidad en el perfil
	int PED_IDPER=1;

	// Variables para el spinner carbonatos
	int[] arrayCarbonatosId;
	String[] arrayCarbonatosDesc;
	int CAR_ID=1;

	// Variable para el radioGroup
	int RAS_HORPEDROC = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1caryobserv456);

		
		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();

		// -Instanciar los elementos del formulario-//
		edtPH = (EditText) findViewById(R.id.Idmodrastahr1caryobserv456EdtPH);
		spnCarbonatos = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv456SpnCarbonatos);
		edtProfundidad = (EditText) findViewById(R.id.Idmodrastahr1caryobserv456EdtProfundidad);
		spnPedSup = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv456SpnPedSup);
		spnPedPerfil = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv456SpnPedPerfil);
		rdgHorPedRoc = (RadioGroup) findViewById(R.id.Idmodrastahr1caryobserv456RdgHorPedRoc);
		edtHorPedRocProfundidad = (EditText) findViewById(R.id.Idmodrastahr1caryobserv456EdtHorPedRocProfundidad);
		edtHorPedRocEspesor = (EditText) findViewById(R.id.Idmodrastahr1caryobserv456EdtHorPedRocEspesor);
		edtProfPriRocPied = (EditText) findViewById(R.id.Idmodrastahr1caryobserv456EdtProfPriRocPied);

		// -Metodo para Llenar los spinner de pedregosidad-//
		llenarSpnPedregosidad();
		
		// -Spinner Pedregosidad superficial-//
		spnPedSup.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorPedSup = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, arrayPedegresosidadDesc);

		adaptadorPedSup
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPedSup.setAdapter(adaptadorPedSup);

		// -Spinner Pedregosidad en el perfil-//		
		spnPedPerfil.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorPedPer = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, arrayPedegresosidadDesc);

		adaptadorPedPer
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPedPerfil.setAdapter(adaptadorPedPer);

		// -Spinner Carbonatos-//
		llenarSpnCarbonatos();
		
		spnCarbonatos.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorCarbonatos = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, arrayCarbonatosDesc);

		adaptadorCarbonatos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnCarbonatos.setAdapter(adaptadorCarbonatos);

		// -Asignar los checkedListener a los radioGroup-//
		rdgHorPedRoc.setOnCheckedChangeListener(this);

	}

	/***********************************************************************
	 ************************ METODOS DE LOS BOTONES*************************
	 ***********************************************************************/
	// --Metodo para el boton regresar--//
	public void OnModRastaHr1CarYObserv456BtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para el boton registrar--//
	public void OnModRastaHr1CarYObserv456BtnRegistrar_Click(View button) {
		registrar();
	}

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == rdgHorPedRoc) {
			if (arg1 == R.id.Idmodrastahr1caryobserv456RdoHorPedRocSI) {
				RAS_HORPEDROC = 1;
			} else if (arg1 == R.id.Idmodrastahr1caryobserv456RdoHorPedRocNO) {
				RAS_HORPEDROC = 2;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnCarbonatos) {
			CAR_ID = arrayCarbonatosId[arg2];
			Mensaje("CAR_ID",""+CAR_ID);
		}

		if (arg0 == spnPedSup) {
			PED_IDSUP = arrayPededregosidadId[arg2];
			Mensaje("PED_IDSUP",""+PED_IDSUP);
		}

		if (arg0 == spnPedPerfil) {
			PED_IDPER = arrayPededregosidadId[arg2];
			Mensaje("PED_IDPER",""+PED_IDPER);
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**************************************************************************
	 **************************** METODOS DE LA CLASE***************************
	 **************************************************************************/
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

	// --Metodo para llenar los spinner pedregosidad--//
	public void llenarSpnPedregosidad() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("PEDREGOSIDAD", new String[] {
					"PED_ID", "PED_DESC" }, "", null, null, null,
					"PED_DESC ASC");

			int row = c.getCount();
			arrayPededregosidadId = new int[row];
			arrayPedegresosidadDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayPededregosidadId[i] = c.getInt(0);
				arrayPedegresosidadDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para llenar el spinner carbonatos--//
	public void llenarSpnCarbonatos() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("CARBONATOS", new String[] { "CAR_ID",
					"CAR_DESC" }, "", null, null, null, "CAR_DESC ASC");

			int row = c.getCount();
			arrayCarbonatosId = new int[row];
			arrayCarbonatosDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayCarbonatosId[i] = c.getInt(0);
				arrayCarbonatosDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para registrar la caracteristicas y observaciones(4,5,6)--//
	public void registrar() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = Integer.parseInt(this.RAS_ID);
			int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
			int RAS_PH = Integer.parseInt(edtPH.getText().toString());
			int RAS_CARID = this.CAR_ID;
			float RAS_PROFCARBONATOS = Float.parseFloat(edtProfundidad
					.getText().toString());
			int RAS_PEDSUPID = this.PED_IDSUP;
			int RAS_PEDPERID = this.PED_IDPER;
			int RAS_HORPEDROC = this.RAS_HORPEDROC;
			float RAS_HORPEDROCPROF = Float.parseFloat(edtHorPedRocProfundidad
					.getText().toString());
			float RAS_HORPEDROCESP = Float.parseFloat(edtHorPedRocEspesor
					.getText().toString());
			float RAS_PROFPRIROCPIED = Float.parseFloat(edtProfPriRocPied
					.getText().toString());

			ContentValues cv = new ContentValues();
			cv.put("RAS_PH", RAS_PH);
			cv.put("RAS_CARID", RAS_CARID);
			cv.put("RAS_PROFCARBONATOS", RAS_PROFCARBONATOS);
			cv.put("RAS_PEDSUPID", RAS_PEDSUPID);
			cv.put("RAS_PEDPERID", RAS_PEDPERID);
			cv.put("RAS_HORPEDROC", RAS_HORPEDROC);
			cv.put("RAS_HORPEDROCPROF", RAS_HORPEDROCPROF);
			cv.put("RAS_HORPEDROCESP", RAS_HORPEDROCESP);
			cv.put("RAS_PROFPRIROCPIED", RAS_PROFPRIROCPIED);

			String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
					+ RAS_UMAID + "";

			myDatabase.update("RASTA", cv, selected, null);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}
	
	//--Metodo para limpiar las cajas de texto--//
	public void limpiar(){
		edtHorPedRocEspesor.setText("");
		edtHorPedRocProfundidad.setText("");
		edtPH.setText("");
		edtProfPriRocPied.setText("");
		edtProfundidad.setText("");
	}

}
