import {adminApi} from "./axios.ts";
import type {AdminSubmissionDetails} from "../types/submissions/adminSubmissionDetails.ts";
import type {SubmissionStatusUpdateBody} from "../types/submissions/submissionStatusUpdateBody.ts";
import type {SubmissionUpdateStatusResponse} from "../types/submissions/submissionUpdateStatusResponse.ts";
import type {SubmissionFilter} from "../types/submissions/submissionFilter.ts";
import type {PagedResponse} from "../types/pagination/pagedResponse.ts";
import type {SubmissionListItem} from "../types/submissions/submissionListItem.ts";

export const getSubmissionListPaged = async (
    filter: SubmissionFilter,
    p: number,
    s: number,
    sortKey: string | null,
    sortOrder: string | null,
    signal: AbortSignal): Promise<PagedResponse<SubmissionListItem>> => {

    const result = await adminApi.get(`/submissions`, {
        params: {
            filter: filter,
            p: p,
            s: s,
            sort: sortKey,
            sortOrder: sortOrder,
        },
        signal: signal,
    });

    return {...result.data, content: result.data.content};
}

export const getSubmissionDetails = async (
    submissionId: string): Promise<AdminSubmissionDetails> => {

    const result = await adminApi.get(`/submissions/${submissionId}`, {
        params: {
            id: submissionId,
        },
    });

    return result.data;
}

export const patchSubmissionStatus = async (
    submissionId: string,
    body: SubmissionStatusUpdateBody): Promise<SubmissionUpdateStatusResponse> => {
    const result = await adminApi.patch(`/submissions/${submissionId}`, body);

    return result.data;
}