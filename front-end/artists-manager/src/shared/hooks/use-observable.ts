import { useEffect, useState } from "react";
import { BehaviorSubject, Observable } from "rxjs";

export function useObservable<T>(
  observable$: Observable<T>,
  initialValue?: T,
): T {
  const getInitialValue = () => {
    if (observable$ instanceof BehaviorSubject) {
      return observable$.value;
    }
    return initialValue as T;
  };
  const [value, setValue] = useState<T>(getInitialValue);

  useEffect(() => {
    const subscription = observable$.subscribe({
      next: (val) => setValue(val),
      error: (err) => console.error("Erro no stream reativo:", err),
    });

    return () => subscription.unsubscribe();
  }, [observable$]);

  return value;
}
