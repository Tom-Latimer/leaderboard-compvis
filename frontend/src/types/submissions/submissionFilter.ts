export type SubmissionStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface SubmissionFilter {
    challengeId?: number;
    challengeName?: string;
    submissionId?: string;
    status?: SubmissionStatus;
    organization?: string;
    teamName?: string;
}