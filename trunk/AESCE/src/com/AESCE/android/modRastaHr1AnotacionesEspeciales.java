package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class modRastaHr1AnotacionesEspeciales extends Activity {

	// Elementos del formulario
	EditText edtAnotacionEspecial;

	// Elementos del bundle
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
		setContentView(R.layout.modrastahr1anotacionesespeciales);

		// --Instanciar elementos del formulario--//
		edtAnotacionEspecial = (EditText) findViewById(R.id.Idmodrastahr1edtanotacionesespeciales);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -crear la base de datos-//
		CrearBBDD();
	}

	/***********************************************************************************************
	 ********************************* METODOS DE LOS BOTONES****************************************
	 ***********************************************************************************************/

	// --boton registar anotacion especial--//
	public void Onmodrastahr1BtnRegAnotacionEspecial_Click(View button) {
		RegistrarRasta();
	}
	
	//--Boton regresar--//
	public void Onmodrastahr1BtnRegresar_Click(View button){
		finish();
	}

	/***********************************************************************************************
	 *********************************** METODOS DE LA CLASE****************************************
	 ***********************************************************************************************/
	// -- METODO PARA IMPRIMIR MENSAJES--//
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

	// --Metodo para llenar la TABLA RASTA--//
	public void RegistrarRasta() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = Integer.parseInt(this.RAS_ID);
			int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
			String RAS_ANOTACIONESPECIALES = edtAnotacionEspecial.getText()
					.toString();

			ContentValues cv = new ContentValues();
			cv.put("RAS_ANOTACIONESPECIALES", RAS_ANOTACIONESPECIALES);

			String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
					+ RAS_UMAID + "";

			myDatabase.update("RASTA", cv, selected, null);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error 2", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los elementos--//
	public void limpiar() {
		edtAnotacionEspecial.setText("");
	}

}
