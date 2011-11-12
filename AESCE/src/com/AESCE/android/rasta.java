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

public class rasta extends Activity implements AdapterView.OnItemClickListener {
	
	private static final int REQUEST_CODE = 10;

	// Variables para capturar los bundles
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";

	int rows;
	int columns;

	private String[] rastaID;
	String RAS_ID;
	String RAS_UMAID;
	int[] UMA_ID;

	// Componente que esta en el formulario Rasta
	GridView gdvRasta;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rasta);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		
		//Mensaje("USU_ID",USU_ID);

		// -Componentes que estan en el formulairo-//
		gdvRasta = (GridView) findViewById(R.id.IdRastaGdvRasta);

		// -Crear la base de datos-//
		CrearBBDD();

		// Llenar el gridview
		llenarGridView();

		gdvRasta.setAdapter(new FunnyLookingAdapter(this,
				android.R.layout.simple_list_item_1, rastaID));

		gdvRasta.setOnItemClickListener(this);// .setOnItemSelectedListener(this);

	}

	/********************************************************************************
	 *********************** METODO DE LOS BOTONES************************************
	 ********************************************************************************/

	// -Metodo para el boton regresar-//
	public void OnRastaBtnRegresar_Click(View button) {
		finish();
	}

	// -Metodo para el boton insertar-//
	public void OnRastaInsertar_Click(View button) {
		Intent iModNuevoLote = new Intent();
		iModNuevoLote.setClass(this, insertarNuevoLote.class);
		iModNuevoLote.putExtra("FIN_ID", FIN_ID);
		startActivity(iModNuevoLote);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg2 > 3) {
			int id = arg2 - (arg2 % columns);

			RAS_ID = String.valueOf(rastaID[id]);
			RAS_UMAID = String.valueOf(rastaID[id + 1]);

			
			Intent iModRasta = new Intent();
			iModRasta.setClass(this, modRasta.class);
			iModRasta.putExtra("USU_ID", USU_ID);
			iModRasta.putExtra("USU_NOMBRE", USU_NOMBRE);
			iModRasta.putExtra("PRO_ID", PRO_ID);
			iModRasta.putExtra("FIN_ID", FIN_ID);
			iModRasta.putExtra("RAS_ID", RAS_ID);
			iModRasta.putExtra("RAS_UMAID", RAS_UMAID);
			startActivity(iModRasta);
			
			

		}
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnRasta")) {
				llenarGridView();

				gdvRasta.setAdapter(new FunnyLookingAdapter(this,
						android.R.layout.simple_list_item_1, rastaID));
			}
		}
		
	}

	/********************************************************************************
	 *********************** METODO DE LA CLASE************************************
	 ********************************************************************************/

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

	// --Metodo para crear la base de datos--//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
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

			String selected = "RAS.RAS_UMAID=UMA.UMA_ID AND " + "FIN.FIN_ID="
					+ FIN_ID + " AND " + "UMA.UMA_FINID=FIN.FIN_ID ";

			Cursor c = myDatabase.query("RASTA RAS, UMANEJO UMA, FINCA FIN",
					new String[] { "RAS.RAS_ID", "UMA.UMA_ID",
							"UMA.UMA_LOTENRO", "FIN.FIN_NOMBRE" }, selected,
					null, null, null, "RAS.RAS_ID");

			rows = c.getCount();
			columns = c.getColumnCount();

			int total = rows * columns;

			rastaID = new String[total + 4];

			rastaID[0] = "ID";
			rastaID[1] = "UMA ID";
			rastaID[2] = "Nº Lote";
			rastaID[3] = "Nombre Finca";

			c.moveToFirst();
			int i = 3;

			while (c.isAfterLast() == false) {
				i++;
				rastaID[i] = c.getString(0);
				i++;
				rastaID[i] = c.getString(1);
				i++;
				rastaID[i] = c.getString(2);
				i++;
				rastaID[i] = c.getString(3);

				c.moveToNext();
			}

		} catch (Exception e) {
			Mensaje("Error", "Error 2 " + e.getMessage());
		} finally {
			bdH.close();
		}
	}

	/***********************************************************************************************
	 ****************** CLASE ADAPTER PARA MOSTRAR LOS DATOS EN EL GRIDVIEW****************
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
			if (position <= 3) {
				txvProductor.setGravity(Gravity.CENTER);
			}
			txvProductor.setBackgroundColor(Color.WHITE);
			// txvProductor.setClickable(true);
			txvProductor.setTextColor(Color.BLACK);
			txvProductor.setTextSize(12);
			txvProductor.setText(rastaID[position]);

			return (convertView);

		}
	}

}
