export interface HealthStatus {
  status: "UP" | "DOWN" ;
  components?: {
    db?: {
      status: string;
      details?: { database: string; validationQuery: string };
    };
    minio?: { status: string; details?: { service: string } };
    diskSpace?: { status: string; details?: { total: number; free: number } };
    livenessState?: { status: string };
    readinessState?: { status: string };
  };
}
