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

public class modrastahr1caryobserv789 extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Elementos que estan en el formulario
	RadioGroup rdgCapasEndurecidas;
	EditText edtCapEndurecidasProf;
	EditText edtCapEndurecidasEsp;
	RadioGroup rdgMoteados;
	EditText edtMoteadosProf;
	RadioGroup rdgMoteados70cm;
	Spinner spnMoteados70cmEst;

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// variables para el spinner estructura
	int[] arrayEstId;
	String[] arrayEstDesc;
	int EST_ID = 1;

	// Variable para el radioGroup capas endurecidas
	int CAP_SINO = 1;

	// Variable para el radioGroup Moteados
	int RAS_MOTEADOSSINO = 1;

	// Variable para el radioGroup Moteados70cm
	int RAS_MOTEADOS70CM = 1;

	// Variable para recuperar el ultimo CAP_ID
	int CAP_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1caryobserv789);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Instancia elementos del formulario-//
		rdgCapasEndurecidas = (RadioGroup) findViewById(R.id.Idmodrastahr1caryobserv789RdgCapEndurecidas);
		edtCapEndurecidasProf = (EditText) findViewById(R.id.Idmodrastahr1caryobserv789EdtCapEndurecidasProf);
		edtCapEndurecidasEsp = (EditText) findViewById(R.id.Idmodrastahr1caryobserv789EdtCapEndurecidasEsp);
		rdgMoteados = (RadioGroup) findViewById(R.id.Idmodrastahr1caryobserv789RdgMoteados);
		edtMoteadosProf = (EditText) findViewById(R.id.Idmodrastahr1caryobserv789EdtMoteadosProf);
		rdgMoteados70cm = (RadioGroup) findViewById(R.id.Idmodrastahr1caryobserv789RdgMoteados70cm);
		spnMoteados70cmEst = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv789SpnMoteados70cmEst);

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();

		// -Metodo para Llenar el spinner estructura y adaptadores-//
		llenarSpnEstructura();

		spnMoteados70cmEst.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorEstructura = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, arrayEstDesc);

		adaptadorEstructura
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnMoteados70cmEst.setAdapter(adaptadorEstructura);

		// -Asignar los checkedListener a los radioGroup-//
		rdgMoteados.setOnCheckedChangeListener(this);
		rdgMoteados70cm.setOnCheckedChangeListener(this);
		rdgCapasEndurecidas.setOnCheckedChangeListener(this);

		// -Recuperar ultimo CAP_ID-//
		CAP_ID = ultimoCAP_ID();
		
		Mensaje("ultimo CAP_ID", ""+CAP_ID);
	}

	/****************************************************************************
	 *********************** METODOS DE LOS BOTONES*******************************
	 ****************************************************************************/

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

		if (arg0 == rdgMoteados) {
			if (arg1 == R.id.Idmodrastahr1caryobserv789RdoMoteadosSI) {
				RAS_MOTEADOSSINO = 1;
			} else if (arg1 == R.id.Idmodrastahr1caryobserv789RdoMoteadosNO) {
				RAS_MOTEADOSSINO = 2;
			}
		}

		if (arg0 == rdgMoteados70cm) {
			if (arg1 == R.id.Idmodrastahr1caryobserv789RdoMoteados70cmSI) {
				RAS_MOTEADOS70CM = 1;
			} else if (arg1 == R.id.Idmodrastahr1caryobserv789RdoMoteados70cmNO) {
				RAS_MOTEADOS70CM = 2;
			}
		}

		if (arg0 == rdgCapasEndurecidas) {
			if (arg1 == R.id.Idmodrastahr1caryobserv789RdoCapEndurecidasSI) {
				CAP_SINO = 1;
			} else if (arg1 == R.id.Idmodrastahr1caryobserv789RdoCapEndurecidasNO) {
				CAP_SINO = 2;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if (arg0 == spnMoteados70cmEst) {
			EST_ID = arrayEstId[arg2];
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	//--Metodo para el boton regresar--//
	public void OnModRastaHr1BtnCaryObserv789BtnRegresar_Click(View button){
		finish();
	}
	
	//--Metodo para el boton adicionar una capa--//
	public void OnModRastaHr1BtnCaryObserv789BtnCapas_Click(View button){
		adiCapas();
	}
	
	//--Metodo para el boton registrar--//
	public void OnModRastaHr1BtnCaryObserv789BtnRegistrar_Click(View button){
		registrar();
	}
	
	

	/****************************************************************************
	 ************************ METODOS DE LA CLASE*******************************
	 ****************************************************************************/
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
	public void llenarSpnEstructura() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("ESTRUCTURA", new String[] { "EST_ID",
					"EST_DESC" }, "", null, null, null, "EST_DESC ASC");

			int row = c.getCount();
			arrayEstId = new int[row];
			arrayEstDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayEstId[i] = c.getInt(0);
				arrayEstDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para adicionar una capa--//
	public void adiCapas() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int CAP_ID = this.CAP_ID;
			int CAP_RASID = Integer.parseInt(this.RAS_ID);
			int CAP_SINO = this.CAP_SINO;
			float CAP_PROF = Float.parseFloat(edtCapEndurecidasProf.getText()
					.toString());
			float CAP_ESP = Float.parseFloat(edtCapEndurecidasEsp.getText()
					.toString());

			ContentValues cv = new ContentValues();
			cv.put("CAP_ID", CAP_ID);
			cv.put("CAP_RASID", CAP_RASID);
			cv.put("CAP_SINO", CAP_SINO);
			cv.put("CAP_PROF", CAP_PROF);
			cv.put("CAP_ESP", CAP_ESP);

			myDatabase.insert("CAPASENDURECIDAS", null, cv);

			limpiar();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Recuperar el ultimo CAP_ID--//
	public int ultimoCAP_ID() {
		int CapId = 1;
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("CAPASENDURECIDAS",
					new String[] { "MAX(CAP_ID)+1" }, "", null, null, null,
					null);

			c.moveToFirst();

			if (c.isAfterLast() == false) {
				CapId = c.getInt(0);
			} else {
				CapId = 1;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
		return CapId;
	}

	// --Metodo para limpiar las cajas de texto--//
	public void limpiar() {
		edtCapEndurecidasEsp.setText("");
		edtCapEndurecidasProf.setText("");
	}
	
	// --Metodo para registrar la caracteristicas y observaciones(7,8,9)--//
		public void registrar() {
			BaseDatosHelper bdH = new BaseDatosHelper(this);
			try {

				SQLiteDatabase myDatabase = bdH.getWritableDatabase();
				bdH.abrirBaseDatos();

				int RAS_ID = Integer.parseInt(this.RAS_ID);
				int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
				int RAS_MOTEADOSSINO = this.RAS_MOTEADOSSINO;
				float RAS_MOTEADOSPROF = Float.parseFloat(edtMoteadosProf.getText().toString());
				int RAS_MOTEADOS70CM = this.RAS_MOTEADOS70CM;
				int RAS_EST=this.EST_ID;
				
				ContentValues cv = new ContentValues();
				cv.put("RAS_MOTEADOSSINO", RAS_MOTEADOSSINO);
				cv.put("RAS_MOTEADOSPROF", RAS_MOTEADOSPROF);
				cv.put("RAS_MOTEADOS70CM", RAS_MOTEADOS70CM);
				cv.put("RAS_ESTID", RAS_EST);
				
				String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
						+ RAS_UMAID + "";

				myDatabase.update("RASTA", cv, selected, null);

				limpiar2();

				finish();

			} catch (Exception e) {
				Mensaje("Error", "" + e.getMessage());

			} finally {
				bdH.close();
			}
		}
		
		//--Metodo para limpiar las cajas de texto--//
		public void limpiar2(){
			edtMoteadosProf.setText("");
		}
}
