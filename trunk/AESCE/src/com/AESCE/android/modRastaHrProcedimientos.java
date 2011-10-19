package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class modRastaHrProcedimientos extends Activity {

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
		setContentView(R.layout.modrastahrprocedimientos);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");
	}

	/***************************************************************
	 ********************* METODOS DE LOS BOTONES********************
	 ***************************************************************/
	// --Metodo para el boton regresar--//
	public void OnModRastaHrProcedimientosBtnRegresar_Click(View button) {
		finish();
	}

	// --Metodo para el boton presencia de moteados--//
	public void OnModRastaHrProcedimientosBtnPresMoteados_Click(View button) {
		Intent iModRastaHrProcedimientosPresMoteados = new Intent();
		iModRastaHrProcedimientosPresMoteados.setClass(this,
				modRastaHrProcedimientosPresMoteados.class);
		iModRastaHrProcedimientosPresMoteados.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientosPresMoteados.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientosPresMoteados.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientosPresMoteados
				.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientosPresMoteados.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientosPresMoteados.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientosPresMoteados);
	}

	// --Metodo para el boton profundidad efectiva--//
	public void OnModRastaHrProcedimientosBtnProfEfectiva_Click(View button) {
		Intent iModRastaHrProcedimientosProfEfectiva = new Intent();
		iModRastaHrProcedimientosProfEfectiva.setClass(this,
				modrastahrprocedimientosprofefectiva.class);
		iModRastaHrProcedimientosProfEfectiva.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientosProfEfectiva.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientosProfEfectiva.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientosProfEfectiva
				.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientosProfEfectiva.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientosProfEfectiva.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientosProfEfectiva);
	}

	// --Metodo para el boton Materia Organica--//
	public void OnModRastaHrProcedimientosBtnMatOrganica_Click(View button) {
		Intent iModRastaHrProcedimientosMatOrganica = new Intent();
		iModRastaHrProcedimientosMatOrganica.setClass(this,
				modrastahrprocedimientosmatorganica.class);
		iModRastaHrProcedimientosMatOrganica.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientosMatOrganica.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientosMatOrganica.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientosMatOrganica.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientosMatOrganica.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientosMatOrganica.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientosMatOrganica);
	}

	// --Metodo para el boton salinidad--//
	public void OnModRastaHrProcedimientosBtnSalinidad(View button) {
		Intent iModRastaHrProcedimientosSalinidad = new Intent();
		iModRastaHrProcedimientosSalinidad.setClass(this,
				modrastahrprocedimientossalinidad.class);
		iModRastaHrProcedimientosSalinidad.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientosSalinidad.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientosSalinidad.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientosSalinidad.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientosSalinidad.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientosSalinidad.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientosSalinidad);
	}
	
	//--Metodo para el boton sodicidad--//
	public void OnModRastaHrProcedimientosBtnSodicidad_Click(View button){
		Intent iModRastaHrProcedimientosSodicidad= new Intent();
		iModRastaHrProcedimientosSodicidad.setClass(this,
				modrastahrprocedimientossodicidad.class);
		iModRastaHrProcedimientosSodicidad.putExtra("PRO_ID", PRO_ID);
		iModRastaHrProcedimientosSodicidad.putExtra("FIN_ID", FIN_ID);
		iModRastaHrProcedimientosSodicidad.putExtra("USU_ID", USU_ID);
		iModRastaHrProcedimientosSodicidad.putExtra("USU_NOMBRE", USU_NOMBRE);
		iModRastaHrProcedimientosSodicidad.putExtra("RAS_ID", RAS_ID);
		iModRastaHrProcedimientosSodicidad.putExtra("RAS_UMAID", RAS_UMAID);
		startActivity(iModRastaHrProcedimientosSodicidad);
	}

}
