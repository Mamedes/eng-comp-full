export function FullScreenLoader() {
    return (
        <div className="flex h-screen w-screen items-center justify-center bg-slate-50">
            <div className="flex flex-col items-center gap-4 animate-fade-in">
                <div className="h-10 w-10 animate-spin rounded-full border-4 border-slate-200 border-t-blue-500" />
                <span className="text-sm text-slate-600">
                    Carregando…
                </span>
            </div>
        </div>
    );
}