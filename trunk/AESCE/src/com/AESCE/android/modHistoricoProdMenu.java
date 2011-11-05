package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modHistoricoProdMenu extends Activity {

	// Elementos del bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String UMA_ID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modhistoricoprodmenu);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		UMA_ID = "" + bundle.getString("UMA_ID");
	}

	/****************************************************
	 ***************** METODOS DE LOS BOTONES*************
	 ****************************************************/
	// --Boton regresar--//
	public void OnModHistoricoProdMenuBtnRegresar_Click(View button) {
		finish();
	}

	// --Boton produccion--//
	public void OnModHistoricoProdMenuBtnProduccion_Click(View button) {
		Intent iModHistoricoProdProduccion = new Intent();
		iModHistoricoProdProduccion.setClass(this,
				modHistoricoProdProduccion.class);
		iModHistoricoProdProduccion.putExtra("PRO_ID", PRO_ID);
		iModHistoricoProdProduccion.putExtra("FIN_ID", FIN_ID);
		iModHistoricoProdProduccion.putExtra("USU_ID", USU_ID);
		iModHistoricoProdProduccion.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModHistoricoProdProduccion.putExtra("UMA_ID", UMA_ID);
		startActivity(iModHistoricoProdProduccion);
	}

	// --Boton informacion de cosechas--//
	public void OnModHistoricoProdMenuBtnCosechas_Click(View button) {
		Intent iModHistoricoProdCosecha = new Intent();
		iModHistoricoProdCosecha.setClass(this, modHistoricoProdCosechas.class);
		iModHistoricoProdCosecha.putExtra("PRO_ID", PRO_ID);
		iModHistoricoProdCosecha.putExtra("FIN_ID", FIN_ID);
		iModHistoricoProdCosecha.putExtra("USU_ID", USU_ID);
		iModHistoricoProdCosecha.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModHistoricoProdCosecha.putExtra("UMA_ID", UMA_ID);
		startActivity(iModHistoricoProdCosecha);
	}

}
