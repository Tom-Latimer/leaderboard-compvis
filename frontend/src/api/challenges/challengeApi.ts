import type {Challenge} from "../../types/challenges/challenge.tsx";
import api from "../axios.ts";

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
    })
    return result.data;
}