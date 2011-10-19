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

public class modrastahr1preg16to23 extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Elementos del formulario
	RadioGroup rdgPreg16;
	RadioGroup rdgPreg17;
	EditText edtPreg17;
	Spinner spnPreg18;
	RadioGroup rdgPreg19;
	RadioGroup rdgPreg20;
	RadioGroup rdgPreg21;
	RadioGroup rdgPreg22;
	Spinner spnPreg23;

	// Guardar preguntas
	int rdg16 = 1;
	int rdg17 = 1;
	int spn18 = 1;
	int rdg19 = 1;
	int rdg20 = 1;
	int rdg21 = 1;
	int rdg22 = 1;
	int spn23 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1preg16to23);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Instanciar elementos del formulario-//
		rdgPreg16 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg16);
		rdgPreg17 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg17);
		edtPreg17 = (EditText) findViewById(R.id.Idmodrastahr1preg16to23EdtProf17);
		spnPreg18 = (Spinner) findViewById(R.id.Idmodrastahr1preg16to23spn18);
		rdgPreg19 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg19);
		rdgPreg20 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg20);
		rdgPreg21 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg21);
		rdgPreg22 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg16to23Rdg22);
		spnPreg23 = (Spinner) findViewById(R.id.Idmodrastahr1preg16to23spn23);

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();

		// -Asignar los checkedListener a los radioGroup-//
		rdgPreg16.setOnCheckedChangeListener(this);
		rdgPreg17.setOnCheckedChangeListener(this);
		rdgPreg19.setOnCheckedChangeListener(this);
		rdgPreg20.setOnCheckedChangeListener(this);
		rdgPreg21.setOnCheckedChangeListener(this);
		rdgPreg22.setOnCheckedChangeListener(this);

		// -Spinner preg12-//
		spnPreg18.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg18 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg16to123preg18,
						android.R.layout.simple_spinner_item);

		adaptadorPreg18
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPreg18.setAdapter(adaptadorPreg18);

		// -Spinner preg23-//
		spnPreg23.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg23 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg16to123preg23,
						android.R.layout.simple_spinner_item);

		adaptadorPreg23
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPreg23.setAdapter(adaptadorPreg23);
	}

	/*******************************************************************
	 ********************** METODOS DE LOS BOTONES***********************
	 *******************************************************************/

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

		if (arg0 == rdgPreg16) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo16SI) {
				rdg16 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo16NO) {
				rdg16 = 2;
			}
		}

		if (arg0 == rdgPreg17) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo17SI) {
				rdg17 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo17NO) {
				rdg17 = 2;
			}
		}

		if (arg0 == rdgPreg19) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo19SI) {
				rdg19 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo19NO) {
				rdg19 = 2;
			}
		}

		if (arg0 == rdgPreg20) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo20SI) {
				rdg20 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo20NO) {
				rdg20 = 2;
			}
		}

		if (arg0 == rdgPreg21) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo21SI) {
				rdg21 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo21NO) {
				rdg21 = 2;
			}
		}

		if (arg0 == rdgPreg22) {
			if (arg1 == R.id.Idmodrastahr1preg16to23Rdo22SI) {
				rdg22 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg16to23Rdo22NO) {
				rdg22 = 2;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnPreg18) {
			spn18 = arg2 + 1;
		}

		if (arg0 == spnPreg23) {
			spn23 = arg2 + 1;
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	//--Metodo para el boton regresar--//
	public void Onmodrastahr1preg16to23BtnRegresar_Cllick(View button){
		finish();
	}
	
	//--Boton registrar--//
	public void Onmodrastahr1preg16to23BtnRegistrar_Click(View button){
		regPreg16to23();
	}

	/*******************************************************************
	 ********************** METODOS DE LA CLASE**************************
	 *******************************************************************/
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

	// --Metodo para registrar preguntas 10-15--//
	public void regPreg16to23() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		int RSP_PREID;
		int RSP_PRERES;

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// -Registrar pregunta 16-//
			int RSP_RASID = Integer.parseInt(RAS_ID);
			int RSP_RASUMAID = Integer.parseInt(RAS_UMAID);
			RSP_PREID = 16;
			RSP_PRERES = this.rdg16;

			ContentValues cv = new ContentValues();
			cv.put("RSP_RASID", RSP_RASID);
			cv.put("RSP_RASUMAID", RSP_RASUMAID);
			cv.put("RSP_PREID", RSP_PREID);
			cv.put("RSP_PRERES", RSP_PRERES);
			cv.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv);

			// -Registrar preguntas 17-//
			RSP_PREID = 17;
			RSP_PRERES = this.rdg17;
			float RSP_PROF = Float.parseFloat(edtPreg17.getText().toString());

			ContentValues cv1 = new ContentValues();
			cv1.put("RSP_RASID", RSP_RASID);
			cv1.put("RSP_RASUMAID", RSP_RASUMAID);
			cv1.put("RSP_PREID", RSP_PREID);
			cv1.put("RSP_PRERES", RSP_PRERES);
			cv1.put("RSP_PROF", RSP_PROF);

			myDatabase.insert("RASPREGUNTAS", null, cv1);

			// -Registrar preguntas 18-//
			RSP_PREID = 18;
			RSP_PRERES = this.spn18;

			ContentValues cv2 = new ContentValues();
			cv2.put("RSP_RASID", RSP_RASID);
			cv2.put("RSP_RASUMAID", RSP_RASUMAID);
			cv2.put("RSP_PREID", RSP_PREID);
			cv2.put("RSP_PRERES", RSP_PRERES);
			cv2.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv2);

			// -Registrar preguntas 19-//
			RSP_PREID = 19;
			RSP_PRERES = this.rdg19;

			ContentValues cv3 = new ContentValues();
			cv3.put("RSP_RASID", RSP_RASID);
			cv3.put("RSP_RASUMAID", RSP_RASUMAID);
			cv3.put("RSP_PREID", RSP_PREID);
			cv3.put("RSP_PRERES", RSP_PRERES);
			cv3.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv3);

			// -Registrar preguntas 20-//
			RSP_PREID = 20;
			RSP_PRERES = this.rdg20;

			ContentValues cv4 = new ContentValues();
			cv4.put("RSP_RASID", RSP_RASID);
			cv4.put("RSP_RASUMAID", RSP_RASUMAID);
			cv4.put("RSP_PREID", RSP_PREID);
			cv4.put("RSP_PRERES", RSP_PRERES);
			cv4.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv4);

			// -Registrar preguntas 21-//
			RSP_PREID = 21;
			RSP_PRERES = this.rdg21;

			ContentValues cv5 = new ContentValues();
			cv5.put("RSP_RASID", RSP_RASID);
			cv5.put("RSP_RASUMAID", RSP_RASUMAID);
			cv5.put("RSP_PREID", RSP_PREID);
			cv5.put("RSP_PRERES", RSP_PRERES);
			cv5.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv5);

			// -Registrar preguntas 22-//
			RSP_PREID = 22;
			RSP_PRERES = this.rdg22;

			ContentValues cv6 = new ContentValues();
			cv6.put("RSP_RASID", RSP_RASID);
			cv6.put("RSP_RASUMAID", RSP_RASUMAID);
			cv6.put("RSP_PREID", RSP_PREID);
			cv6.put("RSP_PRERES", RSP_PRERES);
			cv6.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv6);
			
			// -Registrar preguntas 23-//
			RSP_PREID = 23;
			RSP_PRERES = this.spn23;

			ContentValues cv7 = new ContentValues();
			cv7.put("RSP_RASID", RSP_RASID);
			cv7.put("RSP_RASUMAID", RSP_RASUMAID);
			cv7.put("RSP_PREID", RSP_PREID);
			cv7.put("RSP_PRERES", RSP_PRERES);
			cv7.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv7);

			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

}
