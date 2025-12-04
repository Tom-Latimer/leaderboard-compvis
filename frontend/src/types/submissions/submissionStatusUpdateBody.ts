import type {SubmissionStatus} from "./submissionFilter.ts";

export interface SubmissionStatusUpdateBody {
    status: SubmissionStatus;
    maxPrecision: number | null;
    maxRecall: number | null;
    split: number | null;
}