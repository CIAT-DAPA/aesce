package com.AESCE.android;

import java.io.IOException;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ProductorNuevo extends Activity {

	EditText edtCedula;
	EditText edtNombre;
	EditText edtApellido1;
	EditText edtApellido2;
	EditText edtCelular;
	EditText edtFijo;
	EditText edtEmail;
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
		setContentView(R.layout.productornuevo);

		CrearBBDD();

		txvFecha = (TextView) findViewById(R.id.IdProductorNuevoTxvFecha);
		edtCedula = (EditText) findViewById(R.id.IdProductorNuevoEdtCedula);
		edtNombre = (EditText) findViewById(R.id.IdProductorNuevoEdtNombre);
		edtApellido1 = (EditText) findViewById(R.id.IdProductorNuevoEdtApellido1);
		edtApellido2 = (EditText) findViewById(R.id.IdProductorNuevoEdtApellido2);
		edtCelular = (EditText) findViewById(R.id.IdProductorNuevoEdtNumCel);
		edtEmail = (EditText) findViewById(R.id.IdProductorNuevoEdtNombre);
		edtFijo = (EditText) findViewById(R.id.IdProductorNuevoEdtFijo);
		edtEmail = (EditText) findViewById(R.id.IdProductorNuevoEdtEmail);

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();

	}

	/*************************************************************************************
	 ************************************ METODO DE LOS BOTONES****************************
	 *************************************************************************************/

	// Metodo para el boton Registrar
	public void OnProductorNuevoBtnRegistrar_Click(View button) {
		if (edtCedula.getText().toString().equals("")
				|| edtNombre.getText().toString().equals("")
				|| edtApellido1.getText().toString().equals("")) {
			Mensaje("Incompleto", "Alguno de los campos se encuentran vacios");
		} else {
			
			registrarProductor();
		}

	}

	// Metodo para el boton Fecha
	public void OnProductorNuevoBtnFechaRegis_Click(View button) {
		showDialog(DATE_DIALOG_ID);
	}

	// Metodo para el boton regresar
	public void OnProductorBtnRegresar_Click(View button) {
		finish();
	}

	public void finalizar() {
		Intent iProductor = new Intent();
		iProductor.putExtra("returnProductor", "1");
		setResult(RESULT_OK, iProductor);
		super.finish();
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
	public void registrarProductor() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int PRO_ID = Integer.parseInt(edtCedula.getText().toString());
			String PRO_DIA = "" + mDay;
			String PRO_MES = "" + mMonth;
			String PRO_ANIO = "" + mYear;
			String PRO_NOMBRE = edtNombre.getText().toString();
			String PRO_APELLIDO1 = edtApellido1.getText().toString();
			String PRO_APELLIDO2 = edtApellido2.getText().toString();
			String PRO_CELULAR = edtCelular.getText().toString();
			int PRO_FIJO = Integer.parseInt(edtFijo.getText().toString());
			String PRO_EMAIL = edtEmail.getText().toString();

			ContentValues cv = new ContentValues();
			cv.put("PRO_ID", PRO_ID);
			cv.put("PRO_DIA", PRO_DIA);
			cv.put("PRO_MES", PRO_MES);
			cv.put("PRO_ANIO", PRO_ANIO);
			cv.put("PRO_NOMBRE", PRO_NOMBRE);
			cv.put("PRO_APELLIDO1", PRO_APELLIDO1);
			cv.put("PRO_APELLIDO2", PRO_APELLIDO2);
			cv.put("PRO_CELULAR", PRO_CELULAR);
			cv.put("PRO_FIJO", PRO_FIJO);
			cv.put("PRO_EMAIL", PRO_EMAIL);
			/*
			 * String SQL = "INSERT INTO PRODUCTOR " + "VALUES(" + PRO_ID + ",'"
			 * + PRO_DIA + "','" + PRO_MES + "','" + PRO_ANIO + "','" +
			 * PRO_NOMBRE + "','" + PRO_APELLIDO1 + "','" + PRO_APELLIDO2 +
			 * "','" + PRO_CELULAR + "'," + PRO_FIJO + ",'" + PRO_EMAIL + "')";
			 */
			myDatabase.insert("PRODUCTOR", null, cv);

			// Mensaje("Exito","El registro se hizo exitosamente");
			limpiar();

			finalizar();

		} catch (NumberFormatException e) {
			Mensaje("Error", "" + e.getMessage());
		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Limpiar contenido--//
	public void limpiar() {
		edtCedula.setText("");
		txvFecha.setText("");
		edtNombre.setText("");
		edtApellido1.setText("");
		edtApellido2.setText("");
		edtCelular.setText("");
		edtFijo.setText("");
		edtEmail.setText("");

	}
}
