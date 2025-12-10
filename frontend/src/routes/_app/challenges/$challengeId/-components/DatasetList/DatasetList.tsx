import type {FileDetails} from "../../../../../../types/submissions/fileDetails.ts";
import {formatBytes} from "../../../../../../utils/formatBytes.ts";
import "./dataset-list.css";
import DownloadIcon from "../../../../../../assets/download.svg?react";

const DatasetRow = ({dataset}: { dataset: FileDetails }) => {

    if (!dataset.attachmentId) {
        throw new Error(`Dataset is missing attachmentId for file: ${dataset.fileName}`);
    }

    const downloadUrl = `/api/challenges/1/datasets/${dataset.attachmentId}/download`;

    return (
        <div className="dataset-row">
            <div className="file-name-col">{dataset.fileName}</div>
            <div className="file-size-col">{formatBytes(dataset.fileSize)}</div>
            <div></div>
            <div className="download-col">
                <a href={downloadUrl} download className="download-button">
                    <DownloadIcon/>
                </a>
            </div>
        </div>
    );
}

const DatasetList = ({items}: { items: FileDetails[] }) => {

    if (items.length === 0) {
        return (
            <div className="no-data-message">
                <p>No datasets available for download.</p>
            </div>
        );
    }

    return (
        <div className="dataset-table-wrapper">
            <div className="dataset-table">
                <div className="header">
                    <div className="file-name-col header-cell">File Name</div>
                    <div className="file-size-col header-cell">File Size</div>
                    <div></div>
                    <div className="download-col header-cell">Download</div>
                </div>
                {
                    items.map(dataset => (
                        <DatasetRow key={dataset.attachmentId || dataset.fileName} dataset={dataset}/>
                    ))
                }
            </div>
        </div>

    );
}

export default DatasetList;