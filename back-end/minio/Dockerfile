FROM minio/minio:latest

# Define diretório de trabalho
WORKDIR /minio

# Copia o script de inicialização e o entrypoint
COPY minio-init.sh /minio/minio-init.sh
COPY entrypoint.sh /minio/entrypoint.sh

# Dá permissão para execução dos scripts
RUN chmod +x /minio/entrypoint.sh /minio/minio-init.sh

# Define o entrypoint do contêiner
ENTRYPOINT ["/bin/sh", "/minio/entrypoint.sh"]