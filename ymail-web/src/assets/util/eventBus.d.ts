declare module '@/assets/util/eventBus' {
    export function on(event: string, callback: Function): void
    export function off(event: string, callback: Function): void
    export function emit(event: string, ...args: any[]): void
}