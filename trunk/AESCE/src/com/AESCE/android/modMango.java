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
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class modMango extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	// Variables para el GPS
	LocationManager locManager;
	LocationListener locListener;

	// Variables que almacenan la fecha
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;

	// Bundle que se cargan del fomulario menuFinca
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";

	// Elementos del formulario
	TextView txvFecha;
	Button btnFechaRegis;
	EditText edtLoteNo;
	EditText edtNArboles;
	EditText edtAreaLote;
	EditText edtAltitud;
	EditText edtLatitud;
	EditText edtLongitud;
	RadioGroup rdgDispersosLtdDefinido;
	EditText edtOtraForma;
	EditText edtDistSiembra;
	RadioGroup rdgLoteDefinido;
	EditText edtCulAso;
	RadioGroup rdgDispersos;
	RadioGroup rdgAnaQuimico;
	RadioGroup rdgUInjerto;
	EditText edtNPatron;
	EditText edtVariedad;
	EditText edtEdad;
	EditText edtRSiembra;
	EditText edtProAnio;
	Spinner spnProAnioUnidades;
	LinearLayout linearLayout01;
	LinearLayout linearLayout02;

	// variables para los radioGroup
	int dispersosLtdDefinido = 1;
	int LTD_CUAREG = 1;
	int LTD_TRESBOLILLO;
	int dispersos = 1;
	int anaQuimico = 1;
	int uInjerto = 1;
	int DIS_CERCA = 1;
	int DIS_VIA = 0;
	int DIS_RIO = 0;
	int DIS_VIVIENDA = 0;
	int DIS_POTRERO = 0;
	String DIS_OTRA = "";

	// Variables que se emplean para almacenar los valores del spinner Unidades
	String[] unidades;
	int[] arrayUniCod;
	int[] arrayUniTipo;
	int UNI_COD;
	int UNI_TIPO;

	// Variable para almacenar el UMA_ID
	int UMA_ID = 1;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modmango);

		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");

		// -Seleccionar el UMA_ID MAXIMO-//
		UMA_ID = selectUmaId();

		// -Crear la base de datos-//
		CrearBBDD();

		// Instanciar los campos del formulario modcitricos
		txvFecha = (TextView) findViewById(R.id.IdModMangoTxvFecha);
		btnFechaRegis = (Button) findViewById(R.id.IdModMangoBtnFechaRegis);
		edtLoteNo = (EditText) findViewById(R.id.IdModMangoEdtLoteNo);
		edtNArboles = (EditText) findViewById(R.id.IdModMangoEdtNArboles);
		edtAreaLote = (EditText) findViewById(R.id.IdModMangoEdtAreaLote);
		edtAltitud = (EditText) findViewById(R.id.IdModMangoEdtAltitud);
		edtLatitud = (EditText) findViewById(R.id.IdModMangoEdtLatitud);
		edtLongitud = (EditText) findViewById(R.id.IdModMangoEdtLongitud);
		rdgDispersosLtdDefinido = (RadioGroup) findViewById(R.id.IdModMangoRdgDisPersosLtdDefinido);
		edtOtraForma = (EditText) findViewById(R.id.IdModMangoEdtOtraForma);
		edtDistSiembra = (EditText) findViewById(R.id.IdModMangoEdtDistSiembra);
		rdgLoteDefinido = (RadioGroup) findViewById(R.id.IdModMangoRdgLoteDefinido);
		edtCulAso = (EditText) findViewById(R.id.IdModMangoEdtCulAso);
		rdgDispersos = (RadioGroup) findViewById(R.id.IdModMangoRdgDispersos);
		rdgAnaQuimico = (RadioGroup) findViewById(R.id.IdModMangoRdgAnaQuimico);
		rdgUInjerto = (RadioGroup) findViewById(R.id.IdModMangoRdgUInjerto);
		edtNPatron = (EditText) findViewById(R.id.IdModMangoEdtNPatron);
		edtVariedad = (EditText) findViewById(R.id.IdModMangoEdtVariedad);
		edtEdad = (EditText) findViewById(R.id.IdModMangoEdtEdad);
		edtRSiembra = (EditText) findViewById(R.id.IdModMangoEdtResiembra);
		edtProAnio = (EditText) findViewById(R.id.IdModMangoEdtProAnio);
		spnProAnioUnidades = (Spinner) findViewById(R.id.IdModMangoSpnProAnioUnidades);

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		linearLayout01=(LinearLayout)findViewById(R.id.LinearLayout08);
		linearLayout02=(LinearLayout)findViewById(R.id.LinearLayout25);
		

		updateDisplay();

		// Invocar metodo del GPS
		comenzarLocalizacion();

		// Asignar los CheckedListener a los RadioGroup
		rdgAnaQuimico.setOnCheckedChangeListener(this);
		rdgDispersos.setOnCheckedChangeListener(this);
		rdgDispersosLtdDefinido.setOnCheckedChangeListener(this);
		rdgLoteDefinido.setOnCheckedChangeListener(this);
		rdgUInjerto.setOnCheckedChangeListener(this);
		

		// -Llenar y adaptadores para las unidades-//
		llenarSpinnerUnidades();

		spnProAnioUnidades.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorUnidades = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, unidades);

		adaptadorUnidades
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnProAnioUnidades.setAdapter(adaptadorUnidades);
		
		edtOtraForma.setEnabled(false);
		findViewById(R.id.IdModMangoRdoCerca).setEnabled(false);
		findViewById(R.id.IdModMangoRdoVia).setEnabled(false);
		findViewById(R.id.IdModMangoRdoRio).setEnabled(false);
		findViewById(R.id.IdModMangoRdoVivienda).setEnabled(false);
		findViewById(R.id.IdModMangoRdoPotrero).setEnabled(false);
		findViewById(R.id.IdModMangoRdoCuaReg).setEnabled(false);
		findViewById(R.id.IdModMangoRdoTresbolillo).setEnabled(false);
		edtDistSiembra.setEnabled(false);
		edtCulAso.setEnabled(false);
	}

	/********************************************************************
	 ********************* METODOS DE LOS BOTONES*************************
	 ********************************************************************/

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == rdgAnaQuimico) {
			if (arg1 == R.id.IdModMangoRdoAnaQuimicoSI) {
				anaQuimico = 1;
			} else if (arg1 == R.id.IdModMangoRdoAnaQuimicosNO) {
				anaQuimico = 0;
			}
		}

		if (arg0 == rdgDispersos) {
			if (arg1 == R.id.IdModMangoRdoCerca) {
				this.DIS_CERCA = 1;
				this.DIS_VIA = 0;
				this.DIS_RIO = 0;
				this.DIS_VIVIENDA = 0;
				this.DIS_POTRERO = 0;
			} else if (arg1 == R.id.IdModMangoRdoVia) {
				this.DIS_CERCA = 0;
				this.DIS_VIA = 1;
				this.DIS_RIO = 0;
				this.DIS_VIVIENDA = 0;
				this.DIS_POTRERO = 0;
			} else if (arg1 == R.id.IdModMangoRdoRio) {
				this.DIS_CERCA = 0;
				this.DIS_VIA = 0;
				this.DIS_RIO = 1;
				this.DIS_VIVIENDA = 0;
				this.DIS_POTRERO = 0;
			} else if (arg1 == R.id.IdModMangoRdoVivienda) {
				this.DIS_CERCA = 0;
				this.DIS_VIA = 0;
				this.DIS_RIO = 0;
				this.DIS_VIVIENDA = 1;
				this.DIS_POTRERO = 0;
			} else if (arg1 == R.id.IdModMangoRdoPotrero) {
				this.DIS_CERCA = 0;
				this.DIS_VIA = 0;
				this.DIS_RIO = 0;
				this.DIS_VIVIENDA = 0;
				this.DIS_POTRERO = 1;
			}
		}

		if (arg0 == rdgDispersosLtdDefinido) {
			if (arg1 == R.id.IdModMangoRdoDispersos) {
				dispersosLtdDefinido = 1;
				edtOtraForma.setEnabled(true);
				edtDistSiembra.setEnabled(false);
				findViewById(R.id.IdModMangoRdoCuaReg).setEnabled(false);
				findViewById(R.id.IdModMangoRdoTresbolillo).setEnabled(false);
				findViewById(R.id.IdModMangoRdoCerca).setEnabled(true);
				findViewById(R.id.IdModMangoRdoVia).setEnabled(true);
				findViewById(R.id.IdModMangoRdoRio).setEnabled(true);
				findViewById(R.id.IdModMangoRdoVivienda).setEnabled(true);
				findViewById(R.id.IdModMangoRdoPotrero).setEnabled(true);
				edtCulAso.setEnabled(false);
				
			} else if (arg1 == R.id.IdModMangoRdoLtdDefinido) {
				dispersosLtdDefinido = 0;
				edtOtraForma.setEnabled(false);
				findViewById(R.id.IdModMangoRdoCerca).setEnabled(false);
				findViewById(R.id.IdModMangoRdoVia).setEnabled(false);
				findViewById(R.id.IdModMangoRdoRio).setEnabled(false);
				findViewById(R.id.IdModMangoRdoVivienda).setEnabled(false);
				findViewById(R.id.IdModMangoRdoPotrero).setEnabled(false);
				findViewById(R.id.IdModMangoRdoCuaReg).setEnabled(true);
				findViewById(R.id.IdModMangoRdoTresbolillo).setEnabled(true);
				edtDistSiembra.setEnabled(true);
				edtCulAso.setEnabled(true);
				
			}
		}

		if (arg0 == rdgLoteDefinido) {
			if (arg1 == R.id.IdModMangoRdoCuaReg) {
				this.LTD_CUAREG = 1;
				this.LTD_TRESBOLILLO = 0;
			} else if (arg1 == R.id.IdModMangoRdoTresbolillo) {
				this.LTD_TRESBOLILLO = 1;
				this.LTD_CUAREG = 0;
			}
		}

		if (arg0 == rdgUInjerto) {
			if (arg1 == R.id.IdModMangoRdoUInjertoSI) {
				uInjerto = 1;
			} else if (arg1 == R.id.IdModMangoRdoUInjertoNO) {
				uInjerto = 0;
			}
		}

	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if (arg0 == spnProAnioUnidades) {
			UNI_COD = arrayUniCod[arg2];
			UNI_TIPO = arrayUniTipo[arg2];
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	// Boton seleccionar fecha
	public void OnModMangoBtnFechaRegis_Click(View button) {
		showDialog(DATE_DIALOG_ID);
	}

	// Boton registrar
	public void OnModMangoBtnRegistrar_Click(View button) {
		if(dispersosLtdDefinido!=-1)
		registarModMango();
	}

	// Boton regresar
	public void OnModMangoBtnRegresar_Click(View button) {
		finish();
	}

	/*********************************************************************
	 ************************ METODOS DE LA CLASE**************************
	 *********************************************************************/
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
				edtAltitud.setText("Altitud: {GPS StatusChanged}");
				edtLatitud.setText("Latitud: {GPS StatusChanged}");
				edtLongitud.setText("Longitud: {GPS StatusChanged}");
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				edtAltitud.setText("Altitud: {GPS Cargando...}");
				edtLatitud.setText("Latitud: {GPS Cargando...}");
				edtLongitud.setText("Longitud: {GPS Cargando...}");
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				String mensaje = "Por favor active su GPS para un correcto funcionamiento del sistema";
				String titulo = "Advertencia";
				Mensaje(titulo, mensaje);
				edtAltitud.setText("Altitud: {GPS Desactivado}");
				edtLatitud.setText("Latitud: {GPS Desactivado}");
				edtLongitud.setText("Longitud: {GPS Desactivado}");
			}

			public void onLocationChanged(Location location) {
				edtAltitud.setText(String.valueOf(location.getAltitude()));
				edtLatitud.setText(String.valueOf(location.getLatitude()));
				edtLongitud.setText(String.valueOf(location.getLongitude()));

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
			edtAltitud.setText(String.valueOf(loc.getAltitude()));
			edtLatitud.setText(String.valueOf(loc.getLatitude()));
			edtLongitud.setText(String.valueOf(loc.getLongitude()));

			Log.i("",
					String.valueOf(loc.getLatitude() + " - "
							+ String.valueOf(loc.getLongitude())));
		} else {
			edtAltitud.setText("Altitud: {sin datos}");
			edtLatitud.setText("Latitud: {sin datos}");
			edtLongitud.setText("Longitud: {sin datos}");
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
	public void registarModMango() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// Tabla UManejo
			int UMA_ID = this.UMA_ID;
			int UMA_LOTENRO = Integer.parseInt(edtLoteNo.getText().toString());
			int UMA_FINID = Integer.parseInt(this.FIN_ID);
			int UMA_PRUID = 4;
			String UMA_DIA = String.valueOf(this.mDay);
			String UMA_MES = String.valueOf(this.mMonth);
			String UMA_ANIO = String.valueOf(this.mYear);
			float UMA_LATITUD = Float.parseFloat(edtLatitud.getText()
					.toString());
			float UMA_LONGITUD = Float.parseFloat(edtLongitud.getText()
					.toString());
			float UMA_ALTITUD = Float.parseFloat(edtAltitud.getText()
					.toString());
			int UMA_NARBOLES = Integer.parseInt(edtNArboles.getText()
					.toString());
			float UMA_AREA = Float.parseFloat(edtAreaLote.getText().toString());
			String UMA_TRAZADO = "";
			int UMA_TASUELO = this.anaQuimico;
			int UMA_UINJERTO = this.uInjerto;
			String UMA_NPATRON = edtNPatron.getText().toString();
			String UMA_VARIEDAD = edtVariedad.getText().toString();
			int UMA_EDAD = Integer.parseInt(edtEdad.getText().toString());
			float UMA_RESIEMBRA = Float.parseFloat(edtRSiembra.getText()
					.toString());
			float UMA_PRODANIO = Float.parseFloat(edtProAnio.getText()
					.toString());
			int UMA_UNICOD = this.UNI_COD;
			int UMA_UNITIPO = this.UNI_TIPO;
			int UMA_PERINT = 0;
			int UMA_INVITRO = 0;
			float UMA_PENDIENTE = 0;
			int UMA_CAPAS = 0;
			float UMA_PROEFEC = 0;
			int UMA_PH = 0;

			// tabla UMANEJO
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

			// DISPERSOS
			if (dispersosLtdDefinido == 1) {
				int DIS_UMAID = this.UMA_ID;
				int DIS_CERCA = this.DIS_CERCA;
				int DIS_VIA = this.DIS_VIA;
				int DIS_RIO = this.DIS_RIO;
				int DIS_VIVIENDA = this.DIS_VIVIENDA;
				int DIS_POTRERO = this.DIS_POTRERO;
				String DIS_OTRA = edtOtraForma.getText().toString();

				cv.clear();
				cv.put("DIS_UMAID", DIS_UMAID);
				cv.put("DIS_CERCA", DIS_CERCA);
				cv.put("DIS_VIA", DIS_VIA);
				cv.put("DIS_RIO", DIS_RIO);
				cv.put("DIS_VIVIENDA", DIS_VIVIENDA);
				cv.put("DIS_POTRERO", DIS_POTRERO);
				cv.put("DIS_OTRA", DIS_OTRA);

				myDatabase.insert("DISPERSOS", null, cv);
			} else if (dispersosLtdDefinido == 0) {
				int LTD_UMAID = this.UMA_ID;
				String LTD_DISTSIEMBRA = edtDistSiembra.getText().toString();
				int LTD_CUAREG = this.LTD_CUAREG;
				int LTD_TRESBOLILLO = this.LTD_TRESBOLILLO;

				cv.clear();
				cv.put("LTD_UMAID", LTD_UMAID);
				cv.put("LTD_DISTSIEMBRA", LTD_DISTSIEMBRA);
				cv.put("LTD_CUAREG", LTD_CUAREG);
				cv.put("LTD_TRESBOLILLO", LTD_TRESBOLILLO);

				myDatabase.insert("LOTEDEFINIDO", null, cv);
			}

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
		edtAltitud.setText("");
		edtAreaLote.setText("");
		edtCulAso.setText("");
		edtDistSiembra.setText("");
		edtEdad.setText("");
		edtLatitud.setText("");
		edtLongitud.setText("");
		edtLoteNo.setText("");
		edtNArboles.setText("");
		edtNPatron.setText("");
		edtProAnio.setText("");
		edtRSiembra.setText("");
		edtVariedad.setText("");
		edtOtraForma.setText("");
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
