package com.AESCE.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class main extends Activity {

	public TextView TxvLatitud;
	public TextView TxvLongitud;

	LocationManager locManager;
	LocationListener locListener;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TxvLatitud = (TextView) findViewById(R.id.txvMainLatitud);
		TxvLongitud = (TextView) findViewById(R.id.txvMainLongitud);

		// Invocar metodo del GPS
		comenzarLocalizacion();
	}

	/*********************************************************************
	 * ******************METODOS DE LOS BOTONES****************************
	 *********************************************************************/

	// Metodo del boton salir
	public void OnMainBtnSalir_Click(View button) {
		//salir();
		//System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// Metodo para el boton Iniciar
	public void OnBtnMainIniciar_Click(View button) {
		Intent iLogin = new Intent();
		iLogin.setClass(this, login.class);
		startActivity(iLogin);
	}

	// Metodo para el boton ver mi ubicacion en el mapa
	public void OnMainBtnVerMapa_Click(View button) {
		Intent iMaps = new Intent();
		iMaps.setClass(this, maps.class);
		startActivity(iMaps);
	}

	/*************************************************************************
	 ***************************** METODOS DE LA CLASE*************************
	 *************************************************************************/

	// ------------------------------------------------------------------------
	// -----------------------------------------GPS----------------------------
	// ------------------------------------------------------------------------
	private void comenzarLocalizacion() {
		
		// Obtenemos una referencia al LocationManager
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Obtenemos la ultima posicion Conocida
		Location loc = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Mostramos la ultima posicion conocida
		mostrarPosicion(loc);

		// Nos registramos para recibir actualizaciones
		locListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				TxvLatitud.setText("Latitud: {GPS StatusChanged}");
				TxvLongitud.setText("Longitud: {GPS StatusChanged}");
				
				findViewById(R.id.IdMainBtnVerMapa).setEnabled(true);
				findViewById(R.id.IdMainBtnIniciar).setEnabled(true);
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

				TxvLatitud.setText("Latitud: {GPS Cargando...}");
				TxvLongitud.setText("Longitud: {GPS Cargando...}");
				findViewById(R.id.IdMainBtnVerMapa).setEnabled(true);
				findViewById(R.id.IdMainBtnIniciar).setEnabled(true);
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				String mensaje = "Por favor active su GPS para un correcto funcionamiento del sistema";
				String titulo = "Advertencia";
				Mensaje(titulo, mensaje);
				TxvLatitud.setText("Latitud: {GPS Desactivado}");
				TxvLongitud.setText("Longitud: {GPS Desactivado}");
				
				findViewById(R.id.IdMainBtnVerMapa).setEnabled(false);
				findViewById(R.id.IdMainBtnIniciar).setEnabled(false);
				Mensaje("Desactivado", "Favor habilitar el GPS para que la aplicacion pueda funcionar correctamente");
			}

			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				TxvLatitud.setText("Latitud: " + location.getLatitude());
				TxvLongitud.setText("Longitud: " + location.getLongitude());
				
				findViewById(R.id.IdMainBtnVerMapa).setEnabled(true);
				findViewById(R.id.IdMainBtnIniciar).setEnabled(true);

				Log.i("",
						String.valueOf("Latitud: " + location.getLatitude()
								+ " - " + "Longitud: "
								+ location.getLongitude()));
			}
		};

		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locListener);

	}

	public void mostrarPosicion(Location loc) {
		if (loc != null) {
			TxvLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
			TxvLongitud.setText("Longitud: "
					+ String.valueOf(loc.getLongitude()));
			
			findViewById(R.id.IdMainBtnVerMapa).setEnabled(true);
			findViewById(R.id.IdMainBtnIniciar).setEnabled(true);

			Log.i("",
					String.valueOf(loc.getLatitude() + " - "
							+ String.valueOf(loc.getLongitude())));
		} else {
			TxvLatitud.setText("Latitud: {sin datos}");
			TxvLongitud.setText("Longitud: {sin datos}");
			
			findViewById(R.id.IdMainBtnVerMapa).setEnabled(false);
			findViewById(R.id.IdMainBtnIniciar).setEnabled(false);
			Mensaje("Desactivado", "Favor habilitar el GPS para que la aplicacion pueda funcionar correctamente");
		}
	}

	// ------------------------------------------------------------------------------------
	// --------------------------METODO PARA IMPRIMIR
	// MENSAJES-----------------------------
	// ------------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------
	// ---------------------------Mensaje de salir-------------------------
	// --------------------------------------------------------------------
	public void salir() {
		String squence = "Esta seguro que desea salir";
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
								finish();
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
}