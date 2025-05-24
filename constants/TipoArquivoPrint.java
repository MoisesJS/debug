package br.com.netservicos.novosms.emissao.constants;

import java.io.Serializable;

public enum TipoArquivoPrint  implements Serializable {

	
		PRINT5(1,"PRINT5"),
		PRINT10(2,"PRINT10"),
		PRINT15(3,"PRINT15"),
		PRINT20(4,"PRINT20"),
		PRINT25(5,"PRINT25");
		

		private String chaveTpArquivo;
		private int value;

		private TipoArquivoPrint(int value, String chaveTpArquivo ){
			this.value = value;
			this.chaveTpArquivo = chaveTpArquivo;
		}

		public String getChaveTpArquivo() {
			return chaveTpArquivo;
		}

		public void setChaveTpArquivo(String chaveTpArquivo) {
			this.chaveTpArquivo = chaveTpArquivo;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	

}
