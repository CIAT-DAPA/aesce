package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menuFinca extends Activity {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menufinca);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
	}

	/**************************************************************
	 ******************* METODOS DE LOS BOTONES*********************
	 **************************************************************/
	
	//--Boton regresar--//
	public void OnMenuFincaBtnRegresar_Click(View button){
		finish();
	}

	// --Boton contactos--//
	public void OnMenuFincaBtnContactos_Click(View button) {
		Intent iContactos = new Intent();
		iContactos.setClass(this, contactos.class);
		iContactos.putExtra("PRO_ID", PRO_ID);
		iContactos.putExtra("FIN_ID", FIN_ID);
		startActivity(iContactos);
	}

	// --Boton Modulo Citricos--//
	public void OnMenuProductorBtnModCitricos_Click(View button) {
		Intent iModCitricos = new Intent();
		iModCitricos.setClass(this, modCitricos.class);
		iModCitricos.putExtra("PRO_ID", PRO_ID);
		iModCitricos.putExtra("FIN_ID", FIN_ID);
		startActivity(iModCitricos);
	}

	// --Boton Modulo Aguacate--//
	public void OnMenuFincaBtnModAguacate_Click(View button) {
		Intent iModAguacate = new Intent();
		iModAguacate.setClass(this, modAguacate.class);
		iModAguacate.putExtra("PRO_ID", PRO_ID);
		iModAguacate.putExtra("FIN_ID", FIN_ID);
		startActivity(iModAguacate);
	}

	// --Boton modulo Rasta--//
	public void OnMenuFincaBtnModRasta_Click(View button){
		Intent iRasta = new Intent();
		iRasta.setClass(this, rasta.class);
		iRasta.putExtra("PRO_ID", PRO_ID);
		iRasta.putExtra("FIN_ID", FIN_ID);
		iRasta.putExtra("USU_ID", USU_ID);
		iRasta.putExtra("USU_NOMBRE", USU_NOMBRE);
		startActivity(iRasta);
	}
	
	//--Boton modulo platano--//
	public void OnMenuFincaBtnModPlatano_Click(View button){
		Intent iModPlatano = new Intent();
		iModPlatano.setClass(this, modPlatano.class);
		iModPlatano.putExtra("PRO_ID", PRO_ID);
		iModPlatano.putExtra("FIN_ID", FIN_ID);
		startActivity(iModPlatano);
	}
	
	//--Boton modulo mango--//
	public void OnModMenuFincaBtnModMango_Click(View button){
		Intent iModMango = new Intent();
		iModMango.setClass(this, modMango.class);
		iModMango.putExtra("PRO_ID", PRO_ID);
		iModMango.putExtra("FIN_ID", FIN_ID);
		iModMango.putExtra("USU_ID", USU_ID);
		iModMango.putExtra("USU_NOMBRE", USU_NOMBRE);
		startActivity(iModMango);
	}
	
	//--Boton historico produccion--//
	public void OnMenuFincaBtnHistoricoProduccion_Click(View button){
		Intent iModHistoricoProd = new Intent();
		iModHistoricoProd.setClass(this, modHistoricoProduccion.class);
		iModHistoricoProd.putExtra("PRO_ID", PRO_ID);
		iModHistoricoProd.putExtra("FIN_ID", FIN_ID);
		iModHistoricoProd.putExtra("USU_ID", USU_ID);
		iModHistoricoProd.putExtra("USU_NOMBRE", USU_NOMBRE);
		startActivity(iModHistoricoProd);
	}
	
	//--Boton calidad fruta--//
	public void OnMenuFincaBtnCalidadFruta_Click(View button){
		Intent iCalidadFruta = new Intent();
		iCalidadFruta.setClass(this, calidadFruta.class);
		iCalidadFruta.putExtra("PRO_ID", PRO_ID);
		iCalidadFruta.putExtra("FIN_ID", FIN_ID);
		iCalidadFruta.putExtra("USU_ID", USU_ID);
		iCalidadFruta.putExtra("USU_NOMBRE", USU_NOMBRE);
		startActivity(iCalidadFruta);
	}

}
