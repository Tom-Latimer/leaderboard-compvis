export class Dataset {
    attachmentId: string;
    fileName: string;
    fileSize: number;

    constructor(attachmentId: string, fileName: string, fileSize: number) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    getFormattedSize(): string {
        if (this.fileSize < 1024 * 1024) return `${this.fileSize} B`;
        if (this.fileSize < 1024 * 1024 * 1024) return `${(this.fileSize / (1024 * 1024)).toFixed(2)} MB`;
        return `${(this.fileSize / (1024 * 1024 * 1024)).toFixed(2)} GB`;
    }
}