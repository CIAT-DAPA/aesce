package com.AESCE.android;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class modRastaHr2PropInferidas extends Activity {

	// Elementos del formulario
	EditText edtLoteNro;
	EditText edtCajuelaNro;
	EditText edtNombreUsuario;
	EditText edtUsoActLote;

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
		setContentView(R.layout.modrastahr2propinferidas);

		// --Instanciar elementos del formulario--//
		edtLoteNro = (EditText) findViewById(R.id.Idmodrastahr2PropInferidasEdtLoteNro);
		edtCajuelaNro = (EditText) findViewById(R.id.Idmodrastahr2PropInferidasEdtCajuelaNumero);
		edtNombreUsuario = (EditText) findViewById(R.id.Idmodrastahr2PropInferidasEdtNombreUsuario);
		edtUsoActLote = (EditText) findViewById(R.id.Idmodrastahr2PropInferidasEdtUsoActLote);

		// -Capturar los bundles-//
		Bundle bundle = getIntent().getExtras();
		USU_ID = "" + bundle.getString("USU_ID");
		USU_NOMBRE = "" + bundle.getString("USU_NOMBRE");
		PRO_ID = "" + bundle.getString("PRO_ID");
		FIN_ID = "" + bundle.getString("FIN_ID");
		RAS_ID = "" + bundle.getString("RAS_ID");
		RAS_UMAID = "" + bundle.getString("RAS_UMAID");

		// -Invocar al metodo crear la base de datos-//
		CrearBBDD();
		
		edtLoteNro.setText(String.valueOf(returnNroLote(this.RAS_UMAID, this.USU_ID)));
		edtNombreUsuario.setText(this.USU_NOMBRE);
	}

	/*******************************************************************
	 ******************** METODOS DE LOS BOTONES************************
	 *******************************************************************/

	// --boton regresar--//
	public void OnModRastaHr2PropInferidasBtnRegresar_Click(View button) {
		finish();
	}
	
	// --Boton registrar--//
	public void OnModRastaHr2PropInferidasBtnRegistrar_Click(View button){
		RegistrarPropInferidas();
	}

	/*******************************************************************
	 ********************** METODOS DE LA CLASE**************************
	 *******************************************************************/
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

	// --Metodo para retornar el numero del lote--//
	public int returnNroLote(String RAS_UMAID, String USU_ID) {

		BaseDatosHelper bdH = new BaseDatosHelper(this);
		int UMA_LOTENRO = 0;

		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			String selected = "RAS.RAS_UMAID="
					+ Integer.parseInt(this.RAS_UMAID) + " AND "
					+ "RAS.RAS_UMAID=UMA.UMA_ID";

			Cursor c = myDatabase.query("RASTA RAS, UMANEJO UMA",
					new String[] { "UMA.UMA_LOTENRO" }, selected, null, null,
					null, "");

			c.moveToFirst();

			if (c.isAfterLast() == false) {
				UMA_LOTENRO = c.getInt(0);
			}

		} catch (Exception e) {
			Mensaje("Error", "Error " + e.getMessage());
		} finally {
			bdH.close();
		}

		return UMA_LOTENRO;
	}

	// --Metodo para registrar las propiedades inferidas--//
	public void RegistrarPropInferidas() {
		BaseDatosHelper bdH = new BaseDatosHelper(this);
		try {

			SQLiteDatabase myDatabase = bdH.getWritableDatabase();
			bdH.abrirBaseDatos();

			int RAS_ID = Integer.parseInt(this.RAS_ID);
			int RAS_UMAID = Integer.parseInt(this.RAS_UMAID);
			int RAS_CAJUELANRO=Integer.parseInt(edtCajuelaNro.getText().toString());
			String RAS_USUID=this.USU_ID;
			String RAS_USOACTLOTE=edtUsoActLote.getText().toString();

			ContentValues cv = new ContentValues();
			cv.put("RAS_CAJUELANRO",RAS_CAJUELANRO);
			cv.put("RAS_USUID",RAS_USUID);
			cv.put("RAS_USOACTLOTE",RAS_USOACTLOTE);

			String selected = "RAS_ID=" + RAS_ID + " AND RAS_UMAID="
					+ RAS_UMAID + "";

			myDatabase.update("RASTA", cv, selected, null);

			limpiar();

			finish();

		} catch (Exception e) {
			Mensaje("Error ", "" + e.getMessage());

		} finally {
			bdH.close();
		}
	}

	// --Metodo para limpiar los elementos--//
	public void limpiar() {
		edtCajuelaNro.setText("");
		edtLoteNro.setText("");
		edtNombreUsuario.setText("");
		edtUsoActLote.setText("");
	}
}
