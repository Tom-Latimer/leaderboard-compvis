import api from "../axios.ts";
import type {SubmissionUploadResponse} from "../../types/submissions/submissionUploadResponse.ts";

export const postChallengeSubmission = async (challengeId: string, formData: FormData): Promise<SubmissionUploadResponse[]> => {
    const result = await api.post(`/challenges/${challengeId}/submissions`, formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
    return result.data;
}