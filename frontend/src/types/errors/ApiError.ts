export interface ApiError {
    status: number;
    errorCode: string;
    timestamp: string;
    validationErrors?: Record<string, string>;
}