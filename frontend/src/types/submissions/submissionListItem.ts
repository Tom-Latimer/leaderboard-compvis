import type {OrderedItem} from "../pagination/orderedItem.ts";

export interface SubmissionListItem extends OrderedItem {
    challengeId: number;
    challengeName: string;
    submissionId: string;
    status: string;
    submittedAt: string;
}