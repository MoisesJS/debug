package br.com.netservicos.novosms.emissao.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.netservicos.emissao.bean.TransporteDTO;
import br.com.netservicos.framework.transporte.BaseTransporteArquivoTransacao;
import br.com.netservicos.framework.transporte.ProtocoloTransporteEnum;
import br.com.netservicos.framework.transporte.exception.TransporteArquivoException;
import br.com.netservicos.novosms.emissao.core.facade.impl.TransporteArquivoEJBImpl;

public class TransporteUploadTester {
	

	public static void main(String[] args) {

		DecimalFormat  formatador  = new DecimalFormat();
		formatador.applyPattern("R$ ###,###.##;R$ (###,###.##)");


		
		BigDecimal valor = new BigDecimal(123.45);
		String ret22 = formatador.format(valor);
		
		DecimalFormat  formatZeros  = new DecimalFormat();
		formatZeros.applyPattern("00");
		String ret23 = formatZeros.format(6);
		String ret24 = formatZeros.format(66);

		String ret3 = new BigDecimal(6) != null ? formatador.format(new BigDecimal(6)) :  null ;
		ret3 = null != null ? formatador.format(new BigDecimal(6)) :  null ;
		
		
		TransporteArquivoEJBImpl transporte = new TransporteArquivoEJBImpl();
		
		ProtocoloTransporteEnum protocolo = 
			ProtocoloTransporteEnum.valueOf("FTPS");

		
		BaseTransporteArquivoTransacao b = new BaseTransporteArquivoTransacao();

		TransporteDTO origem = new TransporteDTO(); 
		TransporteDTO destino = new TransporteDTO();
		String nomeArquivoOrigem = "tnspadrao.txt"; 
		String nomeArquivoDestino= "tnspadrao.txt";
		String port = new String( "22" );
		
		ProtocoloTransporteEnum ret = ProtocoloTransporteEnum.valueOf("ftp".toUpperCase());
		String a = new String();
		
		
		destino.setProtocolo(ProtocoloTransporteEnum.FTPS);
		destino.setServidor("5.8.100.132");  
		destino.setPorta(port);
		destino.setUsuario("ftp");
		destino.setSenha("ftp");
		destino.setDiretorio("/TRANSBAN/ARRECADACAO/RET/");
		//para compor o nome do arquivo local
		destino.setIdFila(12345);
		
		//destino.setDiretorio("/Cat79/");
		
//		destino.setServidor("5.8.100.182");  
//		destino.setPorta("22");
//		destino.setUsuario("ftp");
//		destino.setSenha("ftp");
//		destino.setDiretorio("/CNAB/DCC/");
//		destino.setProtocolo(ProtocoloTransporteEnum.FTPS);
		
		
		
		destino.setBinario(true);
		destino.setNomeArquivo(nomeArquivoDestino);
		origem.setDiretorio("c:/arq");
		origem.setNomeArquivo(nomeArquivoOrigem);
		
		StringBuffer nomeMascara = new StringBuffer("");
		//StringBuffer nomeMascara = new StringBuffer("[");
		//nomeMascara.append(nomeArquivoOrigem.substring(0, nomeArquivoOrigem.length() - 4));
		//nomeMascara.append("]");
		//nomeMascara.append("[enviado_]ddMMyyyy[.H]999999[.zip]");
		nomeMascara.append("");
		//origem.setMascara("12345");
		
		String pathLog = "arquivoLogs.txt"; 


		

		
		try {
    
			
			
			  
			transporte.transportarArquivoUpload(origem, destino, pathLog);
			
			
//		 	File f = new File(destino.getNomeArquivo());
//			TransporteArquivoFiltro filter = new DefaultTransporteArquivoFiltro( f );
//
//			DefaultNameBuilder nameBuilder = new DefaultNameBuilder(origem.getIdFila().toString());
//			
//			b.transportarArquivoDownload(ProtocoloTransporteEnum.FTP,
//					"5.8.8.128", 
//					port, 
//					"ftpp2oi", 
//					"Brasil@2007", 
//					"TRANSBAN/ARRECADACAO/RET/", 
//					"c:/arq", 
//					nomeArquivo, 
//					new TransporteArquivoTransacao().new UniqueNameCreatorTeste(),
//					true, 
//					filter,
//					transporteArquivoEvento);
//			
//			
//			Collection<String> nomes = b.listarNomesArquivos(ProtocoloTransporteEnum.FTP, 
//																		"5.8.8.128", 
//																		port, 
//																		"ftpp2oi", 
//																		"Brasil@2007", 
//																		"TRANSBAN/ARRECADACAO/RET/", 
//																		filter, 
//																		p);
//			
//			
//			for(String s : nomes){System.out.println(s);}
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransporteArquivoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//System.out.println("Results : " + results);
		//System.out.println("Falhas : " + falhas);
		
		
	}	
	


}
