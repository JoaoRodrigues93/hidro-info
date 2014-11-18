package mz.co.hidroinfo.controller;

import java.util.Vector;

import mz.co.hidroinfo.model.Recibo;

public class ReciboCollection {

	private static Recibo recibo;
	
	public static Vector<Recibo> getCollectionBean(){
		
		Vector<Recibo> vector = new Vector<Recibo>();
		vector.add(recibo);
		
		return vector;
	}

	public ReciboCollection(){}
 
	public static void setRecibo(Recibo recibo){
		ReciboCollection.recibo = recibo;
	}
	
			
}
