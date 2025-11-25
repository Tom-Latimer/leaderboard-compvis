import {Controller, type FieldValues, type Path, useFormContext} from "react-hook-form";
import UploadIcon from "../../../assets/upload.svg?react";
import CheckFileIcon from "../../../assets/check-file.svg?react";
import CrossIcon from "../../../assets/circled-x.svg?react";
import "./file-dropzone.css";
import React from "react";

interface FileDropzoneProps<T extends FieldValues> {
    name: Path<T>;
    required?: boolean;
    accept?: string;
    maxEntries?: number;
}

const UploadedFile = ({file, onRemove}: { file: File; onRemove: () => void }) => {

    const getFormattedSize = (fileSize: number): string => {
        if (fileSize < 1024 * 1024) return `${fileSize} B`;
        if (fileSize < 1024 * 1024 * 1024) return `${(fileSize / (1024 * 1024)).toFixed(2)} MB`;
        return `${(fileSize / (1024 * 1024 * 1024)).toFixed(2)} GB`;
    }

    return (
        <div className="file-info">
            <div className="left-container">
                <CheckFileIcon className="file-icon"/>
                <div>
                    <p> {file.name} </p>
                    <p className="file-size"> {getFormattedSize(file.size)}</p>
                </div>
            </div>


            <button type="button" onClick={onRemove} className="cross-button">
                <CrossIcon/>
            </button>
        </div>
    );
}

function FileDropzone<T extends FieldValues>({
                                                 name,
                                                 required = false,
                                                 accept = "*/*",
                                                 maxEntries = Infinity
                                             }: FileDropzoneProps<T>) {

    const {control} = useFormContext<T>();

    return (
        <Controller
            name={name}
            control={control}
            rules={{required}}
            render={({field}) => {

                const files = (field.value as File[]) || [];

                const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
                    if (!e.target.files) return;

                    const newFiles = Array.from(e.target.files);
                    const combined = [...files, ...newFiles];

                    if (combined.length > maxEntries) {

                        const limitedFiles = combined.slice(0, maxEntries);

                        field.onChange(limitedFiles);
                    } else {
                        field.onChange(combined);
                    }

                    e.target.value = "";
                }

                const handleRemove = (index: number) => {
                    const updated = (files || []).filter((_, i) => i !== index);
                    field.onChange(updated);
                }

                return (
                    <div className="dropzone-container">
                        <label htmlFor={name} className="dropzone">
                            <div className="dropzone-text">
                                <UploadIcon className="dropzone-icon"/>
                                <p><span>Click to upload</span> or drag and drop</p>
                            </div>
                            <input
                                id={name}
                                type="file"
                                className="hidden"
                                accept={accept}
                                onChange={handleChange}/>
                        </label>
                        {files && files.length > 0 && (
                            <div className="uploaded-files-list">
                                <h4>Uploaded Files</h4>
                                {files.map((file, index) => (
                                    <UploadedFile key={index} file={file} onRemove={() => handleRemove(index)}/>
                                ))}
                            </div>
                        )}
                    </div>
                );
            }}
        />

    );
}

export default FileDropzone;