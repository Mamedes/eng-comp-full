package com.seletivo.domain.arquivo;

import java.util.List;

public interface ArquivoStorageGateway {
    void uploadArquivo(byte[] conteudo, String nomeArquivo, String tipoConteudo, String caminhoDestino) throws Exception;
    void deleteArquivos(List<String> caminhosDestino) throws Exception;
    void deleteArquivo(String caminhosDestino) throws Exception;

}