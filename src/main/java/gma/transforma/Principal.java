package gma.transforma;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Principal {
	
	private static String _PASTA_FONTE="fonte";
	private static String _PASTA_DESTINO="destino";
	

	public static void main(String[] args) throws IOException {
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
	
	private static void  _processarTransformacao(File fileFonte, File fileDestino) throws IOException
	{
		File[] listaArquivoFonte=fileFonte.listFiles();
		System.out.println("Processando transformação...");
		System.out.println("Total arquivos para Processar:"+listaArquivoFonte.length);
		
	   
	    
		for(File item:listaArquivoFonte)
		{
			_processaArquivo(item);
		}
		
	}
	
	private static void _processaArquivo(File nomeArquivo) throws IOException
	{
		System.out.println("--->procesando Transformacao arquivo: "+nomeArquivo);
		
		FileReader fileReader = new FileReader(new File(nomeArquivo.getAbsolutePath()));
		BufferedReader reader = new BufferedReader(fileReader);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(_PASTA_DESTINO+"/_p_"+nomeArquivo.getName()));
		
		List<String> listaF=new ArrayList();
		List<String> listaT=new ArrayList();
		String data = null;
		while((data = reader.readLine()) != null){
			if(data.equals(""))
			{
			 listaT=_transforma((ArrayList)listaF);
			 listaF.clear();
			 
			 for(String item:listaT)
			 {
				writer.write(item);
				writer.newLine();
			 }
			 writer.newLine();
			}
			else
			{
				if(data.trim()!="")	listaF.add(data);	
			}
			
			 
		}
		
		fileReader.close();
		reader.close();
		
		writer.close();

	}
	
	private static List<String> _transforma(ArrayList<String> listaF)
	{
		List<String> listaD= new ArrayList<String>();
		
	    
		 String tempo_1[]= listaF.get(1).split("-->");
		 String narrador_2=listaF.get(2);
		 String conteudo_3=listaF.get(3);;
		 
		
		listaD.add("DUB ["+tempo_1[0].trim()+">"+tempo_1[1].trim()+"] "+narrador_2);
	
		if(listaF.size()>3)
		{
			for(int i=4; i<=listaF.size()-1; i++)
			{
				conteudo_3=conteudo_3+" "+listaF.get(i);
			}	
		}
	
		listaD.add(conteudo_3);
		
		return listaD;
	}
}
