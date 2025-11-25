import type {SubmissionTeamMember} from "./submissionTeamMember.ts";

export interface Submission {
    submissionId: string;
    organization: string;
    teamName: string;
    maxPrecision: number;
    maxRecall: number;
    split: number;
    teamMembers: SubmissionTeamMember[];
}