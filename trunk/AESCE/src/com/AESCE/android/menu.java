package com.AESCE.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu extends Activity {

	String USU_NOMBRE="";
	String USU_ID="";
	
	TextView txvBienvenido;
	Button btnProductor;
	Button btnSincronizar;
	
	Bundle bundle;
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		txvBienvenido=(TextView)findViewById(R.id.IdMenuTxvBievenido);
		btnProductor=(Button)findViewById(R.id.IdMenuBtnProductor);
		btnSincronizar=(Button)findViewById(R.id.IdMenuBtnSincronizar);

		
		bundle=getIntent().getExtras();
		txvBienvenido.setText("Bienvenido "+bundle.getString("USU_NOMBRE"));
		
		USU_NOMBRE=bundle.getString("USU_NOMBRE");
		USU_ID=bundle.getString("USU_ID");
		
	}
	
	/***************************************************************************
	 ****************************METODOS DE LOS BOTONES************************* 
	 ***************************************************************************/
	
	//Metodo para el boton productor
	public void OnMenuBtnProductor_Click(View button){
		Intent iProductor=new Intent();
		iProductor.setClass(this, productor.class);
		iProductor.putExtra("USU_NOMBRE", USU_NOMBRE);
		iProductor.putExtra("USU_ID", USU_ID);
		startActivity(iProductor);
	}
	
	//Metodo para el boton sincronizar
	public void OnMenuBtnSincronizar_Click(View button){
		
	}

}