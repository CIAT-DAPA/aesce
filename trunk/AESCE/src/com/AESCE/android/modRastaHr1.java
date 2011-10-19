package com.AESCE.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modRastaHr1 extends Activity {

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
		setContentView(R.layout.modrastahr1);

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

	// --Boton Caracteristicas y observaciones (1,2)--//
	public void OnModRastaHr1BtnCaryObserv12_Click(View button) {
		Intent imodrastahr1caryobserv12 = new Intent();
		imodrastahr1caryobserv12.setClass(this, modrastahr1caryobserv12.class);
		imodrastahr1caryobserv12.putExtra("PRO_ID", PRO_ID);
		imodrastahr1caryobserv12.putExtra("FIN_ID", FIN_ID);
		imodrastahr1caryobserv12.putExtra("USU_ID", USU_ID);
		imodrastahr1caryobserv12.putExtra("RAS_ID", RAS_ID);
		imodrastahr1caryobserv12.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1caryobserv12);
	}

	// --Boton regresar--//
	public void OnModRastaHr1BtnRegresar_Click(View button) {
		finish();
	}

	// --Boton Caracteristicas y observaciones (3)--//
	public void OnModRastaHr1BtnCarYObserv3_Click(View button) {
		Intent imodrastahr1caryobserv3 = new Intent();
		imodrastahr1caryobserv3.setClass(this, modRastaHr1CarYObserv3.class);
		imodrastahr1caryobserv3.putExtra("PRO_ID", PRO_ID);
		imodrastahr1caryobserv3.putExtra("FIN_ID", FIN_ID);
		imodrastahr1caryobserv3.putExtra("USU_ID", USU_ID);
		imodrastahr1caryobserv3.putExtra("RAS_ID", RAS_ID);
		imodrastahr1caryobserv3.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1caryobserv3);
	}
	
	//--Boton caracterisiticas y observaciones (4,5,6)--//
	public void OnModRastaHr1BtnCarYObserv456_Click(View button){
		Intent imodrastahr1caryobserv456 = new Intent();
		imodrastahr1caryobserv456.setClass(this, modRastaHr1CarYObserv456.class);
		imodrastahr1caryobserv456.putExtra("PRO_ID", PRO_ID);
		imodrastahr1caryobserv456.putExtra("FIN_ID", FIN_ID);
		imodrastahr1caryobserv456.putExtra("USU_ID", USU_ID);
		imodrastahr1caryobserv456.putExtra("RAS_ID", RAS_ID);
		imodrastahr1caryobserv456.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1caryobserv456);
	}
	
	//--Boton caracterisitcas y observaciones (7,8,9)--//
	public void OnModRastaHr1BtnCarYObserv789_Click(View button){
		Intent imodrastahr1caryobserv789 = new Intent();
		imodrastahr1caryobserv789.setClass(this, modrastahr1caryobserv789.class);
		imodrastahr1caryobserv789.putExtra("PRO_ID", PRO_ID);
		imodrastahr1caryobserv789.putExtra("FIN_ID", FIN_ID);
		imodrastahr1caryobserv789.putExtra("USU_ID", USU_ID);
		imodrastahr1caryobserv789.putExtra("RAS_ID", RAS_ID);
		imodrastahr1caryobserv789.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1caryobserv789);
	}
	
	//--Boton preguntas de campo--//
	public void OnModRastaHr1BtnCarYObservPregCampo_Click(View button){
		Intent imodrastahr1PregCampo = new Intent();
		imodrastahr1PregCampo.setClass(this, modRastaHr1PregCampo.class);
		imodrastahr1PregCampo.putExtra("PRO_ID", PRO_ID);
		imodrastahr1PregCampo.putExtra("FIN_ID", FIN_ID);
		imodrastahr1PregCampo.putExtra("USU_ID", USU_ID);
		imodrastahr1PregCampo.putExtra("RAS_ID", RAS_ID);
		imodrastahr1PregCampo.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1PregCampo);
	}
	
	//--Boton anotacion especial--//
	public void OnModRastaHr1BtnAnotacionEspecial_Click(View button){
		Intent imodrastahr1AnotacionEspecial = new Intent();
		imodrastahr1AnotacionEspecial.setClass(this, modRastaHr1AnotacionesEspeciales.class);
		imodrastahr1AnotacionEspecial.putExtra("PRO_ID", PRO_ID);
		imodrastahr1AnotacionEspecial.putExtra("FIN_ID", FIN_ID);
		imodrastahr1AnotacionEspecial.putExtra("USU_ID", USU_ID);
		imodrastahr1AnotacionEspecial.putExtra("RAS_ID", RAS_ID);
		imodrastahr1AnotacionEspecial.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(imodrastahr1AnotacionEspecial);
	}

	
	
	/**********************************************************************************
	 *******************************METODOS DE LA CLASE******************************** 
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
