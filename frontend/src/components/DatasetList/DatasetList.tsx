import './dataset-list.css';
import {Dataset} from "../../types/challenges/dataset.ts";

const DatasetRow = ({dataset}: { dataset: Dataset }) => {

    if (!dataset.attachmentId) {
        throw new Error(`Dataset is missing attachmentId for file: ${dataset.fileName}`);
    }

    const downloadUrl = `/api/challenges/1/datasets/${dataset.attachmentId}/download`;

    return (
        <tr>
            <td>{dataset.fileName}</td>
            <td>{dataset.getFormattedSize()}</td>
            <td>
                <a href={downloadUrl} download>Download</a>
            </td>
        </tr>
    );
}

const DatasetList = ({datasets}: { datasets: Dataset[] }) => {

    if (datasets.length === 0) {
        return (
            <p>No datasets to download.</p>
        );
    }

    return (
        <div className="">
            <table>
                <thead>
                <tr>
                    <th>File name</th>
                    <th>File size</th>
                    <th>Download</th>
                </tr>
                </thead>
                <tbody>
                {
                    datasets.map(dataset => (
                        <DatasetRow key={dataset.attachmentId} dataset={dataset}/>
                    ))
                }
                </tbody>
            </table>
        </div>
    );
}

export default DatasetList;