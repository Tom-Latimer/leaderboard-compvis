export const formatBytes = (bytes: number | null | undefined, decimals = 2): string => {

    if (bytes === 0 || bytes === null || bytes === undefined) {
        return '0 Bytes';
    }

    const absoluteBytes = Math.abs(bytes);

    //define base as 1024 for binary
    const k = 1024;

    const dm = decimals < 0 ? 0 : decimals;

    //units
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];

    const i = Math.floor(Math.log(absoluteBytes) / Math.log(k));

    return `${(absoluteBytes / Math.pow(k, i)).toFixed(i === 0 ? 0 : dm)} ${sizes[i]}`;
};