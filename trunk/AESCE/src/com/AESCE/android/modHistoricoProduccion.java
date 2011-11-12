package com.AESCE.android;

import android.app.Activity;
import android.content.Context;
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

public class modHistoricoProduccion extends Activity implements
		AdapterView.OnItemClickListener {

	// Variables para capturar los bundles
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";

	int rows;
	int columns;

	// Elementos del formulario
	GridView gdvUmanejo;

	// Elementos para transferir al seleccionar el grid
	int UMA_ID = 0;

	private String[] historicoProd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modhistoricoproduccion);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");

		// -Instanciar los elementos del formulario-//
		gdvUmanejo = (GridView) findViewById(R.id.IdmodHistoricoProduccionGdvUmanejo);

		// -Llenar el gridview-//
		llenarGridView();
		
		gdvUmanejo.setAdapter(new FunnyLookingAdapter(this,
				android.R.layout.simple_list_item_1, historicoProd));

		gdvUmanejo.setOnItemClickListener(this);// .setOnItemSelectedListener(this);
		
	}

	/****************************************************
	 *************** METODOS DE LOS BOTONES***************
	 ****************************************************/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg2 > 2) {
			int id = arg2 - (arg2 % columns);

			String UMA_ID = String.valueOf(historicoProd[id]);

			
			Intent iModHistoricoProdMenu = new Intent();
			iModHistoricoProdMenu.setClass(this, modHistoricoProdMenu.class);
			iModHistoricoProdMenu.putExtra("USU_ID", USU_ID);
			iModHistoricoProdMenu.putExtra("USU_NOMBRE", USU_NOMBRE);
			iModHistoricoProdMenu.putExtra("PRO_ID", PRO_ID);
			iModHistoricoProdMenu.putExtra("FIN_ID", FIN_ID);
			iModHistoricoProdMenu.putExtra("UMA_ID", UMA_ID);
			startActivity(iModHistoricoProdMenu);
		}
	}
	
	//--Boton regresar--//
	public void OnModHistoricoProduccionBtnRegresar_Click(View button){
		finish();
	}

	/****************************************************
	 **************** METODOS DE LA CLASE*****************
	 ****************************************************/
	// Metodo para llenar el gridView
	public void llenarGridView() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "UMA.UMA_PRUID=PRU.PRU_COD AND " + "FIN.FIN_ID="
					+ Integer.parseInt(this.FIN_ID) + " AND "
					+ "UMA.UMA_FINID=FIN.FIN_ID";

			Cursor c = myDatabase.query(
					"UMANEJO UMA, FINCA FIN, PRODUCTOS PRU", new String[] {
							"UMA.UMA_ID", "FIN.FIN_NOMBRE", "PRU.PRU_DESC" },
					selected, null, null, null, "UMA.UMA_ID");

			rows = c.getCount();
			columns = c.getColumnCount();

			int total = rows * columns;

			historicoProd = new String[total + 3];

			historicoProd[0] = "UMA_ID";
			historicoProd[1] = "Nombre Finca";
			historicoProd[2] = "Producto";

			c.moveToFirst();
			int i = 2;

			while (c.isAfterLast() == false) {
				i++;
				historicoProd[i] = c.getString(0);
				i++;
				historicoProd[i] = c.getString(1);
				i++;
				historicoProd[i] = c.getString(2);

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
			if (position <= 2) {
				txvProductor.setGravity(Gravity.CENTER);
			}
			txvProductor.setBackgroundColor(Color.WHITE);
			// txvProductor.setClickable(true);
			txvProductor.setTextColor(Color.BLACK);
			txvProductor.setTextSize(12);
			txvProductor.setText(historicoProd[position]);

			return (convertView);

		}
	}

}
