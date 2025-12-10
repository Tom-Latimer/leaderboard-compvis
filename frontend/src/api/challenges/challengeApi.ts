import type {Challenge} from "../../types/challenges/challenge.ts";
import api from "../axios.ts";
import type {PagedResponse} from "../../types/pagination/pagedResponse.ts";
import type {ChallengeOverview} from "../../types/challenges/challengeOverview.ts";
import type {FileDetails} from "../../types/submissions/fileDetails.ts";

export const getChallengeById = async (id: string): Promise<Challenge> => {
    const result = await api.get(`/challenges/${id}`);
    return result.data;
}

export const getChallengeOverview = async (id: string): Promise<ChallengeOverview> => {
    const result = await api.get(`/challenges/${id}/overview`);
    return result.data;
}

export const getChallenges = async (p: number, s: number, signal: AbortSignal): Promise<PagedResponse<Challenge>> => {
    const result = await api.get(`/challenges`, {
        params: {
            p: p,
            s: s
        },
        signal: signal,
    });
    return result.data;
}

export const getDatasets = async (challengeId: string, p: number, s: number, signal: AbortSignal): Promise<PagedResponse<FileDetails>> => {
    const result = await api.get(`/challenges/${challengeId}/datasets`, {
        params: {
            id: challengeId,
            p: p,
            s: s
        },
        signal: signal,
    });

    console.log(result.data);
    return result.data;
}