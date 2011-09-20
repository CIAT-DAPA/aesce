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

public class modRastaHr1CarYObserv3 extends Activity implements
		AdapterView.OnItemSelectedListener {

	// ELementos del formulario
	Spinner spnCapasHorizontes;
	EditText edtEspesor;
	EditText edtColorSeco;
	EditText edtColorHumedo;
	Spinner spnTextura;
	Spinner spnResRompimiento;

	// Variables para el spinner capas u horizontes
	String[] capasHorizontes = new String[] { "1", "2", "3", "4", "5" };
	int CAP_ID;

	// Variables para el spinner textura
	int[] arrayTextId;
	String[] arrayTextDesc;
	int TEX_ID;

	// Variables para el spinner Resistencia al rompimiento
	int[] arrayResId;
	String[] arrayResDesc;
	int RES_ID;

	// Variables del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr1caryobserv3);

		// -instancia eLementos que estan en el formulario-//
		spnCapasHorizontes = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv3SpnCapasHorizontes);
		edtEspesor = (EditText) findViewById(R.id.Idmodrastahr1caryobserv3EdtEspesor);
		edtColorSeco = (EditText) findViewById(R.id.Idmodrastahr1caryobserv3EdtColorSeco);
		edtColorHumedo = (EditText) findViewById(R.id.Idmodrastahr1caryobserv3EdtColorHumedo);
		spnTextura = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv3SpnTextura);
		spnResRompimiento = (Spinner) findViewById(R.id.Idmodrastahr1caryobserv3SpnResRompimiento);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Invocar crear base de datos-//
		CrearBBDD();

		// -Spinner CapasHorizontes-//		
		spnCapasHorizontes.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorCapasHorizontes = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, capasHorizontes);

		adaptadorCapasHorizontes
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnCapasHorizontes.setAdapter(adaptadorCapasHorizontes);

		// -Spinner Textura-//
		llenarSpinnerTextura();

		spnTextura.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorTexturas = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, arrayTextDesc);

		adaptadorTexturas
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnTextura.setAdapter(adaptadorTexturas);

		// -Spinner ResRompimiento-//
		llenarSpinnerResRompimiento();

		spnResRompimiento.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorResRompimiento = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, arrayResDesc);

		adaptadorResRompimiento
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnResRompimiento.setAdapter(adaptadorResRompimiento);
		
	}

	/********************************************************************
	 ************************ METODOS DE LOS BOTONES*********************
	 ********************************************************************/
	// --Metodo para el boton registrar--//
	public void OnModRastaHr1CarYObserv3BtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para el boton adicionar--//
	public void OnModRastaHr1CarYObserv3BtnAdicionar_Click(View button) {
		adicionarCapas();
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnCapasHorizontes) {
			CAP_ID = Integer.parseInt(capasHorizontes[arg2]);
		}

		if (arg0 == spnTextura) {
			this.TEX_ID = arrayTextId[arg2];
		}

		if (arg0 == spnResRompimiento) {
			this.RES_ID = arrayResId[arg2];
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

	// --Metodo para llenar el spinner Textura--//
	public void llenarSpinnerTextura() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("TEXTURAS", new String[] { "TEX_ID",
					"TEX_DESC" }, "", null, null, null, "TEX_DESC ASC");

			int row = c.getCount();
			arrayTextId = new int[row];
			arrayTextDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayTextId[i] = c.getInt(0);
				arrayTextDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para llenar el spinner Resistencia al rompimiento--//
	public void llenarSpinnerResRompimiento() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("RESROMPIMIENTO", new String[] {
					"RES_ID", "RES_DESC" }, "", null, null, null,
					"RES_DESC ASC");

			int row = c.getCount();
			arrayResId = new int[row];
			arrayResDesc = new String[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				arrayResId[i] = c.getInt(0);
				arrayResDesc[i] = c.getString(1);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para adicionar en la tabla capas--//
	public void adicionarCapas() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int CAP_ID = this.CAP_ID;
			int CAP_RASID = Integer.parseInt(RAS_ID);
			int CAP_RASUMAID = Integer.parseInt(RAS_UMAID);
			int CAP_ESPESOR = Integer.parseInt(edtEspesor.getText().toString());
			int CAP_COLORSECO = Integer.parseInt(edtColorSeco.getText()
					.toString());
			int CAP_COLORHUMEDO = Integer.parseInt(edtColorHumedo.getText()
					.toString());
			int CAP_TEXTID = this.TEX_ID;
			int CAP_RESID = this.RES_ID;

			ContentValues cv = new ContentValues();
			cv.put("CAP_ID", CAP_ID);
			cv.put("CAP_RASID", CAP_RASID);
			cv.put("CAP_RASUMAID", CAP_RASUMAID);
			cv.put("CAP_ESPESOR", CAP_ESPESOR);
			cv.put("CAP_COLORSECO", CAP_COLORSECO);
			cv.put("CAP_COLORHUMEDO", CAP_COLORHUMEDO);
			cv.put("CAP_TEXID", CAP_TEXTID);
			cv.put("CAP_RESID", CAP_RESID);

			myDatabase.insert("CAPAS", null, cv);

			limpiar();
			
			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los campos en el formulario--//
	public void limpiar() {
		edtEspesor.setText("");
		edtColorSeco.setText("");
		edtColorHumedo.setText("");
	}

}
