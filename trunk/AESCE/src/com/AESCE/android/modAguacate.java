package com.AESCE.android;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class modAguacate extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Variables para el GPS
	LocationManager locManager;
	LocationListener locListener;

	// Variables que almacenan la fecha
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;

	// Campos que estan en el formulario modaguacate
	TextView txvFecha;
	EditText EdtLoteNo;
	EditText EdtNArboles;
	EditText EdtAreaLote;
	EditText EdtAltitud;
	EditText EdtLatitud;
	EditText EdtLongitud;
	EditText EdtDistSiembra;
	RadioGroup RdgLoteDefinido;
	EditText EdtCulAso;
	RadioGroup RdgAnaQuimico;
	RadioGroup RdgUInjerto;
	EditText EdtNPatron;
	EditText EdtVariedad;
	EditText EdtEdad;
	EditText EdtResiembra;
	EditText EdtProdAnio;
	Spinner SpnProAnioUnidades;

	// Variables para almacenar e insertar en LoteDefinido
	int cuareg = 1;
	int tresbolillo = 1;
	int AnaQuimico = 1;
	int UInjerto = 1;

	// Variables qeu se emplean para almacenar los valores del spinner Unidades
	String[] unidades;
	int[] arrayUniCod;
	int[] arrayUniTipo;
	int UNI_COD;
	int UNI_TIPO;

	// Bundle que se cargan del fomulario menuFinca
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";

	// Variable para almacenar el UMA_DI
	int UMA_ID = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modaguacate);

		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");

		// -Seleccionar el UMA_ID MAXIMO-//
		UMA_ID = selectUmaId();

		Mensaje("Funciona", "" + UMA_ID);

		// -Crear la base de datos-//
		CrearBBDD();

		// Instanciar los campos del formulario modaguacate
		txvFecha = (TextView) findViewById(R.id.IdModAguacateTxvFecha);
		EdtLoteNo = (EditText) findViewById(R.id.IdModAguacateEdtLoteNo);
		EdtNArboles = (EditText) findViewById(R.id.IdModAguacateEdtNArboles);
		EdtAreaLote = (EditText) findViewById(R.id.IdModAguacateEdtAreaLote);
		EdtAltitud = (EditText) findViewById(R.id.IdModAguacateEdtAltitud);
		EdtLatitud = (EditText) findViewById(R.id.IdModAguacateEdtLatitud);
		EdtLongitud = (EditText) findViewById(R.id.IdModAguacateEdtLongitud);
		EdtDistSiembra = (EditText) findViewById(R.id.IdModAguacateEdtDistSiembra);
		RdgLoteDefinido = (RadioGroup) findViewById(R.id.IdModAguacateRdgLoteDefinido);
		EdtCulAso = (EditText) findViewById(R.id.IdModAguacateEdtCulAso);
		RdgAnaQuimico = (RadioGroup) findViewById(R.id.IdModAguacateRdgAnaQuimico);
		RdgUInjerto = (RadioGroup) findViewById(R.id.IdModAguacateRdgUInjerto);
		EdtNPatron = (EditText) findViewById(R.id.IdModAguacateEdtNPatron);
		EdtVariedad = (EditText) findViewById(R.id.IdModAguacateEdtVariedad);
		EdtEdad = (EditText) findViewById(R.id.IdModAguacateEdtEdad);
		EdtResiembra = (EditText) findViewById(R.id.IdModAguacateEdtResiembra);
		EdtProdAnio = (EditText) findViewById(R.id.IdModAguacateEdtProAnio);
		SpnProAnioUnidades = (Spinner) findViewById(R.id.IdModAguacateSpnProAnioUnidades);

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();

		// Invocar metodo del GPS
		comenzarLocalizacion();

		// Asignar los CheckedListener a los RadioGroup
		RdgAnaQuimico.setOnCheckedChangeListener(this);
		RdgLoteDefinido.setOnCheckedChangeListener(this);
		RdgUInjerto.setOnCheckedChangeListener(this);

		// -Llenar y adaptadores para las unidades-//
		llenarSpinnerUnidades();

		SpnProAnioUnidades.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorUnidades = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, unidades);

		adaptadorUnidades
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		SpnProAnioUnidades.setAdapter(adaptadorUnidades);
	}

	// --Metodo para el boton seleccionar fecha--//
	public void OnModAguacateBtnFechaRegis_Click(View button) {
		showDialog(DATE_DIALOG_ID);
	}

	// --Metodo para el boton registrar--//
	public void OnModAguacateBtnRegistrar_Click(View button) {
		if (EdtLoteNo.getText().toString().equals("")
				|| EdtCulAso.getText().toString().equals("")
				|| EdtLatitud.getText().toString().equals("")
				|| EdtLongitud.getText().toString().equals("")
				|| EdtAltitud.getText().toString().equals("")
				|| EdtNArboles.getText().toString().equals("")
				|| EdtEdad.getText().toString().equals("")
				|| EdtResiembra.getText().toString().equals("")
				|| EdtProdAnio.getText().toString().equals("")) {
			Mensaje("Error", "Algunos de los campos se encuentra vacíos ");
		} else {
			registarModAguacate();
		}
	}

	// --Metodo para el boton regresar--//
	public void OnModAguacateBtnRegresar_Click(View button) {
		finish();
	}

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub

		if (arg0 == RdgLoteDefinido) {
			if (arg1 == R.id.IdModCitricosRdbCuaReg) {
				cuareg = 1;
				tresbolillo = 0;
			} else if (arg1 == R.id.IdModCitricosRdbTresbolillo) {
				tresbolillo = 1;
				cuareg = 0;
			}
		}

		if (arg0 == RdgAnaQuimico) {
			if (arg1 == R.id.IdModCitricosRdbAnaQuimicoSI) {
				AnaQuimico = 1;
			} else if (arg1 == R.id.IdModCitricosRdbAnaQuimicosNO) {
				AnaQuimico = 2;
			}
		}

		if (arg0 == RdgUInjerto) {
			if (arg1 == R.id.IdModCitricosRdbUInjertoSI) {
				UInjerto = 1;
			} else if (arg1 == R.id.IdModCitricosRdbUInjertoNO) {
				UInjerto = 2;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == SpnProAnioUnidades) {
			UNI_COD = arrayUniCod[arg2];
			UNI_TIPO = arrayUniTipo[arg2];
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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

	// ------------------------------------------------------------------------
	// -----------------------------------------GPS----------------------------
	// ------------------------------------------------------------------------
	private void comenzarLocalizacion() {

		// Obtenemos una referencia al LocationManager
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Obtenemos la ultima posicion Conocida
		Location loc = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Mostramos la ultima posicion conocida
		mostrarPosicion(loc);

		// Nos registramos para recibir actualizaciones
		locListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				EdtAltitud.setText("Altitud: {GPS StatusChanged}");
				EdtLatitud.setText("Latitud: {GPS StatusChanged}");
				EdtLongitud.setText("Longitud: {GPS StatusChanged}");
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				EdtAltitud.setText("Altitud: {GPS Cargando...}");
				EdtLatitud.setText("Latitud: {GPS Cargando...}");
				EdtLongitud.setText("Longitud: {GPS Cargando...}");
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				String mensaje = "Por favor active su GPS para un correcto funcionamiento del sistema";
				String titulo = "Advertencia";
				Mensaje(titulo, mensaje);
				EdtAltitud.setText("Altitud: {GPS Desactivado}");
				EdtLatitud.setText("Latitud: {GPS Desactivado}");
				EdtLongitud.setText("Longitud: {GPS Desactivado}");
			}

			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				EdtAltitud.setText(String.valueOf(location.getAltitude()));
				EdtLatitud.setText(String.valueOf(location.getLatitude()));
				EdtLongitud.setText(String.valueOf(location.getLongitude()));

				Log.i("",
						String.valueOf("Altitud: " + location.getAltitude()
								+ " - " + "Latitud: " + location.getLatitude()
								+ " - " + "Longitud: "
								+ location.getLongitude()));
			}
		};

		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);

	}

	public void mostrarPosicion(Location loc) {
		if (loc != null) {
			EdtAltitud.setText(String.valueOf(loc.getAltitude()));
			EdtLatitud.setText(String.valueOf(loc.getLatitude()));
			EdtLongitud.setText(String.valueOf(loc.getLongitude()));

			Log.i("",
					String.valueOf(loc.getLatitude() + " - "
							+ String.valueOf(loc.getLongitude())));
		} else {
			EdtAltitud.setText("Altitud: {sin datos}");
			EdtLatitud.setText("Latitud: {sin datos}");
			EdtLongitud.setText("Longitud: {sin datos}");
		}

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

	// --Metodo para llenar el spinner unidades--//
	public void llenarSpinnerUnidades() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "UNI_TIPO=2";

			Cursor c = myDatabase.query("UNIDADES", new String[] { "UNI_COD",
					"UNI_TIPO", "UNI_DESC" }, selected, null, null, null,
					"UNI_DESC ASC");

			int row = c.getCount();
			unidades = new String[row];
			arrayUniCod = new int[row];
			arrayUniTipo = new int[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				unidades[i] = c.getString(2);
				arrayUniTipo[i] = c.getInt(1);
				arrayUniCod[i] = c.getInt(0);
				c.moveToNext();
				i++;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Insertar los campos en la base de datos--//
	public void registarModAguacate() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// Tabla UManejo
			int UMA_ID = this.UMA_ID;
			int UMA_FINID = Integer.parseInt(FIN_ID);
			int UMA_LOTENRO = Integer.parseInt(EdtLoteNo.getText().toString());
			int UMA_PRUID = 2;
			String UMA_DIA = "" + mDay;
			String UMA_MES = "" + mMonth;
			String UMA_ANIO = "" + mYear;
			float UMA_LATITUD = Float.parseFloat(EdtLatitud.getText()
					.toString());
			float UMA_LONGITUD = Float.parseFloat(EdtLongitud.getText()
					.toString());
			float UMA_ALTITUD = Float.parseFloat(EdtAltitud.getText()
					.toString());
			int UMA_NARBOLES = Integer.parseInt(EdtNArboles.getText()
					.toString());
			float UMA_AREA = Float.parseFloat(EdtAreaLote.getText().toString());
			String UMA_TRAZADO = "null";
			int UMA_TASUELO = AnaQuimico;
			int UMA_UINJERTO = UInjerto;
			String UMA_NPATRON = EdtNPatron.getText().toString();
			String UMA_VARIEDAD = EdtVariedad.getText().toString();
			String UMA_EDAD = EdtEdad.getText().toString();
			float UMA_RESIEMBRA = Float.parseFloat(EdtResiembra.getText()
					.toString());
			float UMA_PRODANIO = Float.parseFloat(EdtProdAnio.getText()
					.toString());
			int UMA_UNICOD = UNI_COD;
			int UMA_UNITIPO = UNI_TIPO;
			int UMA_PERINT = 0;
			int UMA_INVITRO = 0;
			float UMA_PENDIENTE = 0;
			float UMA_CAPAS = 0;
			float UMA_PROEFEC = 0;
			int UMA_PH = 0;

			// Tabla LoteDefinido
			int LTD_UMAID = this.UMA_ID;
			String LTD_DISTSIEMBRA = EdtDistSiembra.getText().toString();
			int LTD_CUAREG = cuareg;
			int LTD_TRESBOLILLO = tresbolillo;

			// Tabla CultivoAsociado
			int CUA_FINID = Integer.parseInt(FIN_ID);
			int CUA_UMAID = this.UMA_ID;
			int CUA_PRUCOD = 0;
			String CUA_DESC = EdtCulAso.getText().toString();

			// Tabla Umanejo
			ContentValues cv = new ContentValues();
			cv.put("UMA_ID", UMA_ID);
			cv.put("UMA_LOTENRO", UMA_LOTENRO);
			cv.put("UMA_FINID", UMA_FINID);
			cv.put("UMA_PRUID", UMA_PRUID);
			cv.put("UMA_DIA", UMA_DIA);
			cv.put("UMA_MES", UMA_MES);
			cv.put("UMA_ANIO", UMA_ANIO);
			cv.put("UMA_LATITUD", UMA_LATITUD);
			cv.put("UMA_LONGITUD", UMA_LONGITUD);
			cv.put("UMA_ALTITUD", UMA_ALTITUD);
			cv.put("UMA_NARBOLES", UMA_NARBOLES);
			cv.put("UMA_AREA", UMA_AREA);
			cv.put("UMA_TRAZADO", UMA_TRAZADO);
			cv.put("UMA_TASUELO", UMA_TASUELO);
			cv.put("UMA_UINJERTO", UMA_UINJERTO);
			cv.put("UMA_NPATRON", UMA_NPATRON);
			cv.put("UMA_VARIEDAD", UMA_VARIEDAD);
			cv.put("UMA_EDAD", UMA_EDAD);
			cv.put("UMA_RESIEMBRA", UMA_RESIEMBRA);
			cv.put("UMA_PRODANIO", UMA_PRODANIO);
			cv.put("UMA_UNICOD", UMA_UNICOD);
			cv.put("UMA_UNITIPO", UMA_UNITIPO);
			cv.put("UMA_PERINT", UMA_PERINT);
			cv.put("UMA_INVITRO", UMA_INVITRO);
			cv.put("UMA_PENDIENTE", UMA_PENDIENTE);
			cv.put("UMA_CAPAS", UMA_CAPAS);
			cv.put("UMA_PROEFEC", UMA_PROEFEC);
			cv.put("UMA_PH", UMA_PH);

			myDatabase.insert("UMANEJO", null, cv);

			// Tabla LoteDefinido
			ContentValues cv2 = new ContentValues();
			cv2.put("LTD_UMAID", LTD_UMAID);
			cv2.put("LTD_DISTSIEMBRA", LTD_DISTSIEMBRA);
			cv2.put("LTD_CUAREG", LTD_CUAREG);
			cv2.put("LTD_TRESBOLILLO", LTD_TRESBOLILLO);

			myDatabase.insert("LOTEDEFINIDO", null, cv2);

			// Tabla CulAsociado
			ContentValues cv3 = new ContentValues();
			cv3.put("CUA_FINID", CUA_FINID);
			cv3.put("CUA_UMAID", CUA_UMAID);
			cv3.put("CUA_PRUCOD", CUA_PRUCOD);
			cv3.put("CUA_CULDESC", CUA_DESC);

			myDatabase.insert("CULASOCIADO", null, cv3);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar las cajas de texto--//
	public void limpiar() {
		EdtAltitud.setText("");
		EdtAreaLote.setText("");
		EdtCulAso.setText("");
		EdtDistSiembra.setText("");
		EdtEdad.setText("");
		EdtLatitud.setText("");
		EdtLongitud.setText("");
		EdtLoteNo.setText("");
		EdtNArboles.setText("");
		EdtNPatron.setText("");
		EdtProdAnio.setText("");
		EdtResiembra.setText("");
		EdtVariedad.setText("");
	}

	// --Determinar el UMA_ID--//
	public int selectUmaId() {
		int UId = 0;
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("UMANEJO",
					new String[] { "MAX(UMA_ID)+1" }, "", null, null, null,
					null);

			c.moveToFirst();

			if (c.isAfterLast() == false) {
				UId = c.getInt(0);
			} else {
				UId = 1;
			}

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		} finally {
			bdH.close();
		}
		return UId;
	}

}
