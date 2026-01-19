package com.seletivo.infra.persistence.arquivo;

import com.seletivo.domain.arquivo.ArquivoStorageGateway;
import com.seletivo.infra.configuration.MinioConfig;
import com.seletivo.infra.configuration.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class MinioArquivoStorageGateway implements ArquivoStorageGateway {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Override
    public void uploadArquivo(byte[] conteudo, String nomeArquivo, String tipoConteudo, String caminhoDestino) throws Exception {
        minioUtils.uploadFile(minioConfig.getBucketName(), conteudo, caminhoDestino, tipoConteudo);
    }

    public void uploadArquivo(MultipartFile file, String caminhoDestino) throws Exception {
        uploadArquivo(file.getBytes(), file.getOriginalFilename(), file.getContentType(), caminhoDestino);
    }
    @Override
    public void deleteArquivos(List<String> caminhosDestino) throws Exception {
        minioUtils.deleteFiles(minioConfig.getBucketName(), caminhosDestino);
    }
    @Override
    public void deleteArquivo(String caminhosDestino) throws Exception {
        minioUtils.deleteFile(minioConfig.getBucketName(), caminhosDestino);
    }
}