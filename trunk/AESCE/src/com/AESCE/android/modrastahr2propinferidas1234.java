package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class modrastahr2propinferidas1234 extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Elementos del formulario
	EditText edtProfEfectiva;
	Spinner spnHorizonte;
	Spinner spnMatOrganica;
	RadioGroup rdgSueloOrganico;
	Spinner spnDrenInterno;
	Spinner spnDrenExterno;
	Spinner spnSalinidad;
	Spinner spnSodicidad;

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	int horizonte = 1;
	int matOrganica = 1;
	int sloOrganico;
	int drenInterno = 1;
	int drenExterno = 1;
	int salinidad = 1;
	int sodicidad = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr2propinferidas1234);

		// --Instancia elementos del formulario--//
		edtProfEfectiva = (EditText) findViewById(R.id.IdModRastaHr2PropInferidas1234EdtProfEfectiva);
		spnHorizonte = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnHorizonte);
		spnMatOrganica = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnMatOrganica);
		rdgSueloOrganico = (RadioGroup) findViewById(R.id.IdModRastaHr2PropInferidas1234RdgSueloOrganico);
		spnDrenInterno = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnDrenInterno);
		spnDrenExterno = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnDrenExterno);
		spnSalinidad = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnSalinidad);
		spnSodicidad = (Spinner) findViewById(R.id.IdModRastaHr2PropInferidas1234SpnSodicidad);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Crear la base de datos-//
		CrearBBDD();

		// -Asignar los checkedListener a los RadioGroup-//
		rdgSueloOrganico.setOnCheckedChangeListener(this);

		// -Spinner Horizonte-//
		spnHorizonte.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorHorizonte = ArrayAdapter
				.createFromResource(this, R.array.rastaHr2PropInferidasCapas,
						android.R.layout.simple_spinner_item);

		adaptadorHorizonte
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnHorizonte.setAdapter(adaptadorHorizonte);

		// -Spinner Materia Organica-//
		spnMatOrganica.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorMatOrtanica = ArrayAdapter
				.createFromResource(this,
						R.array.rastaHr2PropInferidasMatOrganica,
						android.R.layout.simple_spinner_item);

		adaptadorMatOrtanica
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnMatOrganica.setAdapter(adaptadorMatOrtanica);

		// -Spinner Drenaje Interno-//
		spnDrenInterno.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorDrenInterno = ArrayAdapter
				.createFromResource(this,
						R.array.rastaHr2PropInferidasDrenInterno,
						android.R.layout.simple_spinner_item);

		adaptadorDrenInterno
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnDrenInterno.setAdapter(adaptadorDrenInterno);

		// -Spinner Drenaje Externo-//
		spnDrenExterno.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorDrenExterno = ArrayAdapter
				.createFromResource(this,
						R.array.rastaHr2PropInferidasDrenExterno,
						android.R.layout.simple_spinner_item);

		adaptadorDrenExterno
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnDrenExterno.setAdapter(adaptadorDrenExterno);

		// -Spinner Salinidad-//
		spnSalinidad.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorSalinidad = ArrayAdapter
				.createFromResource(this,
						R.array.rastaHr2PropInferidasSaliSodi,
						android.R.layout.simple_spinner_item);

		adaptadorSalinidad
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnSalinidad.setAdapter(adaptadorSalinidad);

		// -Spinner Sodicidad-//
		spnSodicidad.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorSodicidad = ArrayAdapter
				.createFromResource(this,
						R.array.rastaHr2PropInferidasSaliSodi,
						android.R.layout.simple_spinner_item);

		adaptadorSodicidad
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnSodicidad.setAdapter(adaptadorSodicidad);

	}

	/********************************************************************
	 *********************** METODOS DE LOS BOTONES***********************
	 ********************************************************************/

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

		if (arg0 == rdgSueloOrganico) {
			if (arg1 == R.id.IdModRastaHr2PropInferidas1234RdoSueloOrganicoSI) {
				sloOrganico = 1;
			} else if (arg1 == R.id.IdModRastaHr2PropInferidas1234RdoSueloOrganicoNO) {
				sloOrganico = 2;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnHorizonte) {
			horizonte = arg2 + 1;
		}

		if (arg0 == spnMatOrganica) {
			matOrganica = arg2 + 1;
		}

		if (arg0 == spnDrenInterno) {
			drenInterno = arg2 + 1;
		}

		if (arg0 == spnDrenExterno) {
			drenExterno = arg2 + 1;
		}

		if (arg0 == spnSalinidad) {
			salinidad = arg2 + 1;
		}

		if (arg0 == spnSodicidad) {
			sodicidad = arg2 + 1;
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	// --Boton regresar--//
	public void OnModRastaHr2PropInferidas1234BtnRegresar_Click(View button) {
		finish();
	}
	
	//--Boton adicionar--//
	public void OnModRastaHr2PropInferidas1234BtnAdicionar_Click(View button){
		adiCapas();
	}
	
	//--Boton registrar--//
	public void OnModRastaHr2PropInferidas1234_Click(View button){
		adiPropInferidas();
	}

	/*********************************************************************
	 ******************* METODOS DE LA CLASE*******************************
	 *********************************************************************/
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
	
	//--Metodo para adicionar Materia organica--//
	public void adiCapas(){
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// -Registrar pregunta 16-//
			int RMO_ID = horizonte;
			int RMO_RASID = Integer.parseInt(RAS_ID);
			int RMO_RASUMAID = Integer.parseInt(RAS_UMAID);
			int RMO_MATORGANICA=matOrganica;

			ContentValues cv = new ContentValues();
			cv.put("RMO_ID", RMO_ID);
			cv.put("RMO_RASID", RMO_RASID);
			cv.put("RMO_RASUMAID", RMO_RASUMAID);
			cv.put("RMO_MATORGANICA", RMO_MATORGANICA);

			myDatabase.insert("RASMATORGANICA", null, cv);
			
			Mensaje("Exito","Insercion exitosa");

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}
	
	//--Metodo para adicionar Propiedades Inferidas--//
	public void adiPropInferidas(){
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = Integer.parseInt(this.RAS_ID);
			int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
			float RAS_PROFEFECTIVA=Float.parseFloat(edtProfEfectiva.getText().toString());
			int RAS_SLOORGANICO=this.sloOrganico;
			int RAS_DRENINTERNO=this.drenInterno;
			int RAS_DRENEXTERNO=this.drenExterno;
			int RAS_SALINIDAD=this.salinidad;
			int RAS_SODICIDAD=this.sodicidad;

			ContentValues cv = new ContentValues();
			cv.put("RAS_PROFEFECTIVA",RAS_PROFEFECTIVA);
			cv.put("RAS_SLOORGANICO",RAS_SLOORGANICO);
			cv.put("RAS_DRENINTERNO",RAS_DRENINTERNO);
			cv.put("RAS_DRENEXTERNO",RAS_DRENEXTERNO);
			cv.put("RAS_SALINIDAD",RAS_SALINIDAD);
			cv.put("RAS_SODICIDAD",RAS_SODICIDAD);

			String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
					+ RAS_UMAID + "";

			myDatabase.update("RASTA", cv, selected, null);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error ", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}
	
	// --Metodo para limpiar los elementos--//
		public void limpiar() {
			edtProfEfectiva.setText("");
		}
}
