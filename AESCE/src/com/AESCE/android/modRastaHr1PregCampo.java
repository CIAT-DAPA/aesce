package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modRastaHr1PregCampo extends Activity {

	// Variables del bundle
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
		setContentView(R.layout.modrastahr1preguntas);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");
	}

	/**************************************************************
	 ******************* METODOS DE LOS BOTONES*********************
	 **************************************************************/
	// --Boton regresar--//
	public void OnModRastaHr1PreguntasBtnRegresar_Click(View button) {
		finish();
	}

	// --Boton preguntas 10 - 15--//
	public void OnModRastaHr1PreguntasBtnPreg10to15_Click(View button) {
		Intent imodrastahr1Preg10to15 = new Intent();
		imodrastahr1Preg10to15.setClass(this, modrastahr1preg10to15.class);
		imodrastahr1Preg10to15.putExtra("PRO_ID", PRO_ID);
		imodrastahr1Preg10to15.putExtra("FIN_ID", FIN_ID);
		imodrastahr1Preg10to15.putExtra("USU_ID", USU_ID);
		imodrastahr1Preg10to15.putExtra("RAS_ID", RAS_ID);
		imodrastahr1Preg10to15.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1Preg10to15);
	}

}
