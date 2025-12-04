import {adminApi} from "./axios.ts";

export const downloadBlobFromUrl = async (url: string, signal: AbortSignal): Promise<Blob> => {

    const result = await adminApi.get(url, {
        responseType: "blob",
        signal: signal,
    });

    return result.data;
}