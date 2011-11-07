package com.AESCE.android;

import java.io.IOException;

import com.AESCE.android.productor.FunnyLookingAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class finca extends Activity implements AdapterView.OnItemClickListener {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID;

	int rows;
	int columns;

	private String[] arrayFinca;

	private static final int REQUEST_CODE = 10;

	TextView txvBienvenido;
	GridView gdvFinca;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finca);

		// -Capturar el bundle y los valores de este-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");

		// Mensaje("Finca",""+PRO_ID);

		// -Crear los objetos de los elementos de la interfaz-//
		gdvFinca = (GridView) findViewById(R.id.IdFincaGdvFinca);

		gdvFinca.invalidate();

		// Crear la base de datos
		CrearBBDD();

		// Llenar el gridview
		llenarGridView();

		gdvFinca.setAdapter(new FunnyLookingAdapter(this,
				android.R.layout.simple_list_item_1, arrayFinca));

		gdvFinca.setOnItemClickListener(this);
	}

	/*******************************************************************************
	 ***************************** METODOS DE LOS BOTONES****************************
	 *******************************************************************************/

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg2 > 2) {
			int id = arg2 - (arg2 % columns);

			FIN_ID = "" + arrayFinca[id];

			// Mensaje("Seleccion ", " " +
			// productorID[arg2]+" id "+productorID[cedula]);

			Intent iMenuFinca = new Intent();
			iMenuFinca.setClass(this, menuFinca.class);
			iMenuFinca.putExtra("USU_ID", USU_ID);
			iMenuFinca.putExtra("USU_NOMBRE", USU_NOMBRE);
			iMenuFinca.putExtra("PRO_ID", PRO_ID);
			iMenuFinca.putExtra("FIN_ID", FIN_ID);
			startActivity(iMenuFinca);

			// Mensaje("Bien", "" + FIN_ID);

		}

	}

	// --Metodo para el boton regresar--//
	public void OnFincarBtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para insertar una nueva finca--//
	public void OnFincaInsertar_Click(View button) {
		Intent iFincaNuevo = new Intent();
		iFincaNuevo.setClass(this, fincaNuevo.class);
		iFincaNuevo.putExtra("USU_ID", USU_ID);
		iFincaNuevo.putExtra("USU_NOMBRE", USU_NOMBRE);
		iFincaNuevo.putExtra("PRO_ID", PRO_ID);
		startActivityForResult(iFincaNuevo,REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnFinca")) {
				llenarGridView();

				gdvFinca.setAdapter(new FunnyLookingAdapter(this,
						android.R.layout.simple_list_item_1, arrayFinca));
			}
		}
	}

	/*******************************************************************************
	 ********************************* METODOS DE LA CLASE***************************
	 *******************************************************************************/
	// --Metodo para crear la base de datos--//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
		}
	}

	// --Metodo para llenar el GridView--//
	public void llenarGridView() {

		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("FINCA", new String[] { "FIN_ID",
					"FIN_NOMBRE" }, "", null, null, null, "FIN_NOMBRE");

			rows = c.getCount();
			columns = c.getColumnCount();

			int total = rows * columns;

			arrayFinca = new String[total + 2];

			arrayFinca[0] = "ID";
			arrayFinca[1] = "NOMBRE";

			c.moveToFirst();
			int i = 1;

			while (c.isAfterLast() == false) {
				i++;
				arrayFinca[i] = c.getString(0);
				i++;
				arrayFinca[i] = c.getString(1);

				c.moveToNext();
			}
			myDatabase.close();
			// bdH.close();

		} catch (Exception e) {
			// Mensaje("Error", "Error 2 " + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	// --Metodo para imprimir mensajes--//
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

	/***********************************************************************************************
	 ******* CLASE ADAPTER PARA MOSTRAR LOS DATOS EN EL GRIDVIEW****************
	 ***********************************************************************************************/
	@SuppressWarnings("rawtypes")
	private class FunnyLookingAdapter extends ArrayAdapter {

		Context ctx;

		@SuppressWarnings("unchecked")
		public FunnyLookingAdapter(Context context, int resource, String[] items) {
			super(context, resource, items);
			this.ctx = context;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txvFinca = (TextView) convertView;

			if (convertView == null) {
				convertView = new TextView(ctx);
				txvFinca = (TextView) convertView;
			}
			if (position <= 1) {
				txvFinca.setGravity(Gravity.CENTER);
			}
			txvFinca.setBackgroundColor(Color.WHITE);
			// txvProductor.setClickable(true);
			txvFinca.setTextColor(Color.BLACK);
			txvFinca.setTextSize(12);
			txvFinca.setText(arrayFinca[position]);

			return (convertView);

		}
	}

}
