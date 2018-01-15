package gma.transforma;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Principal {

	private static String _PASTA_FONTE = "fonte";
	private static String _PASTA_DESTINO = "destino";

	public static void main(String[] args) throws IOException {
		boolean isOk;
		File fileFonte;
		File fileDestino;

		System.out.println("Iniciando...");

		isOk = false;
		fileFonte = new File(_PASTA_FONTE);
		fileDestino = new File(_PASTA_DESTINO);

		isOk = _vefificarPasta(fileFonte, fileDestino);

		if (isOk)
			isOk = _verificaPastaDestinoVazia(fileDestino);

		if (isOk)
			_processarTransformacao(fileFonte, fileDestino);

		System.out.println();
		System.out.println("Fim processamento");
	}

	private static boolean _vefificarPasta(File fileFonte, File fileDestino) {
		System.out.println("Verificando existencia pastas...");

		boolean isOk = true;

		if (!fileFonte.exists()) {
			System.out.println("Pasta [./" + _PASTA_FONTE + "] nao existe");
			isOk = false;
		}

		if (!fileDestino.exists()) {
			System.out.println("Pasta [./" + _PASTA_DESTINO + "] nao existe");
			isOk = false;
		}

		if (!isOk)
			return false;

		return isOk;
	}

	private static boolean _verificaPastaDestinoVazia(File fileDestino) {
		boolean isOk = true;

		System.out.println("Verificando se pasta [./" + _PASTA_DESTINO + "] esta vazia");

		if (fileDestino.list().length > 0) {
			System.out.println("Pasta [./" + _PASTA_DESTINO + "] nao esta vazia");
			isOk = false;
		}

		return isOk;
	}

	private static void _processarTransformacao(File fileFonte, File fileDestino) throws IOException {
		File[] listaArquivoFonte = fileFonte.listFiles();
		System.out.println("Processando transformação...");
		System.out.println();
		System.out.println("Total arquivos para Processar:" + listaArquivoFonte.length);
		System.out.println();
		for (File item : listaArquivoFonte) {
			_processaArquivo(item);
		}

	}

	private static void _processaArquivo(File nomeArquivo) throws IOException {
	 
		System.out.println("--->procesando Transformacao arquivo: " + nomeArquivo);

		// "UTF-8"

		// BufferedReader fileReader = new BufferedReader(new InputStreamReader(new
		// FileInputStream(nomeArquivo.getAbsolutePath()), "UTF-8"));

		FileReader fileReader = new FileReader(new File(nomeArquivo.getAbsolutePath()));
		BufferedReader reader = new BufferedReader(fileReader);

		BufferedWriter writer = new BufferedWriter(new FileWriter(_PASTA_DESTINO + "/_p_" + nomeArquivo.getName()));

		List<String> listaF = new ArrayList<>();
		List<Estrutura> listaT = new ArrayList<>();
		List<Estrutura> listaEstrutura= new ArrayList<>();
		
		String data = null;
		while ((data = reader.readLine()) != null) {
			//System.out.println(data);
			if (data.equals("")) {
				listaT = _transforma(listaF);
				for(Estrutura item: listaT) listaEstrutura.add(item);
				
				listaF.clear();
				 
			} else {
				
				if (data.trim() != "")
					listaF.add(data);
			}

		}
	 
		
		if(!listaF.isEmpty())
		{
			listaT = _transforma(listaF);
			for(Estrutura item: listaT) listaEstrutura.add(item);
		}
		
	 
		
	    Collections.sort(listaEstrutura);
		 
		for(Estrutura item:listaEstrutura)
		{
			//System.out.println(item.getChave());
			for (String item2 : item.getBloco()) {
				writer.write(item2);
				writer.newLine();
			}
			
		}
		

		fileReader.close();
		reader.close();

		writer.close();

	}

	private static List<Estrutura> _transforma(List<String> listaF) {
		List<String> listaD = new ArrayList<String>();
		List<Estrutura>listaEstrutura= new ArrayList<Estrutura>();
		

		String tempo_1[] = listaF.get(1).split("-->");
		String tempo_1_A=tempo_1[0].trim().replace(".",":");
		String tempo_1_B=tempo_1[1].trim().replace(".",":");
		String narrador_lista_2[] = listaF.get(2).split(",");
		String conteudo_3 = "";
		
		 
		//Coloca todo o conteudo numa unica linha - conteudo: fala
		if (listaF.size() > 3) {
			conteudo_3 = ""; //listaF.get(3);
			for (int i = 3; i <= listaF.size() - 1; i++) {
				conteudo_3 = conteudo_3 + " " + listaF.get(i);
			}
		}
		conteudo_3=conteudo_3.trim();
		
		  
		//cria bloco para cada narrador
		for (String narrador_2_item : narrador_lista_2) {
							
			listaD.add("DUB[0 N " + tempo_1_A + ">" + tempo_1_B + "] " + narrador_2_item.trim());

			if(!conteudo_3.isEmpty()) listaD.add(conteudo_3);
			listaD.add("\n");
			
			
		}
		listaEstrutura.add(new Estrutura(tempo_1_A,listaD));
		 
		return listaEstrutura;
	}
}

class Estrutura implements Comparable{
	
	private String _chave;
	private List<String> _bloco;
	
	public String getChave() {return _chave;};
	public List<String> getBloco() {return _bloco;};
	
	
	public Estrutura(String chave, List<String> bloco)
	{
		this._chave=chave;
		this._bloco=bloco;
	}
	public int compareTo(Object obj) {
	 
		  Estrutura est=(Estrutura) obj;
		
		  return this._chave.compareTo(est._chave);
		
		
	}
}
