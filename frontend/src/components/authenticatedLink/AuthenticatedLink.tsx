import React, {useEffect, useRef, useState} from "react";
import {downloadBlobFromUrl} from "../../api/downloadApi.ts";
import axios from "axios";

const AuthenticatedLink = ({url, fileName, children}: {
    url: string,
    fileName: string,
    children: React.ReactNode
}) => {

    const link = useRef<HTMLAnchorElement>(null);
    const [objectUrl, setObjectUrl] = useState<string | null>(null);

    //reference for AbortController
    const abortControllerRef = useRef<AbortController>(null);

    useEffect(() => {
        let downloadUrl: string | null = null;
        let isCanceled: boolean = false;

        const createAndSetUrl = async () => {
            if (!url) return;

            //abort if another request is in progress
            if (abortControllerRef.current) {
                abortControllerRef.current.abort();
            }

            //pass new abort signal to api call
            abortControllerRef.current = new AbortController();
            const {signal} = abortControllerRef.current;

            try {
                const blob = await downloadBlobFromUrl(url, signal);

                if (!isCanceled) {
                    downloadUrl = window.URL.createObjectURL(blob);
                    setObjectUrl(downloadUrl);
                }
            } catch (error) {
                if (error instanceof Error && error.name !== 'AbortError' && !axios.isCancel(error)) {
                    throw error;
                }
            }

        };

        createAndSetUrl();

        //free the url from memory
        return () => {
            isCanceled = true;
            abortControllerRef.current?.abort();

            if (downloadUrl) {
                window.URL.revokeObjectURL(downloadUrl);
            }
        }
    }, [url]);

    const handleDownload = (event: React.MouseEvent<HTMLAnchorElement>) => {

        //prevent default click behaviour until download link is ready
        if (!objectUrl) {
            event.preventDefault();
        }
    }

    return (
        <>
            <a
                ref={link}
                href={objectUrl || undefined}
                download={fileName}
                onClick={handleDownload}
                target="_blank"
            >
                {children}
            </a>
        </>
    );
}

export default AuthenticatedLink;