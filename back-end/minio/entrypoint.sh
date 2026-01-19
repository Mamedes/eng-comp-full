#!/bin/sh

echo "Setting permissions for MinIO initialization script..."
chmod +x /minio/minio-init.sh

echo "Waiting for MinIO server to start..."
sleep 5

echo "Executing MinIO initialization..."
/minio/minio-init.sh &

echo "Starting MinIO server..."
exec minio server --console-address :9001 /data