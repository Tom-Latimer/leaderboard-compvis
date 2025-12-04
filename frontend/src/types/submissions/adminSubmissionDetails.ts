import type {SubmissionTeamMember} from "./submissionTeamMember.ts";
import type {SubmissionStatus} from "./submissionFilter.ts";

export interface AdminSubmissionDetails {
    submissionId: string;
    organization: string;
    teamName: string;
    status: SubmissionStatus;
    maxPrecision: number | null;
    maxRecall: number | null;
    split: number | null;
    challengeName: string;
    fileName: string;
    fileSize: number;
    teamMembers: SubmissionTeamMember[];
}