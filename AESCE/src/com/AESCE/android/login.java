package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends Activity {

	EditText EdtId;
	EditText EdtPass;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		EdtId = (EditText) findViewById(R.id.IdLoginEdtId);
		EdtPass = (EditText) findViewById(R.id.IdLoginEdtClave);

		EdtId.setText("");
		EdtPass.setText("");
	}

	/*********************************************************************************
	 ************************************** METODOS DE LOS BOTONES*********************
	 *********************************************************************************/

	// -- Metodo para el boton principal --//
	public void OnIdLoginBtnPrincipal_Click(View button) {
		Intent iMain = new Intent();
		iMain.setClass(this, main.class);
		startActivity(iMain);
	}

	// -- Metodo para el boton ingresar --//
	public void OnIdLoginBtnIngresar_Click(View buttton) {

		CrearBBDD();
		ingresar();
	}

	// -- Metodo para el boton salir --//
	public void OnIdLoginBtnSalir_Click(View button) {
		// salir out=new salir();
		// out.salir2();
	}

	/********************************************************************************
	 ************************************** METODOS DE LA CLASE***********************
	 ********************************************************************************/

	// -- CREAR LA BASE DE DATOS --//
	public void CrearBBDD() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {
			bdH.crearDataBase();
		} catch (IOException ioe) {
			Mensaje("Error", "No se puede crear DataBase " + ioe.getMessage());
		}
	}

	// -- Mensaje de salir --//
	public void salir(String Msj) {
		String squence = Msj;
		String title = "Salir";
		String PositiveButton = "SI";
		String NegativeButton = "NO";

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
								// Salir aplicacion
								// finish();
							}
						})
				.setNegativeButton(NegativeButton,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// Cerrar cuadro
								dialog.cancel();

							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	
	// -- Mensaje--//
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

	// -- Metodo para validar el login --//
	public void ingresar() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);

		try {
			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			// bdH.onCreate(myDataBase);

			String Id = "" + EdtId.getText().toString();
			String clave = "" + EdtPass.getText().toString();

			String selected = "USU_ID='" + Id + "' AND USU_PASS='" + clave
					+ "'";

			Cursor c = myDatabase.query("USUARIO", new String[] { "USU_ID",
					"USU_PASS", "USU_NOMBRE" }, selected, null, null, null,
					null);

			// Iteramos atraves de los registros del cursor
			c.moveToFirst();

			if (c.isAfterLast() == false) {
				Intent iMenu = new Intent();
				iMenu.setClass(this, menu.class);
				iMenu.putExtra("USU_ID", c.getString(0));
				iMenu.putExtra("USU_NOMBRE", c.getString(2));
				limpiar();
				startActivity(iMenu);
			} else {
				Mensaje("Error",
						"No existe en la base de datos nadie registrado con este ID y clave");
			}

			myDatabase.close();

		} catch (Exception e) {
			Mensaje("Error", "Error: " + e.getMessage());
		}
	}
	
	//-- Metodo para lipiar los editTExt --//
	public void limpiar(){
		EdtId.setText("");
		EdtPass.setText("");
	}
}
