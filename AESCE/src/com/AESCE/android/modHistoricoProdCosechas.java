package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class modHistoricoProdCosechas extends Activity implements
		RadioGroup.OnCheckedChangeListener {

	// Variables para capturar los bundles
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String UMA_ID = "";

	// Elementos del formulario
	TextView txvAnioNino;
	TextView txvMesNino;
	RadioGroup rdgNino;
	TextView txvAnioNina;
	TextView txvMesNina;
	RadioGroup rdgNina;
	TextView txvMesFloracion;
	RadioGroup rdgFloracion;

	// Meses
	String[] meses = new String[] { "Enero", "Febrero", "Marzo", "Abril",
			"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };

	// Anio nino
	int[] anioNino = new int[] { 2002, 2003, 2009 };

	// Anio nina
	int[] anioNina = new int[] { 2000, 2001, 2010 };

	int aNino = 0;
	int mNino = 0;
	int aNina = 0;
	int mNina = 0;
	int mFloracion = 0;
	int rNino = 0;
	int rNina = 0;
	int rFloracion = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modhistoricoprodcosechas);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		UMA_ID = "" + bundle.getString("UMA_ID");

		// -Instanciar los elementos del formulario-//
		txvAnioNino = (TextView) findViewById(R.id.IdModHistoricoProdCosechaTxvAnioNino);
		txvMesNino = (TextView) findViewById(R.id.IdModHistoricoProdCosechaTxvMesNino);
		rdgNino = (RadioGroup) findViewById(R.id.IdModHistoricoProdCosechaRdgNino);
		txvAnioNina = (TextView) findViewById(R.id.IdModHistoricoProdCosechaTxvAnioNina);
		txvMesNina = (TextView) findViewById(R.id.IdModHistoricoProdCosechaTxvMesNina);
		rdgNina = (RadioGroup) findViewById(R.id.IdModHistoricoProdCosechaRdgNina);
		txvMesFloracion = (TextView) findViewById(R.id.IdModHistoricoProdCosechaTxvMesFloracion);
		rdgFloracion = (RadioGroup) findViewById(R.id.IdModHistoricoProdCosechaRdgFloracion);

		// -Crear la base de datos-//
		CrearBBDD();
/*
		txtAnioNino.setText(String.valueOf(anioNino[aNino]));
		txvMesNino.setText(meses[mNino]);

		txvAnioNina.setText(String.valueOf(anioNina[aNina]));
		txvMesNina.setText(meses[mNina]);

		txvMesFloracion.setText(meses[mFloracion]);
		*/
	}

	/**********************************************************
	 ******************* METODOS DE LOS BOTONES*****************
	 **********************************************************/

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg0 == rdgNino) {
			if (arg1 == R.id.IdModHistoricoProdCosechaRdoNinoSI) {
				rNino = 1;
			} else if (arg1 == R.id.IdModHistoricoProdCosechaRdoNinoNO) {
				rNino = 0;
			}
		}

		if (arg0 == rdgNina) {
			if (arg1 == R.id.IdModHistoricoProdCosechaRdoNinaSI) {
				rNina = 1;
			} else if (arg1 == R.id.IdModHistoricoProdCosechaRdoNinaNO) {
				rNina = 0;
			}
		}
		
		if(arg0==rdgFloracion){
			if(arg1==R.id.IdModHistoricoProdCosechaRdoFloracionSI){
				rFloracion=1;
			}
			else if(arg1==R.id.IdModHistoricoProdCosechaRdoFloracionNO){
				rFloracion=0;
			}
		}
	}
	
	//--Boton regresar--//
	public void OnModHistoricoProdCosechaBtnRegresar_Click(View button){
		finish();
	}

	

	// --Boton cosecha Nino--//
	public void OnModHistoricoProdCosechaBtnNino_Click(View button) {
		
		if (mNino < 12) {
			int ano = anioNino[aNino];
			
			insertCosecha(ano, (mNino + 1), 1, rNino);
			mNino++;
			txvMesNino.setText(meses[mNino]);
			findViewById(R.id.IdModHistoricoProdCosechaBtnNino)
					.setEnabled(true);
			
		} 
		
		else if (aNino < 2) {
			mNino = 0;
			aNino++;
			txvMesNino.setText(meses[mNino]);
			txvAnioNino.setText(String.valueOf(anioNino[aNino]));
			
		} else {
			aNino=0;
			findViewById(R.id.IdModHistoricoProdCosechaBtnNino).setEnabled(
					false);
		}	
	}


	// --Boton cosecha nina--//
	public void OnModHistoricoProdCosechaBtnNina_Click(View button) {
		if (mNina < 12) {
			int ano = anioNina[aNina];
			insertCosecha(ano, (mNina + 1), 2, rNina);
			mNina++;
			txvMesNina.setText(meses[mNina]);
			
			findViewById(R.id.IdModHistoricoProdCosechaBtnNina)
					.setEnabled(true);
		} else if (aNina < 2) {
			mNina = 0;
			aNina++;
			txvMesNina.setText(meses[mNina]);
			txvAnioNina.setText(String.valueOf(anioNina[aNina]));
		} else {
			
			findViewById(R.id.IdModHistoricoProdCosechaBtnNina).setEnabled(
					false);
		}
	}

	// --Boton floracion--//
	public void OnModHistoricoProdCosechaBtnFloracion_Click(View button) {
		if (mFloracion < 12) {
			insertCosecha(0, (mFloracion + 1), 3, rFloracion);
			mFloracion++;
			txvMesFloracion.setText(meses[mFloracion]);
			findViewById(R.id.IdModHistoricoProdCosechaBtnFloracion)
					.setEnabled(true);
		} else {
			findViewById(R.id.IdModHistoricoProdCosechaBtnFloracion)
					.setEnabled(false);
			mFloracion = 0;
		}
	}

	/*********************************************************************************************
	 ********************************** METODOS DE LA CLASE***************************************
	 *********************************************************************************************/
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

	// --Metodo para crear la base de datos--//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
		}
	}

	// Metodo para insertar en la tabla produccion
	public void insertCosecha(int anio, int mes, int pregId, int resId) {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int COS_UMAID = Integer.parseInt(this.UMA_ID);
			int COS_ANIO = anio;
			int COS_PREGID = pregId;
			int COS_MES = mes;
			int COS_RESID = resId;

			ContentValues cv = new ContentValues();
			cv.put("COS_UMAID", COS_UMAID);
			cv.put("COS_ANIO", COS_ANIO);
			cv.put("COS_PREGID", COS_PREGID);
			cv.put("COS_MES", COS_MES);
			cv.put("COS_RESID", COS_RESID);

			myDatabase.insert("COSECHA", null, cv);
			cv.clear();

		} catch (SQLException e) {
			Mensaje("Error", "" + e.getMessage());
		} finally {
			bdH.close();
		}
	}

}
