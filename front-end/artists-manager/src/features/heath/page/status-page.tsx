import { useEffect } from "react";
import {
    Activity,
    Database,
    HardDrive,
    Cloud,
    CheckCircle2,
    XCircle,
    Loader2,
} from "lucide-react";

import { useObservable } from "@/shared/hooks/use-observable";
import { healthFacade } from "../facade/health.facade";
import { HealthStatus } from "../types/health.types";

const INITIAL_HEALTH_STATE = {
    data: null as HealthStatus | null,
    isLoading: false,
};

interface StatusCardProps {
    title: string;
    status?: string;
    icon: any;
    details?: string;
}

function StatusCard({ title, status, icon: Icon, details }: StatusCardProps) {
    const isUp = status === "UP";

    return (
        <div className="bg-zinc-900/40 border border-white/5 p-6 rounded-3xl">
            <div className="flex justify-between items-center mb-4">
                <div className="p-3 bg-white/5 rounded-2xl text-zinc-400">
                    <Icon size={24} />
                </div>

                {isUp ? (
                    <CheckCircle2 className="text-emerald-500" size={20} />
                ) : (
                    <XCircle className="text-red-500" size={20} />
                )}
            </div>

            <h3 className="text-lg font-bold text-white">{title}</h3>
            <p className="text-sm text-zinc-500 mt-1">
                {details || (isUp ? "Operacional" : "Indisponível")}
            </p>
        </div>
    );
}

export default function StatusPage() {
    const { data, isLoading } = useObservable(
        healthFacade.state$,
        INITIAL_HEALTH_STATE,
    );

    useEffect(() => {
        healthFacade.checkStatus();

        const interval = setInterval(
            () => healthFacade.checkStatus(),
            30000,
        );

        return () => clearInterval(interval);
    }, []);

    return (
        <div className="p-8 space-y-8 min-h-screen bg-zinc-950">
            <div className="flex items-center gap-3">
                <Activity className="text-purple-500" size={32} />
                <h1 className="text-3xl font-bold text-white">
                    Status do Sistema
                </h1>
            </div>

            {isLoading ? (
                <div className="flex justify-center py-20">
                    <Loader2
                        className="animate-spin text-purple-500"
                        size={40}
                    />
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    <StatusCard
                        title="Banco de Dados"
                        status={data?.components?.db?.status}
                        icon={Database}
                        details={data?.components?.db?.details?.database}
                    />

                    <StatusCard
                        title="Storage (MinIO)"
                        status={data?.components?.minio?.status}
                        icon={Cloud}
                    />

                    <StatusCard
                        title="Disco"
                        status={data?.components?.diskSpace?.status}
                        icon={HardDrive}
                    />

                    <StatusCard
                        title="Aplicação"
                        status={data?.status}
                        icon={Activity}
                    />
                </div>
            )}
        </div>
    );
}