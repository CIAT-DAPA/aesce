package com.AESCE.android;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class maps extends MapActivity {

	private MapView mapa=null;
	
	 LocationManager locManager;
	 LocationListener locListener;
	 
	 double Latitud;
	 double Longitud;
	 
	 GeoPoint loc;
	 
	 
	 private MapController controlMapa=null;

	/* (non-Javadoc)
	 * @see com.google.android.maps.MapActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.maps);
		
		
		//Obtenemos una referencia al control MapView
		mapa=(MapView)findViewById(R.id.mapa);
		
		controlMapa=mapa.getController();
		
		//Mostramos los controles de zoom sobre el mapa
		mapa.setBuiltInZoomControls(true);
		
		mapa.setSatellite(false);
		
		//progressDialog();

		comenzarLocalizacion();
		
		//Invocamos la clase que se encarga de los marcadores
		MapOverlay mapOverlay=new MapOverlay();
		List<Overlay> listOverlays=mapa.getOverlays();
		listOverlays.clear();
		listOverlays.add(mapOverlay);
		
		mapa.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/********************************************************************
	 ***************************METODOS DE LOS BOTONES******************* 
	 ********************************************************************/
	
	//Metodo para el boton Normal
	public void OnMapsBtnMapaNormal_Click(View button){
		if(mapa.isSatellite()){
			mapa.setSatellite(false);
		}
		else{
		}
	}
	
	//Metodo para el boton Satelital
	public void OnMapsBtnMapaSatelite(View button){
		if(mapa.isSatellite()){
			mapa.setSatellite(false);
		}
		else{
			mapa.setSatellite(true);
		}
	}
	
	//Metodo para el boton principal
	public void OnMapsBtnPrincipal(View button){
		Intent iMain=new Intent();
		iMain.setClass(this, main.class);
		startActivity(iMain);	
	}
	
	
	/*****************************************************************************
	 ********************************METODO DE LA CLASE*************************** 
	 ******************************************************************************/
	
	//-----------------------------------------------------------------------------
	//---------------------METODO PARA LA LOCALIZACION-----------------------------
	//-----------------------------------------------------------------------------
	public void localizacion(){
		
		Double lat= Latitud*1E6;
		Double lon=Longitud*1E6;
		
		loc=new GeoPoint(lat.intValue(), 
				lon.intValue());
		
		
		controlMapa.animateTo(loc);		
	
		controlMapa.setCenter(loc);
		controlMapa.setZoom(18);
		
		//Toast.makeText(getBaseContext(), "Latitud: "+Latitud+"\n"+"Longitud: "+Longitud, Toast.LENGTH_SHORT).show();
	}
	
	//------------------------------------------------------------------------
    //-----------------------------------------GPS----------------------------
    //------------------------------------------------------------------------
 private void comenzarLocalizacion(){
    	
    	//Obtenemos una referencia al LocationManager
    	locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	
    	//Obtenemos la ultima posicion Conocida
		Location loc=
			locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	
		//Mostramos la ultima posicion conocida
		mostrarPosicion(loc);
    	
    	//Nos registramos para recibir actualizaciones
    	locListener= new LocationListener() {
			
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
			}
			
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}
			
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}
			
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				Latitud=location.getLatitude();
				Longitud=location.getLongitude();
				
				localizacion();
				
				Log.i("", String.valueOf("Latitud: "+location.getLatitude()+" - "+"Longitud: "+location.getLongitude()));
			}
		};
		
		locManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, locListener);
    	
    }
    
    
	public void mostrarPosicion(Location loc){
		if(loc!=null){
			Latitud=loc.getLatitude();
			Longitud=loc.getLongitude();
			
			localizacion();
			
			Log.i("", String.valueOf(loc.getLatitude()+" - "+String.valueOf(loc.getLongitude())));
		}
		else{
		}
	}
	
	//------------------------------------------------------------------------------------
	//--------------------------METODO PARA IMPRIMIR MENSAJES-----------------------------
	//------------------------------------------------------------------------------------
	public void Mensaje(String titulo, String Mensaje) {
		String squence = ""+Mensaje;
		String title = ""+titulo;
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
	
	
	//--------------------------------------------------------------------------------------------------
	//-----------------Imprimir mensaje ProgressDialog--------------------------------------------------
	//--------------------------------------------------------------------------------------------------
	public void progressDialog(){
		ProgressDialog dialog= ProgressDialog.show(maps.this, "Cargando", "Por favor espere", true);
	}
	
	//---------------------------------------------------------------------------------------------------
	//-----------------------Clase para poner marcadores-------------------------------------------------
	//---------------------------------------------------------------------------------------------------
	class MapOverlay extends Overlay{

		/* (non-Javadoc)
		 * @see com.google.android.maps.Overlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean, long)
		 */
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			// TODO Auto-generated method stub
			 super.draw(canvas, mapView, shadow, when);
			 
			 //Convertir el geopoint a pixeles
			 Point screenPoint=new Point();
			 mapa.getProjection().toPixels(loc, screenPoint);
			 
			 //Adicionar la marca
			 Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.point);
			 
			 canvas.drawBitmap(bmp, screenPoint.x, screenPoint.y-50, null);
			 return true;
		}
	}
	
	
}
