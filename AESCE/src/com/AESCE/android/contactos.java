package com.AESCE.android;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class contactos extends Activity {

	String PRO_ID = "";
	String FIN_ID = "";

	EditText edtCedula;
	EditText edtFinca;
	EditText edtNomRepAESCE;
	EditText edtTelRepAESCE;
	EditText edtNomRepASO;
	EditText edtTelRepASO;
	EditText edtNomFunPub;
	EditText edtTelFunPub;
	EditText edtNomAgriLider;
	EditText edtTelAgriLider;
	TextView txvFecha;

	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactos);

		edtCedula = (EditText) findViewById(R.id.IdContactosEdtCedula);
		edtFinca = (EditText) findViewById(R.id.IdContactosEdtFinca);
		edtNomRepAESCE = (EditText) findViewById(R.id.IdContactosEdtNomRepAESCE);
		edtTelRepAESCE = (EditText) findViewById(R.id.IdContactosEdtTelRepAESCE);
		edtNomRepASO = (EditText) findViewById(R.id.IdContactosEdtNomRepASO);
		edtTelRepASO = (EditText) findViewById(R.id.IdContactosEdtTelRepASO);
		edtNomFunPub = (EditText) findViewById(R.id.IdContactosEdtNomFunPub);
		edtTelFunPub = (EditText) findViewById(R.id.IdContactosEdtTelFunPub);
		edtNomAgriLider = (EditText) findViewById(R.id.IdContactosEdtNomAgriLider);
		edtTelAgriLider = (EditText) findViewById(R.id.IdContactosEdtTelAgriLider);
		txvFecha = (TextView) findViewById(R.id.IdContactosTxvFecha);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");

		edtCedula.setText(PRO_ID);
		edtFinca.setText(FIN_ID);

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();

	}

	/***************************************************************************************
	 *************************************** METODOS PARA LOS BOTONES************************
	 ***************************************************************************************/
	// --Metodo para el boton fecha--//
	public void OnContactosBtnFechaRegis_Click(View button) {
		showDialog(DATE_DIALOG_ID);
	}

	// --Metodo para el boton registrar--//
	public void OnCntactosBtnRegistrar_Click(View button) {
		registrarContacto();
	}

	// --Metodo para el boton regresar--//
	public void OnContactosBtnRegresar_Click(View button) {
		finish();
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

	// -- METODO PARA MOSTRAR EL DATEPICKER--//

	// Actualizar la fecha que muestra en txvFecha
	private void updateDisplay() {
		String mensaje = ""
				+ new StringBuilder().append(mMonth + 1).append("-")
						.append(mDay).append("-").append(mYear).append(" ");

		// String mensaje=""+fmtDateAndTime.format(dateAndTime.getTime()));;

		txvFecha.setText("" + mensaje);

	}

	// the callback received when the user "sets" the date in the dialog
	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
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

	// --Metodo para Regristar el productor--//
	public void registrarContacto() {
		CrearBBDD();
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int CON_FINID = Integer.parseInt(edtFinca.getText().toString());
			String CON_DIA = "" + mDay;
			String CON_MES = "" + mMonth;
			String CON_ANIO = "" + mYear;
			String CON_REPAESCE = edtNomRepAESCE.getText().toString();
			String CON_TELREPAESCE = edtTelRepAESCE.getText().toString();
			String CON_REPASO = edtNomRepASO.getText().toString();
			String CON_TELREPASO = edtTelRepASO.getText().toString();
			String CON_REPFUN = edtNomFunPub.getText().toString();
			String CON_TELREPFUN = edtTelFunPub.getText().toString();
			String CON_LIDER = edtNomAgriLider.getText().toString();
			String CON_TELLIDER = edtTelAgriLider.getText().toString();

			ContentValues cv = new ContentValues();
			// cv.put("CON_ID", PRO_ID);
			cv.put("CON_FINID", CON_FINID);
			cv.put("CON_DIA", CON_DIA);
			cv.put("CON_MES", CON_MES);
			cv.put("CON_ANIO", CON_ANIO);
			cv.put("CON_REPAESCE", CON_REPAESCE);
			cv.put("CON_TELREPAESCE", CON_TELREPAESCE);
			cv.put("CON_REPASO", CON_REPASO);
			cv.put("CON_TELREPASO", CON_TELREPASO);
			cv.put("CON_REPFUN", CON_REPFUN);
			cv.put("CON_TELREPFUN", CON_TELREPFUN);
			cv.put("CON_LIDER", CON_LIDER);
			cv.put("CON_TELLIDER", CON_TELLIDER);
			/*
			 * String SQL = "INSERT INTO PRODUCTOR " + "VALUES(" + PRO_ID + ",'"
			 * + PRO_DIA + "','" + PRO_MES + "','" + PRO_ANIO + "','" +
			 * PRO_NOMBRE + "','" + PRO_APELLIDO1 + "','" + PRO_APELLIDO2 +
			 * "','" + PRO_CELULAR + "'," + PRO_FIJO + ",'" + PRO_EMAIL + "')";
			 */
			myDatabase.insert("CONTACTOS", null, cv);
			myDatabase.close();

			// Mensaje("Exito","El registro se hizo exitosamente");
			limpiar();

			finish();

		} catch (NumberFormatException e) {
			Mensaje("Error", "" + e.getMessage());
		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		}
	}

	// --Limpiar contenido--//
	public void limpiar() {
		edtCedula.setText("");
		txvFecha.setText("");
		edtFinca.setText("");
		edtNomAgriLider.setText("");
		edtNomFunPub.setText("");
		edtNomRepAESCE.setText("");
		edtNomRepASO.setText("");
		edtTelAgriLider.setText("");
		edtTelFunPub.setText("");
		edtTelRepAESCE.setText("");
		edtTelRepASO.setText("");
	}

}
