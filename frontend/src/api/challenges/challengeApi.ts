import type {Challenge} from "../../types/challenges/challenge.tsx";
import api from "../axios.ts";
import {Dataset} from "../../types/challenges/dataset.ts";

export const getChallengeById = async (id: string): Promise<Challenge> => {
    const result = await api.get(`/challenges/${id}`);
    return result.data;
}

export const getChallenges = async (p: number, s: number): Promise<Challenge[]> => {
    const result = await api.get(`/challenges`, {
        params: {
            p: p,
            s: s
        }
    });
    return result.data;
}

export const getDatasets = async (challengeId: string, p: number, s: number): Promise<Challenge[]> => {
    const result = await api.get(`/challenges/${challengeId}/datasets`, {
        params: {
            id: challengeId,
            p: p,
            s: s
        }
    });
    return result.data.map((d: any) => new Dataset(d.attachmentId, d.fileName, d.contentLength));
}