package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modRastaHr2 extends Activity {

	// Elementos del Bundle
	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrastahr2);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");
	}

	/*************************************************
	 ************** METODOS DE LOS BOTONES*************
	 *************************************************/
	// --Boton regresar--//
	public void OnModRastaHr2BtnRegresar_Click(View button) {
		finish();
	}
	
	//--Boton propiedades inferidas--//
	public void OnModRastaHr2BtnPropInferidas_Click(View button){
		Intent iModRastaHr2PropInferidas = new Intent();
		iModRastaHr2PropInferidas.setClass(this, modRastaHr2PropInferidas.class);
		iModRastaHr2PropInferidas.putExtra("PRO_ID", PRO_ID);
		iModRastaHr2PropInferidas.putExtra("FIN_ID", FIN_ID);
		iModRastaHr2PropInferidas.putExtra("USU_ID", USU_ID);
		iModRastaHr2PropInferidas.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHr2PropInferidas.putExtra("RAS_ID", RAS_ID);
		iModRastaHr2PropInferidas.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHr2PropInferidas);
	}
	
	//--Boton propiedades inferidas (1,2,3,4)--//
	public void OnModRastaHr2BtnPropInferidas1234_Click(View button){
		Intent iModRastaHr2PropInferidas1234 = new Intent();
		iModRastaHr2PropInferidas1234.setClass(this, modrastahr2propinferidas1234.class);
		iModRastaHr2PropInferidas1234.putExtra("PRO_ID", PRO_ID);
		iModRastaHr2PropInferidas1234.putExtra("FIN_ID", FIN_ID);
		iModRastaHr2PropInferidas1234.putExtra("USU_ID", USU_ID);
		iModRastaHr2PropInferidas1234.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHr2PropInferidas1234.putExtra("RAS_ID", RAS_ID);
		iModRastaHr2PropInferidas1234.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHr2PropInferidas1234);
	}

}
