package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView;

public class fincaNuevo extends Activity implements
		AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	int[] arrayDepId;
	int[] arrayMunId;
	int[] arrayUniCod;
	int[] arrayUniTipo;

	int DEP_ID;
	int MUN_ID;
	int UNI_COD;
	int UNI_TIPO;

	int geoReferencia = 1;
	int asociado = 1;
	int regisProd = 1;
	int manCultivos = 1;
	int clima = 1;

	String[] municipios;
	String[] departamentos;
	String[] unidades;

	String USU_ID;
	String USU_NOMBRE;
	String PRO_ID;

	EditText edtNombreFinca;
	EditText edtCedProd;
	RadioGroup rdgGeoreferencia;
	RadioGroup rdgAso;
	EditText edtNomAso;
	EditText edtAreaTotal;
	Spinner spnUnidadesFinca;
	RadioGroup rdgRegProd;
	RadioGroup rdgRegManejo;
	RadioGroup rdgRegClima;
	Spinner spnDepId;
	Spinner spnMunId;
	EditText edtCorregimiento;
	EditText edtVereda;
	EditText edtUsuId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fincanuevo);

		// -Crear la base de datos-//
		CrearBBDD();

		// -Crear los objetos de cada interfaz-//
		edtNombreFinca = (EditText) findViewById(R.id.IdFincaNuevoEdtNombreFinca);
		edtCedProd = (EditText) findViewById(R.id.IdFincaNuevoEdtCedProd);
		rdgGeoreferencia = (RadioGroup) findViewById(R.id.IdFincaNuevoRdgGeoreferencia);
		rdgAso = (RadioGroup) findViewById(R.id.IdFincaNuevoRdgAso);
		edtNomAso = (EditText) findViewById(R.id.IdFincaNuevoEdtNomAso);
		edtAreaTotal = (EditText) findViewById(R.id.IdFincaNuevoEdtAreaTotal);
		spnUnidadesFinca = (Spinner) findViewById(R.id.IdFincaNuevoSpnUnidadesFinca);
		rdgRegManejo = (RadioGroup) findViewById(R.id.IdFincaNuevoRdgRegManejo);
		rdgRegClima = (RadioGroup) findViewById(R.id.IdFincaNuevoRdgRegClima);
		rdgRegProd=(RadioGroup)findViewById(R.id.IdFincaNuevoRdgRegProd);
		spnDepId = (Spinner) findViewById(R.id.IdFincaNuevoSpnDepId);
		spnMunId = (Spinner) findViewById(R.id.IdFincaNuevoSpnMunId);
		edtCorregimiento = (EditText) findViewById(R.id.IdFincaNuevoEdtCorregimiento);
		edtVereda = (EditText) findViewById(R.id.IdFincaNuevoEdtVereda);
		edtUsuId = (EditText) findViewById(R.id.IdFincaNuevoEdtUsuId);

		// -llenar y Adaptadores para el departamento-//
		llenarSpinnerDepartamento();

		spnDepId.setOnItemSelectedListener(this);

		ArrayAdapter<String> adaptadorDepartamentos = new ArrayAdapter<String>(
				this, android.R.layout.test_list_item, departamentos);

		adaptadorDepartamentos
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnDepId.setAdapter(adaptadorDepartamentos);

		// -Llenar y adaptadores para las unidades-//
		llenarSpinnerUnidades();

		spnUnidadesFinca.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> adaptadorUnidades = new ArrayAdapter<String>(this,
				android.R.layout.test_list_item, unidades);

		adaptadorUnidades
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spnUnidadesFinca.setAdapter(adaptadorUnidades);

		// -Asignar los checkedListener a los radioGroup-//
		rdgAso.setOnCheckedChangeListener(this);
		rdgGeoreferencia.setOnCheckedChangeListener(this);
		rdgRegClima.setOnCheckedChangeListener(this);
		rdgRegManejo.setOnCheckedChangeListener(this);
		rdgRegProd.setOnCheckedChangeListener(this);
		

		// -Capturar el bundle y los valores de este-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");

		// -Imprimir la cedula del productor-//
		edtCedProd.setText(PRO_ID);

		// -Imprimir el usuario-//
		edtUsuId.setText(USU_NOMBRE);
		
		
	}

	/****************************************************************
	 ******************** METODOS DE LOS BOTONES***********************
	 *****************************************************************/
	// --Metodo para el boton insertar nuevo departamento--//
	public void OnFincaNuevoBtnDepNuevo_Click(View button) {
		Intent iDepartamentoNuevo = new Intent();
		iDepartamentoNuevo.setClass(this, departamentoNuevo.class);
		startActivity(iDepartamentoNuevo);
	}

	// --Metodo para el boton insertar nuevo municipio--//
	public void OnFincaNuevoBtnMunNuevo_Click(View button) {
		Intent iMunicipioNuevo = new Intent();
		iMunicipioNuevo.setClass(this, municipioNuevo.class);
		startActivity(iMunicipioNuevo);
	}

	// --Metodo para el boton registrar finca--//
	public void OnFincaNuevoBtnRegistrar_Click(View button) {
		registarFinca();
	}

	// --Metodo para los radioGroup--//
	// @Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if (group == rdgGeoreferencia) {
			if (checkedId == R.id.IdFincaNuevoRdbGeoreferenciaSI) {
				geoReferencia = 1;
			} else if (checkedId == R.id.IdFincaNuevoRdbGeoreferenciaNO) {
				geoReferencia = 2;
			}
		}

		if (group == rdgAso) {
			if (checkedId == R.id.IdFincaNuevoRdbAsoSI) {
				asociado = 1;
			} else if (checkedId == R.id.IdFincaNuevoRdbAsoNO) {
				asociado = 2;
			}
		}

		if (group == rdgRegProd) {
			if (checkedId == R.id.IdFincaNuevoRdbRegProdSI) {
				regisProd = 1;
			} else if (checkedId == R.id.IdFincaNuevoRdbRegProdNO) {
				regisProd = 2;
			}
		}

		if (group == rdgRegManejo) {
			if (checkedId == R.id.IdFincaNuevoRdbRegManejoSI) {
				manCultivos = 1;
			} else if (checkedId == R.id.IdFincaNuevoRdbRegManejoNO) {
				manCultivos = 2;
			}
		}

		if (group == rdgRegClima) {
			if (checkedId == R.id.IdFincaNuevoRdbRegClimaSI) {
				clima = 1;
			} else if (checkedId == R.id.IdFincaNuevoRdbRegClimaNO) {
				clima = 2;
			}
		}
	}

	// --Metodo para los spinner--//
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		if (arg0 == spnDepId) {
			DEP_ID = arrayDepId[arg2];
			llenarSpinnerMunicipios(DEP_ID);

			// -Adaptadores para el municipio-//
			spnMunId.setOnItemSelectedListener(this);

			ArrayAdapter<String> adaptadorMunicipios = new ArrayAdapter<String>(
					this, android.R.layout.test_list_item, municipios);

			adaptadorMunicipios
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			spnMunId.setAdapter(adaptadorMunicipios);
		}

		if (arg0 == spnMunId) {
			MUN_ID = arrayMunId[arg2];
		}

		if (arg0 == spnUnidadesFinca) {
			UNI_COD = arrayUniCod[arg2];
			UNI_TIPO = arrayUniTipo[arg2];
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/*********************************************************************************
	 ************************* METODOS DE LA CLASE************************************
	 *********************************************************************************/

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

	// --Metodo para llenar el spinner Departamento--//
	public void llenarSpinnerDepartamento() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("DEPARTAMENTOS", new String[] {
					"DEP_ID", "DEP_DESC" }, null, null, null, null, "");

			int row = c.getCount();

			departamentos = new String[row];
			arrayDepId = new int[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				departamentos[i] = c.getString(1);
				arrayDepId[i] = c.getInt(0);
				c.moveToNext();
				i++;
			}

			bdH.close();

		} catch (Exception e) {
			Mensaje("Error 2", e.getMessage());
		}
	}

	// --Metodo para llenar el spinner municipios--//
	public void llenarSpinnerMunicipios(int DEP_ID) {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "MUN_DEPID=" + DEP_ID + "";

			Cursor c = myDatabase.query("MUNICIPIOS", new String[] { "MUN_ID",
					"MUN_DESC" }, selected, null, null, null, "MUN_DESC ASC");

			int row = c.getCount();
			municipios = new String[row];
			arrayMunId = new int[row];

			c.moveToFirst();

			int i = 0;

			while (c.isAfterLast() == false) {
				municipios[i] = c.getString(1);
				arrayMunId[i] = c.getInt(0);
				c.moveToNext();
				i++;
			}

			bdH.close();

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		}
	}

	// Metodo para llenar el spinner Unidades
	public void llenarSpinnerUnidades() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "UNI_TIPO=1";

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

			bdH.close();

		} catch (Exception e) {
			Mensaje("Error", e.getMessage());
		}
	}

	// --Insertar los campos en la base de datos--//
	public void registarFinca() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String FIN_NOMBRE = edtNombreFinca.getText().toString();
			int FIN_PROID = Integer.parseInt(PRO_ID);
			int FIN_GEOREFERENCIA = geoReferencia;
			int FIN_ASO = asociado;
			String FIN_NOMASO = edtNomAso.getText().toString();
			float FIN_AREA = Float
					.parseFloat(edtAreaTotal.getText().toString());
			int FIN_UNICOD = UNI_COD;
			int FIN_UNITIPO = UNI_TIPO;
			int FIN_REGPROD = regisProd;
			int FIN_REGMANEJO = manCultivos;
			int FIN_REGCLIMA = clima;
			int FIN_DEPID = DEP_ID;
			int FIN_MUNID = MUN_ID;
			String FIN_CORREGIMIENTO = edtCorregimiento.getText().toString();
			String FIN_VEREDA = edtVereda.getText().toString();
			String FIN_USUID = USU_ID;

			ContentValues cv = new ContentValues();
			//cv.put("FIN_ID", 1);
			cv.put("FIN_NOMBRE", FIN_NOMBRE);
			cv.put("FIN_PROID", FIN_PROID);
			cv.put("FIN_GEOREFERENCIA", FIN_GEOREFERENCIA);
			cv.put("FIN_ASO", FIN_ASO);
			cv.put("FIN_NOMASO", FIN_NOMASO);
			cv.put("FIN_AREA", FIN_AREA);
			cv.put("FIN_UNICOD", FIN_UNICOD);
			cv.put("FIN_UNITIPO", FIN_UNITIPO);
			cv.put("FIN_REGPROD", FIN_REGPROD);
			cv.put("FIN_REGMANEJO", FIN_REGMANEJO);
			cv.put("FIN_REGCLIMA", FIN_REGCLIMA);
			cv.put("FIN_DEPID", FIN_DEPID);
			cv.put("FIN_MUNID", FIN_MUNID);
			cv.put("FIN_CORREGIMIENTO", FIN_CORREGIMIENTO);
			cv.put("FIN_VEREDA", FIN_VEREDA);
			cv.put("FIN_USUID", FIN_USUID);

			myDatabase.insert("FINCA", null, cv);

			myDatabase.close();

			limpiar();

			finish();

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		}
	}

	// --Metodo para limpiar--//
	public void limpiar() {
		edtAreaTotal.setText("");
		edtCedProd.setText("");
		edtCorregimiento.setText("");
		edtNomAso.setText("");
		edtNombreFinca.setText("");
		edtUsuId.setText("");
		edtVereda.setText("");
	}
}
