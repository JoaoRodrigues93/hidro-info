package mz.co.hidroinfo.controller;

import mz.co.hidroinfo.dao.ClienteDao;
import mz.co.hidroinfo.model.ClienteDomestico;
import mz.co.hidroinfo.model.Contacto;
import mz.co.hidroinfo.model.Endereco;

public class MainController {
	
	
	public static void main (String [] args){
		System.out.println("Registando um cliente.....\n");
		
		ClienteDao dao = new ClienteDao();
		Endereco endereco = new Endereco();
		Contacto contacto = new Contacto();
		
		endereco.setAvenida("Karl Marx");
		endereco.setBairro("Bairro central B");
		endereco.setCasaNumero(939);
		endereco.setCidade("Cidade");
		endereco.setCodigoLocal(20);
		endereco.setQuarteirao("Q");
		endereco.setRua("Rua 939");
		
		contacto.setEmail("rodrigueslcn@hotmail.com");
		contacto.setTelefone("+258825845720");
		
		ClienteDomestico cliente = new ClienteDomestico();
		cliente.setBi("04000000000f");
		cliente.setNome("Josue Rodrigues");
		cliente.setNuit(1111111);
		
		cliente.setContacto(contacto);
		cliente.setEndereco(endereco);
		dao.create(cliente);
		
		System.out.println("Registado "+cliente.getNome());
		System.out.println("\nId do cliente registado "+cliente.getId());
	}
}
