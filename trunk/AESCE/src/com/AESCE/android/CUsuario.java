package com.AESCE.android;

import android.content.Intent;
import android.os.Bundle;

public class CUsuario {

	public String USU_ID;
	public String USU_NOMBRE;
	
	private String USU_NOMBRE2;
	
	

	/**
	 * @return the uSU_NOMBRE2
	 */
	public String getUSU_NOMBRE2() {
		return USU_NOMBRE2;
	}

	/**
	 * @param uSU_NOMBRE2 the uSU_NOMBRE2 to set
	 */
	public void setUSU_NOMBRE2(Intent iusu) {
		Bundle bundle=iusu.getExtras();
		USU_NOMBRE2 =bundle.getString("USU_NOMBRE");;
	}
	
	
	
}
