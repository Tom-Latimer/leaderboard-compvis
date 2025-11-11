import api from "../axios.ts";
import type {SubmissionUploadResponse} from "../../types/submissions/submissionUploadResponse.ts";
import {SubmissionLeaderboardItem} from "../../types/submissions/submissionLeaderboardItem.ts";
import type {PagedResponse} from "../../types/pagination/pagedResponse.ts";

export const postChallengeSubmission = async (challengeId: string, formData: FormData): Promise<SubmissionUploadResponse[]> => {
    const result = await api.post(`/challenges/${challengeId}/submissions`, formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
    return result.data;
}

export const getSubmissionsByChallenge = async (
    challengeId: string,
    p: number,
    s: number,
    sortKey: string | null,
    sortOrder: string | null,
    signal: AbortSignal): Promise<PagedResponse<SubmissionLeaderboardItem>> => {

    const result = await api.get(`/challenges/${challengeId}/submissions`, {
        params: {
            id: challengeId,
            p: p,
            s: s,
            sort: sortKey,
            sortOrder: sortOrder,
        },
        signal: signal,
    });

    const mappedContent = result.data.content.map(
        (d: any) =>
            new SubmissionLeaderboardItem(d.submissionId, d.submitterFirstName, d.submitterLastName,
                d.submitterEmail, d.maxPrecision, d.maxRecall, d.split, d.rank)
    );

    return {...result.data, content: mappedContent};
}