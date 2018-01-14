package gma.transforma;

import java.io.File;

public class Principal {
	
	private static String _PASTA_FONTE="fonte";
	private static String _PASTA_DESTINO="destino";
	

	public static void main(String[] args) {
		boolean isOk;
		File fileFonte;
		File fileDestino;
		
		System.out.println("Iniciando...");
		
		isOk=false;
		fileFonte = new File(_PASTA_FONTE);
		fileDestino = new File(_PASTA_DESTINO);
				
		isOk=_vefificarPasta(fileFonte,fileDestino);
		
		if(isOk) isOk=_verificaPastaDestinoVazia(fileDestino);
		
		if(isOk) _processarTransformacao(fileFonte,fileDestino);
		
		

		System.out.println("Fim processamento");
	}

	
	private static boolean _vefificarPasta(File fileFonte, File fileDestino)
	{
		System.out.println("Verificando existencia pastas...");
		
		boolean isOk=true;
		
		if(!fileFonte.exists())
		{
			System.out.println("Pasta [./"+_PASTA_FONTE+"] nao existe");
			isOk=false;
		}
		
		if(!fileDestino.exists())
		{
			System.out.println("Pasta [./"+_PASTA_DESTINO+"] nao existe");
			isOk=false;
		}
		
	    if(!isOk) return false; 
	    
		return isOk;
	}
	
	
	private static  boolean _verificaPastaDestinoVazia(File fileDestino)
	{
		boolean isOk=true;
		
		System.out.println("Verificando se pasta [./"+_PASTA_DESTINO+"] esta vazia");
		
		 if(fileDestino.list().length>0)
		    {
		    	System.out.println("Pasta [./"+_PASTA_DESTINO+"] nao esta vazia");
		    	isOk= false;
		    }
		 
		 
		return isOk;
	}
	
	private static void  _processarTransformacao(File fileFonte, File fileDestino)
	{
		String[] listaArquivoFonte=fileFonte.list();
		System.out.println("Processando transformação...");
		System.out.println("Total arquivos para Processar:"+listaArquivoFonte.length);
		
	
		for(String item:listaArquivoFonte)
		{
			_processaArquivo(item);
		}
		
	}
	
	private static void _processaArquivo(String nomeArquivo)
	{
		System.out.println("--->procesando Transformacao arquivo: "+nomeArquivo);
	}
}
