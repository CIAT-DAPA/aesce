package com.AESCE.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modRasta extends Activity {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";
	String FIN_ID = "";
	String RAS_ID = "";
	String RAS_UMAID = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modrasta);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");
		
		//Mensaje("RAS_ID",RAS_ID);

	}

	/**************************************************************
	 ******************* METODOS DE LOS BOTONES*********************
	 **************************************************************/

	// --Boton Hoja de respuestas No 1--//
	public void OnModRastaBtnHr1_Click(View button) {
		Intent iModRastaHr1 = new Intent();
		iModRastaHr1.setClass(this, modRastaHr1.class);
		iModRastaHr1.putExtra("PRO_ID", PRO_ID);
		iModRastaHr1.putExtra("FIN_ID", FIN_ID);
		iModRastaHr1.putExtra("USU_ID", USU_ID);
		iModRastaHr1.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHr1.putExtra("RAS_ID", RAS_ID);
		iModRastaHr1.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHr1);
	}

	// --Boton Regresar--//
	public void OnModRastaBtnRegresar_Click(View button) {
		finish();
	}

	// --Boton Hoja de respuestas No 2--//
	public void OnModRastaBtnHr2_Click(View button) {
		Intent iModRastaHr2 = new Intent();
		iModRastaHr2.setClass(this, modRastaHr2.class);
		iModRastaHr2.putExtra("PRO_ID", PRO_ID);
		iModRastaHr2.putExtra("FIN_ID", FIN_ID);
		iModRastaHr2.putExtra("USU_ID", USU_ID);
		iModRastaHr2.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHr2.putExtra("RAS_ID", RAS_ID);
		iModRastaHr2.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHr2);
	}

	// --Boton Hoja de procedimientos--//
	public void OnModRastaBtnHojaProc_Click(View button) {
		Intent iModRastaHrProcedimientos = new Intent();
		iModRastaHrProcedimientos.setClass(this, modRastaHrProcedimientos.class);
		iModRastaHrProcedimientos.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientos.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientos.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientos.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientos.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientos.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientos);
	}
	
	/**********************************************************************************
	 *****************************METODOS DE LOS BOTONES******************************* 
	 **********************************************************************************/
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

}
