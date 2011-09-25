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
import android.widget.RadioGroup;
import android.widget.Spinner;

public class modrastahr1preg10to15 extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	// Elementos que estan en la interfaz
	RadioGroup rdgPreg10;
	RadioGroup rdgPreg11;
	Spinner spnPreg12;
	Spinner spnPreg13;
	Spinner spnPreg14;
	Spinner spnPreg15;

	// Variables para los spinner 12,13,14,15
	int preg12=1;
	int preg13=1;
	int preg14=1;
	int preg15=1;

	// Variables para el radioGroup10
	int rdg10 = 1;

	// Variables para el radioGroup11
	int rdg11 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1preg10to15);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Instancia elementos que estan en el formulario-//
		rdgPreg10 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg10to15Rdg10);
		rdgPreg11 = (RadioGroup) findViewById(R.id.Idmodrastahr1preg10to15Rdg11);
		spnPreg12 = (Spinner) findViewById(R.id.Idmodrastahr1preg10to15spn12);
		spnPreg13 = (Spinner) findViewById(R.id.Idmodrastahr1preg10to15spn13);
		spnPreg14 = (Spinner) findViewById(R.id.Idmodrastahr1preg10to15spn14);
		spnPreg15 = (Spinner) findViewById(R.id.Idmodrastahr1preg10to15spn15);

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();

		// -Asignar los checkedListener a los radioGroup-//
		rdgPreg10.setOnCheckedChangeListener(this);
		rdgPreg11.setOnCheckedChangeListener(this);

		// -Spinner preg12-//
		spnPreg12.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg12 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg10to15,
						android.R.layout.simple_spinner_item);

		adaptadorPreg12
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnPreg12.setAdapter(adaptadorPreg12);

		// -Spinner preg13-//
		spnPreg13.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg13 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg10to15two,
						android.R.layout.simple_spinner_item);

		adaptadorPreg13
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPreg13.setAdapter(adaptadorPreg13);

		// -Spinner preg14-//
		spnPreg14.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg14 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg10to15,
						android.R.layout.simple_spinner_item);

		adaptadorPreg14
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPreg14.setAdapter(adaptadorPreg14);

		// -Spinner preg15-//
		spnPreg15.setOnItemSelectedListener(this);

		ArrayAdapter<CharSequence> adaptadorPreg15 = ArrayAdapter
				.createFromResource(this, R.array.rastaHr1Preg10to15,
						android.R.layout.simple_spinner_item);

		adaptadorPreg15
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnPreg15.setAdapter(adaptadorPreg15);

	}

	/*****************************************************************************
	 ***************** METODOS DE LOS BOTONES*************************************
	 *****************************************************************************/
	// --Metodo para el boton regresar--//
	public void Onmodrastahr1preg10to15BtnRegresar_Cllick(View button) {
		finish();
	}

	// --Metodo para el boton registrar--//
	public void Onmodrastahr1preg10to15BtnRegistrar_Click(View button) {
		regPreg10to15();
	}

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == rdgPreg10) {
			if (arg1 == R.id.Idmodrastahr1preg10to15Rdo10SI) {
				rdg10 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg10to15Rdo10NO) {
				rdg10 = 2;
			}
		}

		if (arg0 == rdgPreg11) {
			if (arg1 == R.id.Idmodrastahr1preg10to15Rdo11SI) {
				rdg11 = 1;
			} else if (arg1 == R.id.Idmodrastahr1preg10to15Rdo11NO) {
				rdg11 = 2;
			}
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnPreg12) {
			preg12 = arg2+1;
		}
		if (arg0 == spnPreg13) {
			preg13 = arg2+1;
		}
		if (arg0 == spnPreg14) {
			preg14 = arg2+1;
		}

		if (arg0 == spnPreg15) {
			preg15 = arg2+1;
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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

	// --Metodo para registrar preguntas 10-15--//
	public void regPreg10to15() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		int RSP_PREID;
		int RSP_PRERES;

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// -Registrar preguntas 10-//
			int RSP_RASID = Integer.parseInt(RAS_ID);
			int RSP_RASUMAID = Integer.parseInt(RAS_UMAID);
			RSP_PREID = 10;
			RSP_PRERES = this.rdg10;

			ContentValues cv = new ContentValues();
			cv.put("RSP_RASID", RSP_RASID);
			cv.put("RSP_RASUMAID", RSP_RASUMAID);
			cv.put("RSP_PREID", RSP_PREID);
			cv.put("RSP_PRERES", RSP_PRERES);
			cv.put("RSP_PROF", 0);
			
			myDatabase.insert("RASPREGUNTAS", null, cv);

			// -Registrar preguntas 11-//
			RSP_PREID = 11;
			RSP_PRERES = this.rdg11;

			ContentValues cv1 = new ContentValues();
			cv1.put("RSP_RASID", RSP_RASID);
			cv1.put("RSP_RASUMAID", RSP_RASUMAID);
			cv1.put("RSP_PREID", RSP_PREID);
			cv1.put("RSP_PRERES", RSP_PRERES);
			cv1.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv1);

			// -Registrar preguntas 12-//
			RSP_PREID = 12;
			RSP_PRERES = this.preg12;

			ContentValues cv2 = new ContentValues();
			cv2.put("RSP_RASID", RSP_RASID);
			cv2.put("RSP_RASUMAID", RSP_RASUMAID);
			cv2.put("RSP_PREID", RSP_PREID);
			cv2.put("RSP_PRERES", RSP_PRERES);
			cv2.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv2);

			// -Registrar preguntas 13-//
			RSP_PREID = 13;
			RSP_PRERES = this.preg13;

			ContentValues cv3 = new ContentValues();
			cv3.put("RSP_RASID", RSP_RASID);
			cv3.put("RSP_RASUMAID", RSP_RASUMAID);
			cv3.put("RSP_PREID", RSP_PREID);
			cv3.put("RSP_PRERES", RSP_PRERES);
			cv3.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv3);

			// -Registrar preguntas 14-//
			RSP_PREID = 14;
			RSP_PRERES = this.preg14;

			ContentValues cv4 = new ContentValues();
			cv4.put("RSP_RASID", RSP_RASID);
			cv4.put("RSP_RASUMAID", RSP_RASUMAID);
			cv4.put("RSP_PREID", RSP_PREID);
			cv4.put("RSP_PRERES", RSP_PRERES);
			cv4.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv4);

			// -Registrar preguntas 15-//
			RSP_PREID = 15;
			RSP_PRERES = this.preg15;

			ContentValues cv5 = new ContentValues();
			cv5.put("RSP_RASID", RSP_RASID);
			cv5.put("RSP_RASUMAID", RSP_RASUMAID);
			cv5.put("RSP_PREID", RSP_PREID);
			cv5.put("RSP_PRERES", RSP_PRERES);
			cv5.put("RSP_PROF", 0);

			myDatabase.insert("RASPREGUNTAS", null, cv5);
			
			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

}
