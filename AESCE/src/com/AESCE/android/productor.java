package com.AESCE.android;

import java.io.IOException;

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

public class productor extends Activity implements
		AdapterView.OnItemClickListener {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	
	private static final int REQUEST_CODE = 10;

	int rows;
	int columns;

	private String[] productorID;

	TextView txvBienvenido;
	GridView gdvProductor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productor);

		Bundle bundle = getIntent().getExtras();

		txvBienvenido = (TextView) findViewById(R.id.IdProductorTxvBienvenido);

		gdvProductor = (GridView) findViewById(R.id.IdProductorGdvProductor);

		txvBienvenido.setText("Bienvenido " + bundle.getString("USU_NOMBRE"));

		// Valores para transmitir al siguiente formulario
		USU_ID = bundle.getString("USU_ID");
		USU_NOMBRE = bundle.getString("USU_NOMBRE");

		// Crear la base de datos
		CrearBBDD();

		// Llenar el gridview
		llenarGridView();

		gdvProductor.setAdapter(new FunnyLookingAdapter(this,
				android.R.layout.simple_list_item_1, productorID));

		gdvProductor.setOnItemClickListener(this);// .setOnItemSelectedListener(this);

	


	}

	/********************************************************************************
	 *********************** METODO DE LOS BOTONES************************************
	 ********************************************************************************/
	// --Metodo para el boton regresar--//
	public void OnProductorBtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para el boton insertar--//
	public void OnProductorInsertar_Click(View button) {
		Intent iProductorNuevo = new Intent();
		iProductorNuevo.setClass(this, ProductorNuevo.class);
		startActivityForResult(iProductorNuevo,REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnProductor")) {
				llenarGridView();
				gdvProductor.setAdapter(new FunnyLookingAdapter(this,
						android.R.layout.simple_list_item_1, productorID));
			}
		}
	}

	// -Metodo para capturar el touch del gridview-//
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg2 > 2) {
			int cedula = arg2 - (arg2 % columns);

			PRO_ID = "" + productorID[cedula];

			// Mensaje("Seleccion ", " " +
			// productorID[arg2]+" id "+productorID[cedula]);

			Intent iMenuProductor = new Intent();
			iMenuProductor.setClass(this, menuProductor.class);
			iMenuProductor.putExtra("USU_ID", USU_ID);
			iMenuProductor.putExtra("USU_NOMBRE", USU_NOMBRE);
			iMenuProductor.putExtra("PRO_ID", PRO_ID);
			startActivity(iMenuProductor);

		}
	}

	/*********************************************************************************
	 *************************** METODOS DE LA CLASE**********************************
	 *********************************************************************************/

	// --Metodo para llenar el GridView--//
	public void llenarGridView() {

		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			Cursor c = myDatabase.query("PRODUCTOR", new String[] { "PRO_ID",
					"PRO_NOMBRE", "PRO_APELLIDO1" }, "", null, null, null,
					"PRO_ID");

			rows = c.getCount();
			columns = c.getColumnCount();

			int total = rows * columns;

			productorID = new String[total + 3];

			productorID[0] = "CEDULA";
			productorID[1] = "NOMBRE";
			productorID[2] = "APELLIDO";

			c.moveToFirst();
			int i = 2;

			while (c.isAfterLast() == false) {
				i++;
				productorID[i] = c.getString(0);
				i++;
				productorID[i] = c.getString(1);
				i++;
				productorID[i] = c.getString(2);

				c.moveToNext();
			}


		} catch (Exception e) {
			// Mensaje("Error", "Error 2 " + e.getMessage());
		}
		finally{
			bdH.close();
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
	public class FunnyLookingAdapter extends ArrayAdapter {

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
			TextView txvProductor = (TextView) convertView;

			if (convertView == null) {
				convertView = new TextView(ctx);
				txvProductor = (TextView) convertView;
			}
			if (position <= 2) {
				txvProductor.setGravity(Gravity.CENTER);
			}
			txvProductor.setBackgroundColor(Color.WHITE);
			// txvProductor.setClickable(true);
			txvProductor.setTextColor(Color.BLACK);
			txvProductor.setTextSize(12);
			txvProductor.setText(productorID[position]);

			return (convertView);

		}
	}

}