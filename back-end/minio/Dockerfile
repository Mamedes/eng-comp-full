FROM alpine:latest AS builder

RUN apk add --no-cache curl sed

RUN curl https://dl.min.io/client/mc/release/linux-amd64/mc -o /usr/local/bin/mc && \
    chmod +x /usr/local/bin/mc

WORKDIR /minio
COPY minio-init.sh entrypoint.sh ./

RUN sed -i 's/\r$//' minio-init.sh && \
    sed -i 's/\r$//' entrypoint.sh

FROM minio/minio:latest

COPY --from=builder /usr/local/bin/mc /usr/local/bin/mc

COPY --from=builder /minio/minio-init.sh /minio/minio-init.sh
COPY --from=builder /minio/entrypoint.sh /minio/entrypoint.sh

USER root
RUN chmod +x /minio/entrypoint.sh /minio/minio-init.sh

ENTRYPOINT ["/bin/sh", "/minio/entrypoint.sh"]