#!/bin/sh

echo "Aguardando MinIO iniciar..."
sleep 10
COUNTER=0
MAX_RETRIES=20

while ! mc alias set myminio http://minio:9000 admin sF022A-?=sZT; do
  echo "MinIO ainda não está pronto... aguardando..."
  sleep 2
  COUNTER=$((COUNTER + 1))
  if [ "$COUNTER" -ge "$MAX_RETRIES" ]; then
    echo "Erro: MinIO não iniciou após várias tentativas."
    exit 1
  fi
done

echo "MinIO iniciado! Configurando MinIO Client..."

# Criar bucket se não existir
if mc ls myminio/seletivo >/dev/null 2>&1; then
  echo "Bucket 'seletivo' já existe."
else
  echo "Criando bucket 'seletivo'..."
  mc mb myminio/seletivo
  if [ $? -eq 0 ]; then
    echo "Bucket 'seletivo' criado com sucesso!"
  else
    echo "Erro ao criar bucket!"
    exit 1
  fi
fi
