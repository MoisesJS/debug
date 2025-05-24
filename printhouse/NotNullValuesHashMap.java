package br.com.netservicos.novosms.emissao.printhouse; 

import java.util.HashMap; 

/**
 * HashMap que não permite valores nulos. Quando recebe um valor nulo converte
 * para String vazia. Este comportamento é necessário devido algumas regras da
 * interpretação do arquivo de leiaute.
 */
class NotNullValuesHashMap extends HashMap {

	private static final long serialVersionUID = 1L;

	public NotNullValuesHashMap(int i) {
		super(i);
	}

	@SuppressWarnings("unchecked")
	public Object put(String k, Object v) {
		return super.put(k, v == null ? "" : v);
	}

	@SuppressWarnings("unchecked")
	public Object put(String k, String v) {
		return super.put(k, v == null ? " " : v);
	}

}
