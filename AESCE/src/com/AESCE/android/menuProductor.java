package com.AESCE.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menuProductor extends Activity {

	String USU_ID = "";
	String USU_NOMBRE = "";
	String PRO_ID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuproductor);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
	}

	/*********************************************************************************
	 ***************************** METODO EN LOS BOTONES*******************************
	 *********************************************************************************/
	// --Metodo para el boton finca--//
	public void OnMenuProductorBtnFinca_Click(View button){
		Intent iMfinca=new Intent();
		iMfinca.setClass(this, finca.class);
		iMfinca.putExtra("USU_ID", USU_ID);
		iMfinca.putExtra("USU_NOMBRE", USU_NOMBRE);
		iMfinca.putExtra("PRO_ID", PRO_ID);
		startActivity(iMfinca);
	}

	// -- Metodo en el boton Modulo TIC --//
	public void OnMenuProductorBtnTIC_Click(View button) {
		Intent iMtic = new Intent();
		iMtic.setClass(this, TIC.class);
		iMtic.putExtra("PRO_ID", PRO_ID);
		startActivity(iMtic);
	}

	/*********************************************************************************
	 *************************** METODOS DE LA CLASE***********************************
	 *********************************************************************************/
	// -- Metodo para imprimir mensajes --//
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
